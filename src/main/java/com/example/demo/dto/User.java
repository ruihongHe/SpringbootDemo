package com.example.demo.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

private String account;

private String password;

private  String idCard;

private  String name;

private int isDel;


private Date createDate;

private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public Date getCreateDate() {
        return createDate;
    }

 /*   public User(String account, String password, String idCard, String name) {
        this.account = account;
        this.password = password;
        this.idCard = idCard;
        this.name = name;

    }*/

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
/*
    @Override
    public String toString() {
        return String.format(
                "User[id=%d, account='%s', password='%s']",
                id, account,password, idCard, name, isDel, createDate, updateDate);
    }*/


}
