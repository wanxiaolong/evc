package com.my.evc.model;

/**
 * 描述管理员发布的一个公告，或通知。
 */
public class Notice extends BaseModel {
    
    private String adminId;          //发布者
    private String importantLevel;   //重要性（2=重要，1=一般，0=默认）
    private String title;            //标题
    private String content;          //内容
    
    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public String getImportantLevel() {
        return importantLevel;
    }
    public void setImportantLevel(String importantLevel) {
        this.importantLevel = importantLevel;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
