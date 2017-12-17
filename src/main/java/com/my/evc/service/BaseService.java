package com.my.evc.service;

import com.my.evc.exception.BaseException;

/**
 * This is the base service interface and all services should implements this BaseService.
 * 
 * @author wxiaolong
 */
public interface BaseService<T> {
	public void create(T entity) throws BaseException;
	public void deleteByID(int id) throws BaseException;
	public void update(T entity) throws BaseException;
	public T findByID(int id) throws BaseException;
}
