package com.my.evc.common;

/**
 * 这个类持有所有的系统配置。<br>
 * 为了更好的管理配置项，请将配置的名字以模块的名字开头，并且使用全大写。<br>
 * 例如，配置项是文件相关的，则以"FILE_"开头，如果配置项是考试相关的，则以"EXAM_"开头。
 */
public class SystemConfig {
	/**
	 * 最大文件大小：100MB
	 */
	public static final int FILE_MAX_SIZE = 100 * 1024 * 1024;
	/**
	 * 文件上传路径
	 */
	public static String FILE_RELATIVE_PATH = "";
	
	/**
	 * 这个方法由Spring调用，用来注入配置信息。
	 * @see spring-context.xml
	 */
	public static void setFileUploadPath(String fileUploadPath) {
		FILE_RELATIVE_PATH = fileUploadPath;
	}
}