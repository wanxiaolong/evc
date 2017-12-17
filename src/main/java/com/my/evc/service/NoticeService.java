package com.my.evc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.NoticeMapper;
import com.my.evc.model.Notice;
import com.my.evc.type.NoticeImportantLevel;

@Service
@Transactional
public class NoticeService implements BaseService<Notice>{
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	public void create(Notice file) throws BaseException {
		noticeMapper.create(file);
	}

	public void deleteByID(int id) throws BaseException {
		noticeMapper.delete(id);
	}

	public void update(Notice file) throws BaseException {
		noticeMapper.update(file);
	}

	public Notice findByID(int id) throws BaseException {
		Notice notice = noticeMapper.find(id);
		return convertImportantLevel(notice);
	}
	
	public List<Notice> listNotices() throws BaseException {
		List<Notice> notices = noticeMapper.listNotices();
		for (Notice notice : notices) {
			convertImportantLevel(notice);
		}
		return notices;
	}
	
	private Notice convertImportantLevel(Notice notice) {
		int numValue = Integer.parseInt(notice.getImportantLevel());
		notice.setImportantLevel(NoticeImportantLevel.fromValue(numValue).getName());
		return notice;
	}
}
