package com.my.evc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.evc.exception.BaseException;
import com.my.evc.mapper.MessageMapper;
import com.my.evc.model.Message;

@Service
@Transactional
public class MessageService implements BaseService<Message>{
	
	@Autowired
	private MessageMapper messageMapper;
	
	public void create(Message message) throws BaseException {
		messageMapper.create(message);
	}

	public void deleteByID(int id) throws BaseException {
		messageMapper.delete(id);
	}

	public void update(Message message) throws BaseException {
		messageMapper.update(message);
	}

	public Message findByID(int id) throws BaseException {
		Message message = messageMapper.find(id);
		return message;
	}
	
	/**
	 * 显示公告列表。
	 */
	public List<Message> listNotices() throws BaseException {
		List<Message> messages = messageMapper.listMessages();
		return messages;
	}
}
