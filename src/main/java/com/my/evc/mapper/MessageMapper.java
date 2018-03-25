package com.my.evc.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Message;

@MapperScan
public interface MessageMapper extends BaseMapper<Message> {
	/**
	 * 查询所有的留言。
	 */
	public List<Message> findAll();
}
