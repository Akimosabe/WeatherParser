import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;

public class Email {

    public static void sendEmail(String recipient, String subject, String message) throws MessagingException {

        // Настройки почтового сервера

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");


        // Аккаунт
        final String username = "WeatherParserEkb@yandex.ru";
        final String password = "nzwdocfkodgtpvlg";

        // Сессия для отправки письма
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Сообщение
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        msg.setSubject(subject);
        msg.setText(message);

        // Отправка сообщения
        Transport.send(msg);
    }
}