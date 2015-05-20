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
 * �����ʼ���
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
		props.put("mail.smtp.auth", "true");// ����Ҫ��֤
		props.put("mail.smtp.host", "smtp.exmail.qq.com");// ����host
		props.put("mail.smtp.port", "25"); // ���ö˿�
		SuggestAuthenticator pass = new SuggestAuthenticator(); // ��ȡ�ʺ�����
		Session session = Session.getInstance(props, pass); // ��ȡ��֤�Ự
		try {
			// ���÷��ͼ���������
			InternetAddress fromAddress, toAddress;
			/**
			 * ����ط���Ҫ�ĳ��Լ�������
			 */
			fromAddress = new InternetAddress("suggest_app_tank@wazert.com");
			toAddress = new InternetAddress("zz@wazert.com");
			MimeMessage message = new MimeMessage(session);
			//�Ƿ��и���
			if(filePath!= null){
				MimeMultipart allMultipart = new MimeMultipart("mixed"); // ����
				// �����ļ���MimeMultipart
				MimeBodyPart contentPart = createContentPart("��yaofahuo��������־", filePath);
				allMultipart.addBodyPart(contentPart);
				message.setContent(allMultipart); // ���ʼ�ʱ��Ӹ���
			}
			
			// ���÷�����Ϣ
			//message.setContent(content, "text/plain");
			message.setText(content);
			message.setSubject(subject);
			message.setFrom(fromAddress);
			message.addRecipient(javax.mail.Message.RecipientType.TO, toAddress);
			message.saveChanges();
			// �������䲢����
			Transport transport = session.getTransport("smtp");
			/**
			 * ����ط���Ҫ�ĳ��Լ����˺ź�����
			 */
			transport.connect("smtp.exmail.qq.com", "suggest_app_tank@wazert.com", "wazert123456");
			transport.send(message);
			transport.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogUplus.upLog_i("Exception_sendEmail", "���ͳ���");
			return false;
		}
	}
	
	private static MimeBodyPart createContentPart(String bodyStr, String filename)
			throws Exception {
		// ���ڱ����������Ĳ���
		MimeBodyPart contentBody = new MimeBodyPart();
		// ��������ı���ͼƬ��"related"�͵�MimeMultipart����
		MimeMultipart contentMulti = new MimeMultipart("related");

		// ���ĵ��ı�����
		MimeBodyPart textBody = new MimeBodyPart();
		textBody.setContent(bodyStr, "text/html;charset=utf-8");
		contentMulti.addBodyPart(textBody);

		/**
		 * һ�������ǣ������ʼ�ʱ��Ӹ���
		 */
		MimeBodyPart attachPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(
				SDCARD_ROOT_PATH + FILE_PATH+ filename); // ��Ҫ���͵��ļ�
		if(fds.getFile().length() > 0){
			attachPart.setDataHandler(new DataHandler(fds));
			attachPart.setFileName(fds.getName());
			contentMulti.addBodyPart(attachPart);
		}

		// ������"related"�͵� MimeMultipart ������Ϊ�ʼ�������
		contentBody.setContent(contentMulti);
		return contentBody;
	}
}
