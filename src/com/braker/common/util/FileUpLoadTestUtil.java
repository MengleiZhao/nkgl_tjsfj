package com.braker.common.util;

import java.io.InputStream;
import java.util.Properties;
public class FileUpLoadTestUtil {
	private static Properties props = new Properties();
	static {
		InputStream is = FileUpLoadTestUtil.class.getClassLoader().getResourceAsStream("upload.properties");
		try {
			props.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getSavePath(){
		return props.getProperty("upload.save_path");
	}
	
	public static String getRootPath(){
		return props.getProperty("upload.root.save_path");
	}
	
	public static String getDemoSavePath(){
		return getRootPath() + props.getProperty("upload.demo.save_path");
	}
	//预算模块
	//项目库附件
	public static String getXmkSavePath(){
		return getRootPath() + props.getProperty("upload.ysgl01.save_path");
	}
	//"项目评审"功能附件存放目录
	public static String getXMPSSavePath(){
		return getRootPath() + props.getProperty("upload.ysgl02.save_path");
	}
	
	public static void main(String[] args) {
		System.out.println(getXMPSSavePath());
	}
}
