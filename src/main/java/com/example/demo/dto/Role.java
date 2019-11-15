package com.example.demo.dto;





import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Role {
@Id
private long id;
@NotNull
private String roleName;
@NotNull
private Date creatDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }
}
