package com.my.evc.model;

/**
 * 描述一个网站的用户对象。
 * 网站用户分两类：<br>
 * 1. 学生家长，或者监护人。这一类用户会有一个studentId相关，表示跟这个学生有关系<br>
 * 2. 跟学生没关系的用户，即社会用户，他们可以给网站留言等。
 */
public class User extends BaseModel {
    
    private String username;      //用户名
    private String password;      //密码
    private String email;         //邮箱
    private int studentId;        //关联的学生

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
