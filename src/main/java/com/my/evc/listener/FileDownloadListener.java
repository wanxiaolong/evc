package com.my.evc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.my.evc.exception.BaseException;
import com.my.evc.service.FileService;

/**
 * 监听文件下载请求，每次下载都会增加该文件的下载量，并更新到数据库。
 */
@Slf4j
public class FileDownloadListener implements ServletRequestListener {
	/**
	 * 把逻辑放在请求销毁的回调函数里，是不想阻碍下载流程，提高用户体验。
	 * 因为这个请求不用再真正下载文件之前完成。
	 */
	public void requestDestroyed(ServletRequestEvent sre) {
		ServletRequest request = sre.getServletRequest();
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getServletPath();
		if (uri.startsWith("/files")) {
			ServletContext sc = request.getServletContext();
			XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
			//这里用到的FileService没有被自动注入进来。
			//参见： https://www.cnblogs.com/digdeep/p/4770004.html
			FileService fileService = (FileService) cxt.getBean("fileService");
			String fileName = uri.substring(uri.lastIndexOf('/') + 1);
			try {
				fileService.addDownloadCountByName(fileName);
			} catch (BaseException e) {
				log.error("增加下载次数失败", e);
			}
		}
	}

	public void requestInitialized(ServletRequestEvent sre) {
		
	}
}
