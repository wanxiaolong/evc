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
	
	/**
	 * 批量插入文件数据以提高数据库性能。<br>
	 * @param fileList 待插入的成绩列表。
	 */
	public int createBatch(List<File> fileList);
}
