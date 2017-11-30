package com.my.evc.model;

/**
 * 描述一个系统管理员。
 */
public class Admin extends BaseModel {
    
    private String username;    //用户名
    private String password;    //密码
    
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
}
