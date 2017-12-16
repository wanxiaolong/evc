package com.my.evc.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Notice;

@MapperScan
public interface NoticeMapper extends BaseMapper<Notice> {
    public List<Notice> listNotices();
}
