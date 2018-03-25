package com.my.evc.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.File;

@MapperScan
public interface FileMapper extends BaseMapper<File> {
	/**
	 * 列出所有文件。
	 */
	public List<File> findAll();
	
	/**
	 * 按文件名称查找文件。
	 * @param name 要查找的文件名。
	 */
	public File findByName(String name);
}
