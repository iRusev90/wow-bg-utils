package bg.wow.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailClient {
	private String senderUsername;
	private String senderPassword;
	
	public GmailClient(String senderUsername, String senderPassword) {
		this.senderUsername = senderUsername;
		this.senderPassword = senderPassword;
	}
	
    public void sendFromGMail(String[] to, String subject, String body) throws MessagingException {
        Properties props = System.getProperties();
        props.put("mail.imaps.ssl.trust", "*");
        props.put("mail.smtp.ssl.trust", "*");
        
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", senderUsername);
        props.put("mail.smtp.password", senderPassword);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
   
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(senderUsername));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, senderUsername, senderPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
        	ae.printStackTrace();
        	throw new AddressException();
        }
        catch (MessagingException me) {
            me.printStackTrace();
            throw new MessagingException();
        }
    }
}
