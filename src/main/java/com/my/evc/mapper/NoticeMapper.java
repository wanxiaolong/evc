package com.my.evc.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Notice;

@MapperScan
public interface NoticeMapper extends BaseMapper<Notice> {
	/**
	 * 查询所有的公告。
	 */
	public List<Notice> listNotices();
}
