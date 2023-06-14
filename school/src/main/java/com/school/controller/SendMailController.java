package com.school.controller;

import com.school.entity.FormDataDTO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.Security;
import java.util.Properties;

@Controller
public class SendMailController {
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/sendMail")
    public void sendMail(@ModelAttribute FormDataDTO formData, HttpServletResponse response) {
        send(formData);//发送邮件
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>提示</title></head>");
        out.println("<body>");
        out.println("<script type='text/javascript'>");
        out.println("alert('发送成功!');");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");

    }

    @SneakyThrows
    private void send(FormDataDTO formData) {
        String host = "smtp.qq.com";
        int port = 587;
        String username = "3502777299@qq.com";
        String password = "fxtxioywllmrcjbd";
        // 收件人邮箱和发送内容
        String to = "a3502777299@outlook.com";
        String subject = formData.getSubject();
        String text = formData.getMessage();
        // 创建Properties类用于记录相关配置
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        // 创建认证器
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // 创建Session对象
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Session session = Session.getInstance(props, authenticator);

        // 创建邮件消息
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);

        // 发送邮件
        Transport.send(message);

    }
}
