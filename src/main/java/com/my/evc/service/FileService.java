package com.my.evc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.FileMapper;
import com.my.evc.model.File;

@Service
@Transactional
public class FileService implements BaseService<File>{
	
	@Autowired
	private FileMapper fileMapper;
	
	public void create(File file) throws BaseException {
		fileMapper.create(file);
	}

	public void deleteByID(int id) throws BaseException {
		fileMapper.delete(id);
	}

	public void update(File file) throws BaseException {
		fileMapper.update(file);
	}

	public File findByID(int id) throws BaseException {
		return fileMapper.find(id);
	}
	
	public File findByName(String name) throws BaseException {
		return fileMapper.findByName(name);
	}
	
	/**
	 * 当用户下载文件的时候，增加文件的下载量。
	 * @param id 文件对象的主键。
	 */
	public synchronized void addDownloadCountById(int id) throws BaseException {
		File file = findByID(id);
		addDownloadCount(file);
	}
	
	/**
	 * 当用户下载文件的时候，增加文件的下载量。
	 * @param name 文件名。
	 */
	public synchronized void addDownloadCountByName(String name) throws BaseException {
		File file = findByName(name);
		addDownloadCount(file);
	}
	
	/**
	 * 抽取的公共方法，只是在文件存在的时候才增加其下载量。
	 * 对于找不到的文件，可视为非法调用，直接忽略即可。
	 */
	private void addDownloadCount(File file) throws BaseException {
		if (file != null) {
			int newCount = file.getDownloadCount() + 1;
			file.setDownloadCount(newCount);
			update(file);
		}
	}

	/**
	 * 供文件列表页面展示。
	 */
	public List<File> findAll() throws BaseException {
		return fileMapper.findAll();
	}

}
