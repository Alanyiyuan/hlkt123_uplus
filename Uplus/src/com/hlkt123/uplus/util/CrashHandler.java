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
 * UncaughtException������,��������Uncaught�쳣��ʱ��,�и������ӹܳ���,����¼���ʹ��󱨸�.
 * 
 * ��Ҫ��Application��ע�ᣬΪ��Ҫ�ڳ����������ͼ����������
 */
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";
	// ϵͳĬ�ϵ�UncaughtException������
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandlerʵ��
	private static CrashHandler instance;
	// �����Context����
	private Context mContext;
	// �����洢�豸��Ϣ���쳣��Ϣ
	private Map<String, String> infos = new HashMap<String, String>();

	// ���ڸ�ʽ������,��Ϊ��־�ļ�����һ����
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	/** ��ֻ֤��һ��CrashHandlerʵ�� */
	private CrashHandler() {
	}

	/** ��ȡCrashHandlerʵ�� ,����ģʽ */
	public static CrashHandler getInstance() {
		if (instance == null)
			instance = new CrashHandler();
		return instance;
	}

	/**
	 * ��ʼ��
	 */
	public void init(Context context) {
		mContext = context;
		// ��ȡϵͳĬ�ϵ�UncaughtException������
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// ���ø�CrashHandlerΪ�����Ĭ�ϴ�����
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * ��UncaughtException����ʱ��ת��ú���������
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		System.out.println("thread:" + thread + ";Throwable:" + ex);
		if (!handleException(ex) && mDefaultHandler != null) {
			// ����û�û�д�������ϵͳĬ�ϵ��쳣������������
			mDefaultHandler.uncaughtException(thread, ex);
			System.out.println("ϵͳĬ�ϵ��쳣������������:");
		} else {
			System.out.println("ϵͳ�������˳���System.exit(1)");
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}

	/**
	 * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����.
	 * 
	 * @param ex
	 * @return true:��������˸��쳣��Ϣ;���򷵻�false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// �ռ��豸������Ϣ
		collectDeviceInfo(mContext);

		// ʹ��Toast����ʾ�쳣��Ϣ
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "�ż�" + "�����˳���������������",
						Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}.start();
		// ������־�ļ�
		saveCatchInfo2File(ex);
		return true;
	}

	/**
	 * �ռ��豸������Ϣ
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
	 * ���������Ϣ���ļ���
	 * 
	 * @param ex
	 * @return �����ļ�����,���ڽ��ļ����͵�������
	 */
	private String saveCatchInfo2File(Throwable ex) {

		StringBuffer sb = new StringBuffer();
		sb.append("------------------�豸��Ϣ---------------------\n");
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}
		sb.append("------------------������Ϣ---------------------\n");
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
		// ������ͳɹ��ˣ�ȥ�鿴�����Ƿ�����־�ļ����еĻ���ȡ���ݣ����ͳ�ȥ

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
	 * ������־д�뱾��
	 * 
	 * @param path
	 *            �ļ���·��
	 * @param content
	 *            ����
	 * @param fileName
	 *            �ļ���
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
			System.out.println("������־�ѱ��浽����");
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	/**
	 * ��ȡ����log�ļ��е�����
	 * 
	 * @param path
	 *            �ļ���·��
	 * @param fileName
	 *            �ļ���
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
	 * �����ʼ��ķ���
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
				props.put("mail.smtp.auth", "true");// ����Ҫ��֤
				props.put("mail.smtp.host", "smtp.ym.163.com");// ����host
				props.put("mail.smtp.port", "25"); // ���ö˿�
				CrashAuthenticator pass = new CrashAuthenticator(); // ��ȡ�ʺ�����
				Session session = Session.getInstance(props, pass); // ��ȡ��֤�Ự
				try {
					// ���÷��ͼ���������
					InternetAddress fromAddress, toAddress;
					/**
					 * ����ط���Ҫ�ĳ��Լ�������
					 */
					fromAddress = new InternetAddress(
							"liuyiyuan@hlkt123.com");
					toAddress = new InternetAddress(
							Constants.EXCEPTION_EXIT_EMAILTO_ADDR);

					// MimeMultipart allMultipart = new MimeMultipart("mixed");
					// // ����
					// // �����ļ���MimeMultipart
					// MimeBodyPart contentPart = createContentPart("������־",
					// filename);
					// allMultipart.addBodyPart(contentPart);
					// ���÷�����Ϣ
					MimeMessage message = new MimeMessage(session);
					// message.setContent("test123123123123123", "text/plain");
					// message.setContent(allMultipart); // ���ʼ�ʱ��Ӹ���
					message.setText(content);
					message.setSubject("������־");
					message.setFrom(fromAddress);
					message.addRecipient(javax.mail.Message.RecipientType.TO,
							toAddress);
					message.saveChanges();
					// �������䲢����
					Transport transport = session.getTransport("smtp");
					/**
					 * ����ط���Ҫ�ĳ��Լ����˺ź�����
					 */
					transport.connect("smtp.ym.163.com",
							"liuyiyuan@hlkt123.com", "Dkalan@1987");
					Transport.send(message);
					transport.close();

				} catch (Exception e) {
					e.printStackTrace();
					LogUplus.upLog_i("Exception_sendEmail", "���ͳ���,׼��д�뱾�ش�����־");
					writerText(Constants.LOG_PATH, content, "errLog.txt");

				}

			}
		});
		t.start();
		return true;
	}
}
