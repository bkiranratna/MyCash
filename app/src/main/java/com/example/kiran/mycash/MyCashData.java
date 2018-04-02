package com.example.kiran.mycash;

public class MyCashData {

    private Accountdetails accountdetails;

    /**
     * No args constructor for use in serialization
     *
     */
    public MyCashData() {
    }


    public MyCashData(Accountdetails accountdetails) {
        super();
        this.accountdetails = accountdetails;
    }

    public Accountdetails getAccountdetails() {
        return accountdetails;
    }

    public void setAccountdetails(Accountdetails accountdetails) {
        this.accountdetails = accountdetails;
    }

    public MyCashData withAccountdetails(Accountdetails accountdetails) {
        this.accountdetails = accountdetails;
        return this;
    }
}
