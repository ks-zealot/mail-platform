package com.dreamwork.service;


import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author y.lybarskiy
 */
public interface MailServiceMBean {
    public void receiveMessage(String to, Date when, String message);
}
