package com.igw.market.common.util.Email;
/**   
* 发送邮件需要使用的基本信息 
* author 
*/    
import java.util.Properties;

public class MailSenderInfo {    
	 // 发送邮件的服务器的IP和端口    
    private String mailServerHost = "10.86.228.5";    
    private String mailServerPort = "25";    
    // 邮件发送者的地址    
    private String fromAddress = "oa-admin@igwfmc.com";    
    // 邮件接收者的地址    
    private String toAddress; 
    //多个收件人
    private String[] toAddressList;  
    // 登陆邮件发送服务器的用户名和密码    
    private String userName = "oa-admin";    
    private String password = "Passw0rd";    
    // 是否需要身份验证    
    private boolean validate = true;    
    // 邮件主题    
    private String subject= "主题";    
    // 邮件的文本内容    
    private String content= " ";   
    // 邮件附件的文件地址    如{"f:lw/20141017","f:lw/20141018"}
    private String[] attachFileNames;
    //抄收人
    private String[] ccs;
    //文件名变更
    private String fileChangge ;
    //密送
    private String[] msAddress;
    //日志发送人
    private String mattersLogEmail;
    //是否加密
    private String isEncrypt;
    //加密密码
    private String encryptPassw;
    
    
	/**   
      * 获得邮件会话属性   
      */    
    public Properties getProperties(){    
      Properties p = new Properties();    
      p.put("mail.smtp.host", this.mailServerHost);    
      p.put("mail.smtp.port", this.mailServerPort);    
      p.put("mail.smtp.auth", validate ? "true" : "false");    
      return p;    
    }    
    
    public String getMattersLogEmail() {
		return mattersLogEmail;
	}

	public void setMattersLogEmail(String mattersLogEmail) {
		this.mattersLogEmail = mattersLogEmail;
	}

	public String[] getMsAddress() {
		return msAddress;
	}
	public void setMsAddress(String[] msAddress) {
		this.msAddress = msAddress;
	}
    public String getMailServerHost() {    
      return mailServerHost;    
    }    
    public void setMailServerHost(String mailServerHost) {    
      this.mailServerHost = mailServerHost;    
    }   
    public String getMailServerPort() {    
      return mailServerPort;    
    }   
    public void setMailServerPort(String mailServerPort) {    
      this.mailServerPort = mailServerPort;    
    }   
    public boolean isValidate() {    
      return validate;    
    }   
    public void setValidate(boolean validate) {    
      this.validate = validate;    
    }   
    public String[] getAttachFileNames() {    
      return attachFileNames;    
    }   
    public void setAttachFileNames(String[] fileNames) {    
      this.attachFileNames = fileNames;    
    }   
    public String getFromAddress() {    
      return fromAddress;    
    }    
    public void setFromAddress(String fromAddress) {    
      this.fromAddress = fromAddress;    
    }   
    public String getPassword() {    
      return password;    
    }   
    public void setPassword(String password) {    
      this.password = password;    
    }   
    public String getToAddress() {    
      return toAddress;    
    }    
    public void setToAddress(String toAddress) {    
      this.toAddress = toAddress;    
    }    
    public String getUserName() {    
      return userName;    
    }   
    public void setUserName(String userName) {    
      this.userName = userName;    
    }   
    public String getSubject() {    
      return subject;    
    }   
    public void setSubject(String subject) {    
      this.subject = subject;    
    }   
    public String getContent() {    
      return content;    
    }   
    public void setContent(String textContent) {    
      this.content = textContent;    
    }
	public String[] getToAddressList() {
		return toAddressList;
	}
	public void setToAddressList(String[] toAddressList) {
		this.toAddressList = toAddressList;
	}   
	public String[] getCcs() {
		return ccs;
	}
	public void setCcs(String[] ccs) {
		this.ccs = ccs;
	}
	public String getFileChangge() {
		return fileChangge;
	}
	public void setFileChangge(String fileChangge) {
		this.fileChangge = fileChangge;
	}

	public String getIsEncrypt() {
		return isEncrypt;
	}

	public void setIsEncrypt(String isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	public String getEncryptPassw() {
		return encryptPassw;
	}

	public void setEncryptPassw(String encryptPassw) {
		this.encryptPassw = encryptPassw;
	} 
	
}   