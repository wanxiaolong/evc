package com.my.evc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;

import com.my.evc.common.SystemConfig;

/**
 * 文件工具类。
 */
public class FileUtil {
	
	/**
	 * 处理文件上传的请求。
	 */
	public static void handleUploadFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, FileUploadException {
		Iterator<FileItem> itr = parseUploadRequest(request, response);
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (!item.isFormField()) {
				if (item.getName() != null && !item.getName().equals("")) {//判断是否选择了文件
					File file = new File(SystemConfig.FILE_RELATIVE_PATH, item.getName());//获取根目录对应的真实物理路径
					copyStream(item.getInputStream(), new FileOutputStream(file));
				}
			}
		}
	}

	public static List<Map<String,String>> handleUploadScore(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException, FileUploadException {
		Iterator<FileItem> itr = parseUploadRequest(request, response);
		
		List<Map<String, String>> listScore = null;
		String examId = null;
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (item.isFormField()) {
				if ("exam_id".equalsIgnoreCase(item.getFieldName())) {
					examId = item.getString();
				}
			}
			if (!item.isFormField()) {
				String fileName = item.getName();
				//只接受.xls和.xlsx文件
				if (!StringUtils.isEmpty(fileName) && 
						(fileName.endsWith(".xlsx") || fileName.endsWith(".xls"))) {
					listScore = ExcelUtil.readExcel(item.getInputStream(), item.getName());
				} else {
					throw new IOException("只能上传.xlsx和.xls类型的文件。当前文件名为：" + fileName);
				}
			}
		}
		if (listScore != null && examId != null) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("exam_id", examId);
			listScore.add(parameters);
		}
		return listScore;
	}
	
	/**
	 * 通过Apache fileupload工具包来解析请求，以获得可操作的FileItem对象。
	 */
	private static Iterator<FileItem> parseUploadRequest(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, FileUploadException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		DiskFileItemFactory factory = new DiskFileItemFactory();//基于磁盘文件项目创建一个工厂对象
		ServletFileUpload upload = new ServletFileUpload(factory);//创建一个新的文件上传对象
		List<FileItem> items = upload.parseRequest(request);//解析上传请求
		Iterator<FileItem> itr = items.iterator();
		return itr;
	}
	
	/**
	 * 拷贝流数据。把数据从输入流写到输出流。
	 */
	private static void copyStream(InputStream in, FileOutputStream out) throws IOException {
		int length=0;
		byte[] buffer = new byte[1024];
		while((length = in.read(buffer))!=-1){
			out.write(buffer,0,length);
		}
		in.close();
		out.close();
	}
}
