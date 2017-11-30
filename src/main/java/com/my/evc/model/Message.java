package com.my.evc.model;

/**
 * 用户在网站上的留言
 */
public class Message extends BaseModel {
    
    private String type; //留言类型：问题question、建议suggestion、吐槽complain等
    private String title; //标题
    private String contact; //联系方式
    private String content; //内容
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
