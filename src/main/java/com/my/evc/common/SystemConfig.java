package com.my.evc.common;


/**
 * Holds all system config. 
 * For better config management, config name should start with a module.
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
	
	public static void setFileUploadPath(String fileUploadPath) {
		FILE_RELATIVE_PATH = fileUploadPath;
	}
}
