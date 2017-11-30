package com.my.evc.mapper;

/**
 * This is a parent mapper class of each model. 
 * Please note that basic operations have been extracted into this class. 
 * Subclasses only define special operations.
 *
 * @param <T> The model type you want to operate.
 */
public interface BaseMapper<T> {
    
    public void create(T entity);
    public void delete(int id);
    public void update(T entity);
    public T find(int id);
    
}
