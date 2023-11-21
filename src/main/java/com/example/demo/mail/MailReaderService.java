package com.example.demo.mail;

import jakarta.mail.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class MailReaderService {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String pwd;

    /*@Value("${spring.mail.location}")
    private String saveDirectory;*/

    /**
     * Google gmail에 접근하여 지정한 기간 내에 모든 메일 가져오기
     *
     * @return
     * @throws MessagingException
     */
    public List<Mail> receiveMailAttachedFile(String userName, String password, Date startDate, Date endDate) throws MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, pwd);

            // 받은편지함을 INBOX 라고 한다.
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // 변경된 부분: 전체 메일을 가져오기 위해 ReceivedDateTerm 사용하지 않음
            Message[] arrayMessages = inbox.getMessages();

            for (int i = arrayMessages.length; i > 0; i--) {
                Message msg = arrayMessages[i - 1];
                Address[] fromAddress = msg.getFrom();
                // 나머지 코드는 그대로 유지
                // ...
            }

            // disconnect
            inbox.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.exit(2);
        }
        return null;
    }

    private Mail convertToEmail(Message msg) throws MessagingException, IOException {
        // Mail 객체로 변환하는 코드는 이전에 작성한 코드에서 가져오면 됩니다.
        // 이메일 정보를 가지고 Mail 객체를 생성하고 반환하는 메소드를 작성하여 사용합니다.
        // ...

        return new Mail(); // 적절한 반환값으로 수정해야 합니다.
    }
}