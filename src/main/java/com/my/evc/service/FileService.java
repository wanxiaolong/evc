package com.my.evc.service;

import java.util.List;

import com.my.evc.exception.BaseException;
import com.my.evc.model.File;

public interface FileService {
    public void createFile(File file) throws BaseException;
    public void deleteFileByID(int id) throws BaseException;
    public void updateFile(File file) throws BaseException;
    public File findFileByID(int id) throws BaseException;
    public List<File> getFiles() throws BaseException;
}
