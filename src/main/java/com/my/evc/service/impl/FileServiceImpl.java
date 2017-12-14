package com.my.evc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.model.File;
import com.my.evc.service.FileService;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Override
    public void createFile(File file) throws BaseException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteFileByID(int id) throws BaseException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateFile(File file) throws BaseException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public File findFileByID(int id) throws BaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getFiles() throws BaseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<File> files = new ArrayList<>();
        
        for (int i=0; i< 20; i++) {
            File file = new File();
            String fileName = "File"+i+".rar";
            file.setName(fileName);
            file.setPath("/files/eng/" + fileName);
            try {
                String date = "2017-11-" + (10+i) + " 11:11:11";
                file.setCreationDate(dateFormat.parse(date));
            } catch (ParseException e) {}
            files.add(file);
        }
        return files;
    }

}
