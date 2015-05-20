package com.hlkt123.uplus.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.util.Log;

/**
 * 发送邮件类
 * @author zz
 *
 */
public class SendEmail {
	
	private static final String SDCARD_ROOT_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();  
	private static final String FILE_PATH = "/WGFA/LOG/";

	public static boolean sendEmail(final String subject,final String content,final String filePath) {
		
		Properties props = new Properties();
		props.put("mail.smtp.protocol", "smtp");
		props.put("mail.smtp.auth", "true");// 设置要验证
		props.put("mail.smtp.host", "smtp.exmail.qq.com");// 设置host
		props.put("mail.smtp.port", "25"); // 设置端口
		SuggestAuthenticator pass = new SuggestAuthenticator(); // 获取帐号密码
		Session session = Session.getInstance(props, pass); // 获取验证会话
		try {
			// 配置发送及接收邮箱
			InternetAddress fromAddress, toAddress;
			/**
			 * 这个地方需要改成自己的邮箱
			 */
			fromAddress = new InternetAddress("suggest_app_tank@wazert.com");
			toAddress = new InternetAddress("zz@wazert.com");
			MimeMessage message = new MimeMessage(session);
			//是否有附件
			if(filePath!= null){
				MimeMultipart allMultipart = new MimeMultipart("mixed"); // 附件
				// 设置文件到MimeMultipart
				MimeBodyPart contentPart = createContentPart("【yaofahuo】错误日志", filePath);
				allMultipart.addBodyPart(contentPart);
				message.setContent(allMultipart); // 发邮件时添加附件
			}
			
			// 配置发送信息
			//message.setContent(content, "text/plain");
			message.setText(content);
			message.setSubject(subject);
			message.setFrom(fromAddress);
			message.addRecipient(javax.mail.Message.RecipientType.TO, toAddress);
			message.saveChanges();
			// 连接邮箱并发送
			Transport transport = session.getTransport("smtp");
			/**
			 * 这个地方需要改称自己的账号和密码
			 */
			transport.connect("smtp.exmail.qq.com", "suggest_app_tank@wazert.com", "wazert123456");
			transport.send(message);
			transport.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogUplus.upLog_i("Exception_sendEmail", "发送出错");
			return false;
		}
	}
	
	private static MimeBodyPart createContentPart(String bodyStr, String filename)
			throws Exception {
		// 用于保存最终正文部分
		MimeBodyPart contentBody = new MimeBodyPart();
		// 用于组合文本和图片，"related"型的MimeMultipart对象
		MimeMultipart contentMulti = new MimeMultipart("related");

		// 正文的文本部分
		MimeBodyPart textBody = new MimeBodyPart();
		textBody.setContent(bodyStr, "text/html;charset=utf-8");
		contentMulti.addBodyPart(textBody);

		/**
		 * 一下内容是：发送邮件时添加附件
		 */
		MimeBodyPart attachPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(
				SDCARD_ROOT_PATH + FILE_PATH+ filename); // 打开要发送的文件
		if(fds.getFile().length() > 0){
			attachPart.setDataHandler(new DataHandler(fds));
			attachPart.setFileName(fds.getName());
			contentMulti.addBodyPart(attachPart);
		}

		// 将上面"related"型的 MimeMultipart 对象作为邮件的正文
		contentBody.setContent(contentMulti);
		return contentBody;
	}
}
