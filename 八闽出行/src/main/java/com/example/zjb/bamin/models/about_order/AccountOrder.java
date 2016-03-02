package com.example.zjb.bamin.models.about_order;

/**
 * Created by zjb on 2016/2/26.
 * 用户的所有订单号
 */
public class AccountOrder {

    /**
     * id : 1
     * date : 1456464567000
     * bookLogAID : 2016-02-26-c4df17f8-0741-4669-bb63-a4c2051d1e03
     * account_id : 4
     * redEnvelope_id : null
     */

    private int id;
    private long date;
    private String bookLogAID;
    private int account_id;
    private Object redEnvelope_id;

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setBookLogAID(String bookLogAID) {
        this.bookLogAID = bookLogAID;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setRedEnvelope_id(Object redEnvelope_id) {
        this.redEnvelope_id = redEnvelope_id;
    }

    public int getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getBookLogAID() {
        return bookLogAID;
    }

    public int getAccount_id() {
        return account_id;
    }

    public Object getRedEnvelope_id() {
        return redEnvelope_id;
    }
}
