/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamwork.service;

import com.dreamwork.core.FutureMail;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author y.lybarskiy
 */
public class SenderService implements Runnable{
    private boolean stopped;
private MailService service;
 private Thread t;
    public SenderService(MailService service) {
        this.service = service;
        stopped = false;
        
    }
    public void init(){
          t = new Thread(this);
        t.start();
    }
     public void stop() {
        stopped = true;
        try {
            
        } catch (Exception e) {
            
        }
        if (t != null) {
            t.interrupt();
        }
    }

    public void run() {
        while (!stopped) {

            try {
                monitoring();
            } catch (InterruptedException e) {

            }
        }
    }

    public void monitoring() throws InterruptedException {
        Thread.sleep(60000L);
        SessionFactory factory = service.getFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from FutureMail").list();
        for (FutureMail mail : (List<FutureMail>) result) {

            boolean sameDay = DateUtils.isSameDay(new Date(), mail.getWhen());
            if (sameDay) {
                Thread ts = new Thread(new Sender(mail));
                ts.start();
                if (mail.isIsSend()) {
                    session.save(mail);
                }

            }
        }
        session.getTransaction().commit();
        session.close();
    }
}
