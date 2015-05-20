package com.hlkt123.uplus.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hlkt123.uplus.Constants;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * 
 * 需要在Application中注册，为了要在程序启动器就监控整个程序。
 */
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";
	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler instance;
	// 程序的Context对象
	private Context mContext;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		if (instance == null)
			instance = new CrashHandler();
		return instance;
	}

	/**
	 * 初始化
	 */
	public void init(Context context) {
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		System.out.println("thread:" + thread + ";Throwable:" + ex);
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
			System.out.println("系统默认的异常处理器来处理:");
		} else {
			System.out.println("系统非正常退出：System.exit(1)");
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 收集设备参数信息
		collectDeviceInfo(mContext);

		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "优加" + "意外退出，请点击重新启动",
						Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}.start();
		// 保存日志文件
		saveCatchInfo2File(ex);
		return true;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				LogUplus.upLog_d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String saveCatchInfo2File(Throwable ex) {

		StringBuffer sb = new StringBuffer();
		sb.append("------------------设备信息---------------------\n");
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}
		sb.append("------------------错误信息---------------------\n");
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		sendEmail(sb.toString());
		// 如果发送成功了，去查看本地是否有日志文件，有的话读取内容，发送出去

		String lastLog = readText(Constants.LOG_PATH, "errLog.txt");
		if (lastLog != null) {
			sendEmail(lastLog);
			delLogFile(Constants.LOG_PATH, "errLog.txt");
		}

		return null;
	}

	private void delLogFile(String path, String fileName) {
		// TODO Auto-generated method stub
		File file = new File(path + fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
		}
	}

	/**
	 * 错误日志写入本地
	 * 
	 * @param path
	 *            文件夹路径
	 * @param content
	 *            内容
	 * @param fileName
	 *            文件名
	 */
	public static void writerText(String path, String content, String fileName) {

		File dirFile = new File(path);

		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		try {
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(path + fileName, true), "utf-8");
			BufferedWriter bw1 = new BufferedWriter(write);
			bw1.write(content);
			bw1.flush();
			bw1.close();
			write.close();
			System.out.println("错误日志已保存到本地");
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	/**
	 * 读取本地log文件中的内容
	 * 
	 * @param path
	 *            文件夹路径
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static String readText(String path, String fileName) {

		File file = new File(path + fileName);

		if (!file.exists()) {
			return null;
		}
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			reader = new InputStreamReader(
					new FileInputStream(path + fileName), "utf-8");
			br = new BufferedReader(reader);
			String content = "\n------sdcard errlog info-------\n ;log time:"
					+ DateUtil.addDays(new Date(), 0) + "-------\n";
			String line = null;
			while ((line = br.readLine()) != null) {
				content += line + "\n";
			}
			br.close();
			reader.close();
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			if (br != null) {
				try {
					br.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 发送邮件的方法
	 * 
	 * @return
	 */
	private boolean sendEmail(final String content) {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Properties props = new Properties();
				props.put("mail.smtp.protocol", "smtp");
				props.put("mail.smtp.auth", "true");// 设置要验证
				props.put("mail.smtp.host", "smtp.ym.163.com");// 设置host
				props.put("mail.smtp.port", "25"); // 设置端口
				CrashAuthenticator pass = new CrashAuthenticator(); // 获取帐号密码
				Session session = Session.getInstance(props, pass); // 获取验证会话
				try {
					// 配置发送及接收邮箱
					InternetAddress fromAddress, toAddress;
					/**
					 * 这个地方需要改成自己的邮箱
					 */
					fromAddress = new InternetAddress(
							"liuyiyuan@hlkt123.com");
					toAddress = new InternetAddress(
							Constants.EXCEPTION_EXIT_EMAILTO_ADDR);

					// MimeMultipart allMultipart = new MimeMultipart("mixed");
					// // 附件
					// // 设置文件到MimeMultipart
					// MimeBodyPart contentPart = createContentPart("错误日志",
					// filename);
					// allMultipart.addBodyPart(contentPart);
					// 配置发送信息
					MimeMessage message = new MimeMessage(session);
					// message.setContent("test123123123123123", "text/plain");
					// message.setContent(allMultipart); // 发邮件时添加附件
					message.setText(content);
					message.setSubject("错误日志");
					message.setFrom(fromAddress);
					message.addRecipient(javax.mail.Message.RecipientType.TO,
							toAddress);
					message.saveChanges();
					// 连接邮箱并发送
					Transport transport = session.getTransport("smtp");
					/**
					 * 这个地方需要改称自己的账号和密码
					 */
					transport.connect("smtp.ym.163.com",
							"liuyiyuan@hlkt123.com", "Dkalan@1987");
					Transport.send(message);
					transport.close();

				} catch (Exception e) {
					e.printStackTrace();
					LogUplus.upLog_i("Exception_sendEmail", "发送出错,准备写入本地错误日志");
					writerText(Constants.LOG_PATH, content, "errLog.txt");

				}

			}
		});
		t.start();
		return true;
	}
}
