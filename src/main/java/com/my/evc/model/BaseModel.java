package com.my.evc.model;

import java.util.Date;

/**
 * This is the base model class which contains some common attributes for
 * every database model. Note that this class is an <b>abstract</b> class, 
 * models are child class of it.
 */
public abstract class BaseModel {
    private int id;
    private Date creationDate;

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
