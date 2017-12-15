package com.my.evc.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Notice;

@MapperScan
public interface NoticeMapper extends BaseMapper<Notice> {

}
