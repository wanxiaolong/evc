package com.my.evc.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.File;

@MapperScan
public interface FileMapper extends BaseMapper<File> {
    public List<File> listFiles();
    public File findByName(String name);
}
