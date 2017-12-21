package com.my.evc.mapper;

/**
 * 这是所有Mapper类的父类，所有的Mapper类都需要继承它。
 * 这里定义了一些公共的方法，对于某个对象特有的方法，则在自己的接口中定义。
 * 
 * @param <T> 想要操作的参数类型。
 */
public interface BaseMapper<T> {
	
	public void create(T entity);
	public void delete(int id);
	public void update(T entity);
	public T find(int id);
	
}
