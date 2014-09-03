/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamwork.service;

import com.dreamwork.core.FutureMail;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author y.lybarskiy
 */
public class Sender implements Runnable {

    @Resource(mappedName = "java:jboss/mail/Default")
    private Session mailSession;
    private FutureMail mail;
    private String from;

    public Sender(FutureMail mail) {
        this.mail = mail;
    }

    public FutureMail getMail() {
        return mail;
    }

    @Override
    public void run() {
        try {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress("senderemailaddress.com");
            Address[] to = new InternetAddress[]{new InternetAddress(mail.getTo())};

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, to);
            m.setSubject("Mail from Future");
            m.setSentDate(new java.util.Date());
            m.setContent(mail.getText(), "text/plain");
            Transport.send(m);
            mail.setIsSend(true);
        } catch (AddressException ex) {
            mail.setIsSend(false);
        } catch (MessagingException ex) {
            mail.setIsSend(false);
        }
    }
}
