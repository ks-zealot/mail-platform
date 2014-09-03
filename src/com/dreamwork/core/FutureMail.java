/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamwork.core;

import java.util.Date;

/**
 *
 * @author y.lybarskiy
 */
public class FutureMail {
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    String to;
    Date when;
    String text;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatereceive() {
        return datereceive;
    }

    public void setDatereceive(Date datereceive) {
        this.datereceive = datereceive;
    }

    public boolean isIsSend() {
        return isSend;
    }

    public void setIsSend(boolean isSend) {
        this.isSend = isSend;
    }
    Date datereceive;
    boolean isSend  = false;
    
}
