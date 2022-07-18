package com.igw.market.common.util.Email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.igw.market.common.util.ResultMessage;



/**
 * 简单邮件（不带附件的邮件）发送器 http://www.bt285.cn BT下载
 */
public class SimpleMailSender {

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	public boolean sendTextMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}

		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);

			// 创建邮件的接收者地址，并设置到邮件消息中
			String address = mailInfo.getToAddress();
			String[] addressList = mailInfo.getToAddressList();
			if (address != null) {
				// 单个收件人
				Address to = new InternetAddress(address);
				mailMessage.setRecipient(Message.RecipientType.TO, to);
			} else if (addressList != null && addressList.length > 0) {
				// 多个收件人
				Address[] tos = new InternetAddress[addressList.length];
				for (int i = 0; i < addressList.length; i++) {
					tos[i] = new InternetAddress(addressList[i]);
				}
				mailMessage.setRecipients(Message.RecipientType.TO, tos);
			}

			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);

			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public ResultMessage sendHtmlMail(MailSenderInfo mailInfo) {
		ResultMessage msg = new ResultMessage();
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			String address = mailInfo.getToAddress();
			String[] addressList = mailInfo.getToAddressList();
			if (address != null) {
				// 单个收件人
				Address to = new InternetAddress(address);
				mailMessage.setRecipient(Message.RecipientType.TO, to);
			} else if (addressList != null && addressList.length > 0) {
				// 多个收件人
				Address[] tos = new InternetAddress[addressList.length];
				for (int i = 0; i < addressList.length; i++) {
					tos[i] = new InternetAddress(addressList[i]);
				}
				mailMessage.setRecipients(Message.RecipientType.TO, tos);
			}
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			msg.setMsgCode("Y");
		} catch (MessagingException ex) {
			ex.printStackTrace();
			msg.setMsgCode("N");
			msg.setMsgDesc(ex.toString());
		}
		return msg;
	}

	/**
	 * 以HTML格式发送邮件
	 * 附件可选
	 * 密送人可选
	 * @param mailInfo
	 */
	public static ResultMessage sendHtmlMailWithMore(MailSenderInfo mailInfo) {
		ResultMessage msg = new ResultMessage();
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties properties = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}

		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(properties, authenticator);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);// 根据session创建一个邮件消息
			Address from = new InternetAddress(mailInfo.getFromAddress());// 创建邮件发送者地址
			mailMessage.setFrom(from);// 设置邮件消息的发送者

			// 创建邮件的接收者地址，并设置到邮件消息中
			String address = mailInfo.getToAddress();
			String[] addressList = mailInfo.getToAddressList();

			if (address != null) {
				// 单个收件人
				Address to = new InternetAddress(address);
				mailMessage.setRecipient(Message.RecipientType.TO, to);
			} else if (addressList != null && addressList.length > 0) {
				// 多个收件人
				Address[] tos = new InternetAddress[addressList.length];
				for (int i = 0; i < addressList.length; i++) {
					tos[i] = new InternetAddress(addressList[i]);
				}
				mailMessage.setRecipients(Message.RecipientType.TO, tos);
			}
			// 密送人
			if (mailInfo.getMsAddress() != null && mailInfo.getMsAddress().length > 0) {
				// 为每个邮件接收者创建一个地址
				Address[] msAdresses = new InternetAddress[mailInfo.getMsAddress().length];
				for (int i = 0; i < mailInfo.getMsAddress().length; i++) {
					msAdresses[i] = new InternetAddress(mailInfo.getMsAddress()[i]);
				}
				mailMessage.setRecipients(Message.RecipientType.BCC, msAdresses); // 密送人
			}
			
			// 抄送人
			if (mailInfo.getCcs() != null && mailInfo.getCcs()[0] != "") {
				// 为每个邮件接收者创建一个地址
				Address[] ccAdresses = new InternetAddress[mailInfo.getCcs().length];
				for (int i = 0; i < mailInfo.getCcs().length; i++) {
					ccAdresses[i] = new InternetAddress(mailInfo.getCcs()[i]);
				}
				// 将抄送者信息设置到邮件信息中，注意类型为Message.RecipientType.CC
				mailMessage.setRecipients(Message.RecipientType.CC, ccAdresses);
			}

			mailMessage.setSubject(mailInfo.getSubject());// 设置邮件消息的主题
			mailMessage.setSentDate(new Date());// 设置邮件消息发送的时间

			// MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();// 创建一个包含HTML内容的MimeBodyPart
			// 设置HTML内容
			messageBodyPart.setContent(mailInfo.getContent(), "text/html; charset=GBK");
			mainPart.addBodyPart(messageBodyPart);

			// 存在附件
			String[] filePaths = mailInfo.getAttachFileNames();
			if (filePaths != null && filePaths.length > 0) {
				for (String filePath : filePaths) {
					messageBodyPart = new MimeBodyPart();
					File file = new File(filePath);
					if (file.exists()) {// 附件存在磁盘中
						FileDataSource fds = new FileDataSource(file);// 得到数据源
						messageBodyPart.setDataHandler(new DataHandler(fds));// 得到附件本身并至入BodyPart
						messageBodyPart.setFileName(MimeUtility.encodeText(file.getName(), "GBK", "B"));// 得到文件名同样至入BodyPart
						mainPart.addBodyPart(messageBodyPart);
					}
				}
			}

			// 将MimeMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			Transport.send(mailMessage);// 发送邮件
			msg.setMsgCode("Y");
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsgCode("N");
			msg.setMsgDesc(e.toString());
		}
		return msg;
	}

}