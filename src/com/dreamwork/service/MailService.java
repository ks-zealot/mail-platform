package com.dreamwork.service;

import com.dreamwork.core.FutureMail;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author y.lybarskiy
 */
public class MailService implements MailServiceMBean {

    private SessionFactory factory;
    private SenderService service;

    public SessionFactory getFactory() {
        return factory;
    }

    public void start() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties());
        SessionFactory factory = configuration.buildSessionFactory(builder.build());
        service = new SenderService(this);
    }

    public void stop() {
        service.stop();
        factory.close();
    }

    @Override
    public void receiveMessage(String to, Date when, String message) {
        FutureMail mail = new FutureMail();
        mail.setTo(to);
        mail.setWhen(when);
        mail.setText(message);
        mail.setDatereceive(new Date());
        storeMail(mail);
    }

    private void storeMail(FutureMail mail) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(mail);
        session.getTransaction().commit();
        session.close();
    }

}
