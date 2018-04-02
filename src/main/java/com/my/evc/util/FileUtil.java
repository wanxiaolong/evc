package com.my.evc.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

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
	 * 解压的临时目录 
	 */
	private static final String UPZIP_PATH = "C:\\Users\\万小龙\\Desktop\\upload\\tmp";
	
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
	 * 调用ExcelUtil.getHeaderRow，从Excel文件中取出表头。只支持.xls和.xlsx文件。
	 */
	public static Map<Integer, String> handleFileItem(FileItem fileItem) throws BusinessException, IOException {
		String fileName = fileItem.getName();
		//只接受.xls和.xlsx文件，否则报错
		if (!StringUtils.isEmpty(fileName)) {
			String fileExtension = fileName.substring(fileName.lastIndexOf("."));
			if (Constant.ALLOWED_FILE_EXTENSION.contains(fileExtension)) {
				InputStream in = fileItem.getInputStream();
				//先检查该Excel的表头是否符合要求
				Map<Integer, String> headerMap = ExcelUtil.getHeaderRow(in, fileName);
				return headerMap;
			} else {
				throw new BusinessException(ErrorEnum.INVALID_EXCEL_UNSUPPORTED_TYPE);
			}
		} else {
			throw new BusinessException(ErrorEnum.INVALID_EXCEL_EMPTY_FILE_NAME);
		}
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
	public static Iterator<FileItem> parseFromRequest(HttpServletRequest request) throws FileUploadException {
		//通过Apache fileupload工具包来解析请求，以获得可操作的FileItem对象。
		DiskFileItemFactory factory = new DiskFileItemFactory();//基于磁盘文件项目创建一个工厂对象
		ServletFileUpload upload = new ServletFileUpload(factory);//创建一个新的文件上传对象
		List<FileItem> items = upload.parseRequest(request);//解析上传请求
		Iterator<FileItem> itr = items.iterator();
		return itr;
	}

	/**
	 * 解压缩zip包
	 * @param zipFilePath zip文件的全路径
	 * @param unzipFilePath 解压后的文件保存的路径
	 * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
	 */
	public static void unzip(FileItem fileItem) throws Exception {
		String fileName = fileItem.getName();
		InputStream inputStream = fileItem.getInputStream();
		
		// 创建解压缩文件保存的路径
		File unzipFileDir = new File(UPZIP_PATH);
		if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
			unzipFileDir.mkdirs();
		}

		// 构建压缩包中一个文件解压后保存的文件全路径
		String subFolder = fileName.substring(0, fileName.lastIndexOf(".zip"));
		String entryFilePath = UPZIP_PATH + File.separator + subFolder;
		
		// 创建解压文件
		File entryFile = new File(entryFilePath);
		if (entryFile.exists()) {
			// 检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
			SecurityManager securityManager = new SecurityManager();
			securityManager.checkDelete(entryFilePath);
			// 删除已存在的目标文件
			entryFile.delete();
		}

		// 写入文件
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(entryFile));
		BufferedInputStream bis = new BufferedInputStream(new ZipInputStream(inputStream));
		byte[] buffer = new byte[1024];
		int count = 0;
		while ((count = bis.read(buffer, 0, buffer.length)) != -1) {
			bos.write(buffer, 0, count);
		}
		bos.flush();
		bos.close();
	}
}
