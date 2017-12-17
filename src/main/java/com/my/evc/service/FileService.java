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
	 * When user download file, add the download count.
	 * @param id the Primary Key of the file.
	 */
	public synchronized void addDownloadCountById(int id) throws BaseException {
		File file = findByID(id);
		addDownloadCount(file);
	}
	
	public synchronized void addDownloadCountByName(String name) throws BaseException {
		File file = findByName(name);
		addDownloadCount(file);
	}
	
	/**
	 * Add download count only if the file is founded. 
	 * For 404 page(download file not found), just ignore this step.
	 */
	private void addDownloadCount(File file) throws BaseException {
		if (file != null) {
			int newCount = file.getDownloadCount() + 1;
			file.setDownloadCount(newCount);
			update(file);
		}
	}

	public List<File> listFiles() throws BaseException {
		return fileMapper.listFiles();
	}

}
