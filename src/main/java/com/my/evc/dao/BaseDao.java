package com.my.evc.dao;

public interface BaseDao<T> {
    
    public void create(T entity);
    public T findByID(Class<T> clazz, int id);
    
}
