package br.com.mribeiro.marylimp;

import java.util.Date;

public class Visit {
    private boolean happened;
    private Date date;
    private String uid;
    private boolean confirmed;

    public Visit(boolean happened, Date date, String uid, boolean confirmed) {
        this.happened = happened;
        this.date = date;
        this.uid = uid;
        this.confirmed = confirmed;
    }

    public boolean isHappened() {
        return happened;
    }

    public void setHappened(boolean happened) {
        this.happened = happened;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
