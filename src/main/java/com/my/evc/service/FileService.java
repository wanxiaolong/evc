package com.my.evc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.FileMapper;
import com.my.evc.model.File;
import com.my.evc.service.FileService;

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

    public List<File> listFiles() throws BaseException {
    	//Mock data
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<File> files = new ArrayList<File>();
//        
//        for (int i=0; i< 20; i++) {
//            File file = new File();
//            String fileName = "File"+i+".rar";
//            file.setName(fileName);
//            file.setPath("/files/eng/" + fileName);
//            try {
//                String date = "2017-11-" + (10+i) + " 11:11:11";
//                file.setCreationDate(dateFormat.parse(date));
//            } catch (ParseException e) {}
//            files.add(file);
//        }
//        return files;
    	return fileMapper.listFiles();
    }

}
