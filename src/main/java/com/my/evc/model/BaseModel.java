package com.my.evc.model;

import java.util.Date;

/**
 * 这是所有模型对象的基类，它包含了所有子类公有的属性和方法。
 * 注意这是一个抽象类，它不可实例化。
 */
public abstract class BaseModel {
    
    private int id; //ID标识符，数据库的主键
    private Date creationDate; //创建日期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
