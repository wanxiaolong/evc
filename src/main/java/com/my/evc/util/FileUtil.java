package com.my.evc.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
@Slf4j
public class FileUtil {
	
	/**
	 * 处理文件上传的请求。
	 */
	public static List<String> handleUploadFile(List<FileItem> list) throws IOException {
		//上传文件到指定目录
		List<String> fileNames = new ArrayList<String>();
		for (FileItem item : list) {
			String fileName = item.getName();
			if (!StringUtils.isEmpty(fileName)) {//判断是否选择了文件
				File targetFile = new File(SystemConfig.FILE_UPLOAD_PATH, fileName);//获取根目录对应的真实物理路径
				if (!targetFile.exists()) {
					targetFile.createNewFile();
				}
				copyStream(item.getInputStream(), new FileOutputStream(targetFile));
				fileNames.add(fileName);
			}
		}
		return fileNames;
	}
	
	/**
	 * 调用ExcelUtil.getHeaderRow，从Excel文件中取出表头。只支持.xls和.xlsx文件。
	 */
	public static Map<Integer, String> handleExcelFile(FileItem fileItem) throws BusinessException, IOException {
		String fileName = fileItem.getName();
		//只接受.xls和.xlsx文件，否则报错
		if (StringUtils.isEmpty(fileName)) {
			throw new BusinessException(ErrorEnum.INVALID_EXCEL_EMPTY_FILE_NAME);
		}

		String fileExtension = fileName.substring(fileName.lastIndexOf("."));
		if (!Constant.ALLOWED_FILE_EXTENSION.contains(fileExtension)) {
			throw new BusinessException(ErrorEnum.INVALID_EXCEL_UNSUPPORTED_TYPE);
		}
		InputStream in = fileItem.getInputStream();
		//先检查该Excel的表头是否符合要求
		Map<Integer, String> headerMap = ExcelUtil.getHeaderRow(ExcelUtil.getSheet0(in, fileName));
		return headerMap;
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
	}
	
	/**
	 * 通过Apache fileupload工具包来解析请求，以获得可操作的FileItem对象。
	 */
	public static List<FileItem> parseRequest(HttpServletRequest request) throws FileUploadException {
		//通过Apache fileupload工具包来解析请求，以获得可操作的FileItem对象。
		DiskFileItemFactory factory = new DiskFileItemFactory();//基于磁盘文件项目创建一个工厂对象
		ServletFileUpload upload = new ServletFileUpload(factory);//创建一个新的文件上传对象
		List<FileItem> items = upload.parseRequest(request);//解析上传请求
		return items;
	}

	/**
	 * 解析请求，只返回文件。(附加字段将被忽略)
	 */
	public static List<FileItem> parseRequestIgnoreFormField(HttpServletRequest request) throws FileUploadException {
		List<FileItem> items = parseRequest(request);
		List<FileItem> filteredItems = new ArrayList<FileItem>();
		for (FileItem item : CollectionUtils.emptyIfNull(items)) {
			if (!item.isFormField()) {
				filteredItems.add(item);
			}
		}
		return filteredItems;
	}
	
	/**
	 * 先把上传的文件保存在临时文件夹里。
	 */
	public static File saveStreamToFile(FileItem item, String pathToFile) throws FileNotFoundException, IOException {
		File file = new File(pathToFile);
		if(file.exists()) {
			file.delete();
		}
		file.getParentFile().mkdirs();
		file.createNewFile();
		copyStream(item.getInputStream(), new FileOutputStream(file));
		return file;
	}

	public static void main(String[] args) throws Exception {
		File file = new File(SystemConfig.SCORE_UNZIP_PATH + File.separator + "2018_2019上学期.zip");
		unzip(file);
	}
	
	/**
	 * 解压文件。
	 * 需要注意的是，创建ZipFile对象的时候需要制定Charset，否则会使用默认的UTF-8编码，此时如果文件夹中有中文字符，
	 * 则会出现<pre>java.lang.IllegalArgumentException: MALFORMED</pre>的错误。详情参见help.txt文档的#20。<br>
	 * <p>这里要求解压后的文件夹是基于“学期”命名的，比如“2018_2019上学期”，而里面包含了该学期的所有考试成绩的Excel，
	 * 文件的名字即为考试名字，比如“第一学月考试.xlsx”。</p>
	 */
	public static void unzip(File file) throws IOException {
		ZipFile zip = new ZipFile(file, Charset.forName("GBK"));
		//确保目标文件夹存在
		new File(SystemConfig.SCORE_UNZIP_PATH).mkdir();
		Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
		log.info("当前文件：" + file.getPath());
		while (zipFileEntries.hasMoreElements()) {
			ZipEntry entry = zipFileEntries.nextElement();
			String entryName = entry.getName();
			log.info("正在提取：" + entryName);

			//entryName是基于压缩文件的路径，可能包含多层子目录。比如，
			//压缩文件的根目录为path，那么entryName可以为path/sub1/sub2/file1.txt
			File destFile = new File(SystemConfig.SCORE_UNZIP_PATH, entryName);
			
			//这里需要确保文件不存在，而且父目录存在
			if(destFile.exists()) {
				destFile.delete();
			}
			destFile.getParentFile().mkdirs();

			if (entry.isDirectory()) {
				//如果该entry是文件夹
				destFile.mkdir();
			} else {
				//如果该entry是文件
				int currentByte;
				byte buffer[] = new byte[2048];
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
				BufferedInputStream bis = new BufferedInputStream(zip.getInputStream(entry));
				while ((currentByte = bis.read(buffer)) != -1) {
					bos.write(buffer, 0, currentByte);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
		}
		zip.close();
	}

	/**
	 * 根据文件名，从文件上传的文件夹中删除文件。
	 */
	public static void deleteFile(String name) {
		File file  = new File(SystemConfig.FILE_UPLOAD_PATH + File.separator + name);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 下载文件。
	 */
	public static long handleDownloadFile(String path, ServletOutputStream out) throws BusinessException, IOException {
		File file = new File(path);
		if (!file.exists()) {
			throw new BusinessException(ErrorEnum.FILE_NOT_EXISTS);
		}
		InputStream in = new FileInputStream(file);
		copyStream(in, out);
		in.close();
		return file.length();
	}
}
