package com.my.evc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;

import com.my.evc.common.Constant;
import com.my.evc.common.ErrorEnum;
import com.my.evc.common.SystemConfig;
import com.my.evc.exception.BusinessException;

/**
 * 文件工具类。
 */
public class FileUtil {
	
	/**
	 * 处理文件上传的请求。
	 */
	public static void handleUploadFile(HttpServletRequest request)
			throws ServletException, IOException, FileUploadException {
		Iterator<FileItem> itr = parseFromRequest(request);
		
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (!item.isFormField()) {
				String fileName = item.getName();
				if (!StringUtils.isEmpty(fileName)) {//判断是否选择了文件
					File file = new File(SystemConfig.FILE_RELATIVE_PATH, fileName);//获取根目录对应的真实物理路径
					copyStream(item.getInputStream(), new FileOutputStream(file));
				}
			}
		}
	}
	
	/**
	 * 处理成绩上传请求。上传的文件只能是.xls或.xlsx结尾的（只能是Excel文件），否则会报异常。<br>
	 * 系统会读取Excel中的数据，并返回一个成绩对象的列表。
	 * 
	 * @return List对象。 List里面是多个Map，每一个Map代表每个学生在某次考试的各科的成绩。
	 * 最后一个Map对象里有对应的考试信息。
	 */
	public static List<Map<String,String>> handleUploadScore(HttpServletRequest request) 
			throws ServletException, IOException, FileUploadException, BusinessException {
		Iterator<FileItem> itr = parseFromRequest(request);
		
		List<Map<String, String>> listScore = null;
		String examId = null;
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (item.isFormField()) {
				if (Constant.PARAM_EXAM_ID.equalsIgnoreCase(item.getFieldName())) {
					examId = item.getString();
				}
			}
			if (!item.isFormField()) {
				String fileName = item.getName();
				//只接受.xls和.xlsx文件，否则报错
				if (!StringUtils.isEmpty(fileName)) {
					String fileExtension = fileName.substring(fileName.lastIndexOf("."));
					if (Constant.ALLOWED_FILE_EXTENSION.contains(fileExtension)) {
						listScore = ExcelUtil.loadExcel(item.getInputStream(), item.getName());
					} else {
						throw new BusinessException(ErrorEnum.INVALID_EXCEL_UNSUPPORTED_TYPE);
					}
				} else {
					throw new BusinessException(ErrorEnum.INVALID_EXCEL_EMPTY_FILE_NAME);
				}
			}
		}
		//如果没有读取到考试信息，直接报错
		if (examId == null) {
			throw new BusinessException(ErrorEnum.ILLEGAL_REQUEST_NO_EXAM_ID);
		}
		//把另外的参数exam_id也放到Map中并添加到List里，以便调用者使用。
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constant.PARAM_EXAM_ID, examId);
		listScore.add(parameters);
		return listScore;
	}
	
	/**
	 * 拷贝流数据。把数据从输入流写到输出流。
	 */
	public static void copyStream(InputStream in, OutputStream out) throws IOException {
		int length=0;
		byte[] buffer = new byte[1024];
		while((length = in.read(buffer))!=-1){
			out.write(buffer,0,length);
		}
		in.close();
		out.close();
	}
	
	/**
	 * 通过Apache fileupload工具包来解析请求，以获得可操作的FileItem对象。
	 */
	private static Iterator<FileItem> parseFromRequest(HttpServletRequest request) throws FileUploadException {
		//通过Apache fileupload工具包来解析请求，以获得可操作的FileItem对象。
		DiskFileItemFactory factory = new DiskFileItemFactory();//基于磁盘文件项目创建一个工厂对象
		ServletFileUpload upload = new ServletFileUpload(factory);//创建一个新的文件上传对象
		List<FileItem> items = upload.parseRequest(request);//解析上传请求
		Iterator<FileItem> itr = items.iterator();
		return itr;
	}

}
