package com.my.evc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.my.evc.exception.BaseException;
import com.my.evc.service.FileService;

public class FileDownloadListener implements ServletRequestListener {
    /**
     * Add file download count after a download request is destroyed.
     * this is not to block the download process.
     */
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getServletPath();
        if (uri.startsWith("/files")) {
            ServletContext sc = request.getServletContext();
            XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
            //File service is not "AutoWired" because it could be null.
            //See: https://www.cnblogs.com/digdeep/p/4770004.html
            FileService fileService = (FileService) cxt.getBean("fileService");
            String fileName = uri.substring(uri.lastIndexOf('/') + 1);
            try {
                fileService.addDownloadCountByName(fileName);
            } catch (BaseException e) {
                e.printStackTrace();
            }
        }
    }

    public void requestInitialized(ServletRequestEvent sre) {
        
    }
}
