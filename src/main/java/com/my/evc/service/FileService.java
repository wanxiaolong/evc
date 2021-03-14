package com.my.evc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.my.evc.common.ErrorEnum;
import com.my.evc.common.SystemConfig;
import com.my.evc.exception.BusinessException;
import com.my.evc.util.FileUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
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

	/**
	 * 处理上传文件。
	 */
	public List<String> uploadFile(List<FileItem> list) throws BusinessException, IOException {
		//如果有超大文件，报错
		if (containLargeFiles(list)) {
			throw new BusinessException(ErrorEnum.FILE_TOO_LARGE);
		}

		//如果与已有文件名重复，报错
		if (containDuplicateFiles(list)) {
			throw new BusinessException(ErrorEnum.FILE_ALREADY_EXISTS);
		}

		//保存文件
		List<String> uploadedFileNames = FileUtil.handleUploadFile(list);
		//创建数据库记录
		createFiles(uploadedFileNames);
		//返回上传成功的文件名
		return uploadedFileNames;
	}

	/**
	 * 检查列表中是否有超大的文件。
	 */
	private boolean containLargeFiles(List<FileItem> items) {
		return CollectionUtils.emptyIfNull(items).stream()
				.filter(item -> item.getSize() > SystemConfig.FILE_MAX_SIZE)
				.collect(Collectors.toList()).size() > 0;
	}

	/**
	 * 检查列表中的文件名，是否在DB中已经存在。
	 */
	private boolean containDuplicateFiles(List<FileItem> items) {
		//找到DB中的所有文件名
		List<File> files = fileMapper.findAll();
		List<String> existingFileNames = CollectionUtils.emptyIfNull(files).stream()
				.map(file -> file.getName())
				.collect(Collectors.toList());
		//过滤出匹配的文件
		List<FileItem> duplicated = CollectionUtils.emptyIfNull(items).stream()
				.filter(item -> existingFileNames.contains(item.getName()))
				.collect(Collectors.toList());
		return !duplicated.isEmpty();
	}

	/**
	 * 根据上传好的文件名，批量上传到数据库中供查询
	 * @param fileNames 文件名列表
	 */
	private int createFiles(List<String> fileNames) {
		if (fileNames.isEmpty()) {
			return 0;
		}
		List<File> files = new ArrayList<File>();
		for (String name : fileNames) {
			File file = new File();
			file.setName(name);
			file.setType(name.substring(name.lastIndexOf(".") + 1).toLowerCase());
			files.add(file);
		}
		return fileMapper.createBatch(files);
	}
}
