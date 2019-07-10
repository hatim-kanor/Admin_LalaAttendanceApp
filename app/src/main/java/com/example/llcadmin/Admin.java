package com.example.llcadmin;

public class Admin {
    private String id;
    private String name;
    private String lname;
    private String imei;
    private String mobile;
    private String email;

    public Admin(String id,String name,String lname,String imei,String mobile,String email)
    {
        this.id = id;
        this.name = name;
        this.lname = lname;
        this.mobile = mobile;
        this.imei = imei;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
