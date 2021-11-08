package com.pluto.helper;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/4
 */
public class MailHelper {

    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String myEmailAccount = "kevin.h@fanruan.com";
    public static String myEmailPassword = "h8YnS33h76TLPW2p";
    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    public static String myEmailSMTPHost = "smtp.exmail.qq.com";

    // 收件人邮箱（替换为自己知道的有效邮箱）
    public static String[] receiveMailAccount = {"927600510@qq.com"};
    //public static String[] receiveMailAccount = {"927600510@qq.com", "417983429@qq.com"};

    public static void main(String[] args) throws Exception {
        sendMail("2021-11-04", Arrays.asList("/Users/kevin/Works/github/project/gold_code/codeData/result/2021-11-04/1号策略.csv"));
    }

    public static void sendMail(String inputDate, List<String> resultFile) throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        // 端口
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        // Linux下需要设置此项，Windows默认localhost为127.0.0.1
        props.put("mail.smtp.localhost", "127.0.0.1");
        props.put("mail.smtp.ssl.enable", true);

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);                                 // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(inputDate, session, myEmailAccount, receiveMailAccount, resultFile);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session      和服务器交互的会话
     * @param sendMail     发件人邮箱
     * @param receiveMails 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(String inputDate, Session session, String sendMail, String[] receiveMails, List<String> resultFile) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, "神秘代码", "UTF-8"));

        InternetAddress[] receivers = new InternetAddress[receiveMails.length];
        for (int i = 0; i < receiveMails.length; i++) {
            receivers[i] = new InternetAddress(receiveMails[i], "幸运儿", "UTF-8");
        }
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipients(MimeMessage.RecipientType.TO, receivers);

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject("神秘代码(" + inputDate + ")", "UTF-8");

        Multipart mp = new MimeMultipart();
        MimeBodyPart content = new MimeBodyPart();
        content.setContent("今日份神秘代码", "text/html;charset=UTF-8");
        mp.addBodyPart(content);

        for (String file : resultFile) {
            File temp = new File(file);
            if (!temp.exists()) {
                continue;
            }
            MimeBodyPart attachment = new MimeBodyPart();
            // 读取本地文件
            DataHandler dh2 = new DataHandler(new FileDataSource(file));
            // 将附件数据添加到"节点"
            attachment.setDataHandler(dh2);
            // 设置附件的文件名（需要编码）
            attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
            mp.addBodyPart(attachment);
        }


        // 设置内容
        message.setContent(mp);
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

}
