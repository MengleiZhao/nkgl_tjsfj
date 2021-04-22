package com.braker.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;

import com.braker.common.ftp.FileUpload;

public class FtpUtils {

	/**
	 * 编码。默认UTF-8
	 */
	private static final String ENCODING = "UTF-8";

	/**
	 * 文件上传
	 * 
	 * @param path 上传的路径
	 * @param ext  文件扩展名
	 * @param in
	 * @return
	 */
	public static String storeByExt(String path, String ext, InputStream in) {
		String filename = path + UUID.randomUUID() + ext;
		store(filename, in);
		return filename;
	}
	
	/**
	 * 文件上传（缩略图）
	 * 
	 * @param path 上传的路径
	 * @param in  文件流
	 * @param fn  原文件名称
	 * @return
	 */
	public static String storeByExtThumb(String path, InputStream in, String fn) {
		String filename = path + fn;
		store(filename, in);
		return filename;
	}

	/**
	 * 上传文件到ftp
	 * 
	 * @param remote 文件名称
	 * @param in
	 * @return
	 */
	private static int store(String remote, InputStream in) {
		try {
			FTPClient ftp = getClient();
			if (ftp != null) {
				String filename = remote;
				String name = FilenameUtils.getName(filename);
				String path = FilenameUtils.getFullPath(filename);
				// 切换到path路径下
				if (!ftp.changeWorkingDirectory(path)) {
					// path路径不存在。进行创建
					String[] ps = StringUtils.split(path, '/');
					String p = "/";
					ftp.changeWorkingDirectory(p);
					for (String s : ps) {
						p += s + "/";
						if (!ftp.changeWorkingDirectory(p)) {
							// 判断 s 文件夹是否存在,不存在才会执行这行代码
							ftp.makeDirectory(s);
							ftp.changeWorkingDirectory(p);
						}
					}
				}
				ftp.storeFile(name, in);
				ftp.logout();
				ftp.disconnect();
			}
			in.close();
			return 0;
		} catch (SocketException e) {
			e.printStackTrace();
			return 3;
		} catch (IOException e) {
			e.printStackTrace();
			return 4;
		}
	}

	/**
	 * 获取ftp连接客户端
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	private static FTPClient getClient() throws SocketException, IOException {
		FTPClient ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		// 端口
		int port = Integer.parseInt(getFtpConfig("port"));
		ftp.setDefaultPort(port);
		// IP
		String url = getFtpConfig("url");
		ftp.connect(url);
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			System.out.println("FTP server refused connection: {}");
			ftp.disconnect();
			return null;
		}
		// 账号
		String username = getFtpConfig("username");
		// 密码
		String password = getFtpConfig("password");
		if (!ftp.login(username, password)) {
			System.out.println("FTP server refused login: {}, user: {}");
			ftp.logout();
			ftp.disconnect();
			return null;
		}
		ftp.setControlEncoding(ENCODING);
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		return ftp;
	}

	/**
	 * ftp服务器配置信息读取
	 * 
	 * @param key
	 * @return
	 * @author wanping
	 * @createtime 2021年1月11日
	 * @updator wanping
	 * @updatetime 2021年1月11日
	 */
	public static String getFtpConfig(String key) {
		String value = null;
		InputStream in = null;
		try {
			Properties properties = new Properties();
			in = FileUpload.class.getClassLoader().getResourceAsStream("ftp.properties");

			properties.load(in);
			value = properties.getProperty(key);
		} catch (Exception e) {
			
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	/**
	 *
	 * 功能：根据文件名称，下载文件流
	 * 
	 * @param filename 文件名
	 * @return
	 * @throws IOException
	 */
	public static void download(String filename, String originalName, HttpServletResponse response) throws IOException {
		// 获取文件名称
		String[] strs = filename.split("/");
		String downloadFile = URLEncoder.encode(originalName, "UTF-8");
		downloadFile = downloadFile.replaceAll("\\+", "%20");
		try {
			// 设置文件ContentType类型，这样设置，会自动判断下载文件类型
			response.setContentType("application/x-msdownload");
			// 设置文件头：最后一个参数是设置下载的文件名并编码为UTF-8
			response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
			// 建立连接
			FTPClient ftp = getClient();
			// 此句代码适用Linux的FTP服务器
			String newPath = new String(filename.getBytes("GBK"), "ISO-8859-1");
			// ftp文件获取文件
			InputStream is = null;
			BufferedInputStream bis = null;
			try {
				is = ftp.retrieveFileStream(newPath);
				bis = new BufferedInputStream(is);
				OutputStream out = response.getOutputStream();
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = bis.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (ftp != null) {
					ftp.logout();
					ftp.disconnect();
				}
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FTPConnectionClosedException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 * 功能：根据文件名称，下载图片
	 * 
	 * @param filename 文件名
	 * @return
	 * @throws IOException
	 */
	public static void downloadImg(String filename, String originalName, HttpServletResponse response) throws IOException {
		// 获取文件名称
		String[] strs = filename.split("/");
		String downloadFile = URLEncoder.encode(originalName, "UTF-8");
		downloadFile = downloadFile.replaceAll("\\+", "%20");
		try {
			// 设置文件ContentType类型，这样设置，会自动判断下载文件类型
			//response.setContentType("application/x-msdownload");
			response.setContentType("text/html; charset=UTF-8");
			// 设置文件头：最后一个参数是设置下载的文件名并编码为UTF-8
			//response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
			// 建立连接
			FTPClient ftp = getClient();
			// 此句代码适用Linux的FTP服务器
			String newPath = new String(filename.getBytes("GBK"), "ISO-8859-1");
			// ftp文件获取文件
			InputStream is = null;
			BufferedInputStream bis = null;
			try {
				is = ftp.retrieveFileStream(newPath);
				bis = new BufferedInputStream(is);
				OutputStream out = response.getOutputStream();
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = bis.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (ftp != null) {
					ftp.logout();
					ftp.disconnect();
				}
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FTPConnectionClosedException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @param filename
	 * @return
	 * @author wanping
	 * @createtime 2021年1月12日
	 * @updator wanping
	 * @updatetime 2021年1月12日
	 */
	public static boolean deleteFile(String fileName) {
		boolean flag = false;
		// 建立连接
		FTPClient ftp = new FTPClient();
		try {
			ftp = getClient();
//			ftp.changeWorkingDirectory(filePath);
//			ftp.dele(filename);
			ftp.deleteFile(fileName);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.logout();
					ftp.disconnect();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
