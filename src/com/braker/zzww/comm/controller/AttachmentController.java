package com.braker.zzww.comm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.braker.common.page.Result;
import com.braker.common.util.Base64Util;
import com.braker.common.util.FileUpLoadUtil;
import com.braker.common.util.FtpUtils;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.SystemCenterMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.User;
import com.braker.fckeditor.UploadRule;
import com.braker.zzww.comm.entity.Attachment;
import com.braker.zzww.comm.manager.AttachmentMng;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/attachment")
public class AttachmentController extends BaseController{
	
	@Autowired
	private AttachmentMng attachmentMng;
	@Autowired
	private UserMng userMng;
	
	/*
	 * 通用的上传附件
	 * @author 安达
	 * @createtime 2018-11-28
	 * @updatetime 2018-11-28
	 */
	@RequestMapping("/uploadAtt")
	@ResponseBody
	public Result uploadAtt(
			ModelMap model,
			String serviceType,
			String pathNum,
			String foreseeableUser,
			@RequestParam(value = "attFiles", required = false) MultipartFile[] attFiles) {

		try {
			//如果是上传制度中心文件，判断文件类型
			/*if ("zdsy".equals(serviceType)) {
				for (int i = 0; i < attFiles.length; i++) {
					String fileName = attFiles[i].getOriginalFilename();
					if (fileName != null && !fileName.contains(".pdf")) {
						return getJsonResult(false, "请确保上传文档均为pdf格式！");
					}
				}
			}*/
			// 保存附件到服务器
			String ids = attachmentMng.uploadAjax(attFiles, serviceType,
					FileUpLoadUtil.getSavePathByName(pathNum), getUser());
			if (ids != null) {
				return getJsonResult(true, ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, e.getMessage());
		}
		return null;
	}
	/*
	 * 签字的上传附件
	 * upladFileQZ(obj, "qz", "qz01",j,"57"); 前台的上传方法
	 * @author 赵孟雷
	 * @createtime 2020-06-03
	 * @updatetime 2020-06-03
	 */
	@RequestMapping("/uploadAttQZ")
	@ResponseBody
	public Result uploadAttQZ(
			ModelMap model,
			String serviceType,
			String pathNum,
			String foreseeableUser,
			@RequestParam(value = "attFiles", required = false) MultipartFile[] attFiles) {
		
		try {
			//如果是上传制度中心文件，判断文件类型
			/*if ("zdsy".equals(serviceType)) {
				for (int i = 0; i < attFiles.length; i++) {
					String fileName = attFiles[i].getOriginalFilename();
					if (fileName != null && !fileName.contains(".pdf")) {
						return getJsonResult(false, "请确保上传文档均为pdf格式！");
					}
				}
			}*/
			// 保存附件到服务器
			String ids = attachmentMng.uploadAjaxQZ(attFiles, serviceType,
					FileUpLoadUtil.getSavePathByName(pathNum), getUser(),foreseeableUser);
			if (ids != null) {
				return getJsonResult(true, ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, e.getMessage());
		}
		return null;
	}
	
	@RequestMapping("/uploadImg")
	@ResponseBody
	public Result uploadImg(@RequestParam(value="uploadFile",required=false) MultipartFile uploadFile,ModelMap model){
		String uploadFileFileName=uploadFile.getOriginalFilename();
		if (StringUtil.isEmpty(uploadFileFileName) || uploadFile.getSize()==0) {
			return getJsonResult(false,"上传失败！没有找到上传文件");
		}
		int suffixIndex = uploadFileFileName.lastIndexOf('.');
		if (suffixIndex == -1) {
			return getJsonResult(false,"上传失败！文件没有后缀名，不允许上传！");
		}
		String suffix=uploadFileFileName.substring(suffixIndex+1).toLowerCase();
		UploadRule rule=new UploadRule(null,null);
		if (!rule.getAcceptImg().contains(suffix)) {
			return getJsonResult(false,"上传失败！该后缀名不允许上传：" + suffix);
		}
		try {
			Attachment bean=attachmentMng.upload(null,uploadFile,getSavePath(),getUser());
			return getJsonResult(true,bean.getId()+","+bean.getFileUrl());
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false,"出现错误，请联系管理员！");
		}
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable String id){
		try {
			Attachment bean=attachmentMng.findById(id);
			bean.setFlag("0");
			bean.setUpdator(getUser());
			bean.setUpdateTime(new Date());
			attachmentMng.updateDefault(bean);
			// 删除文件
			String fileName = bean.getFileName();
			FtpUtils.deleteFile(fileName);
		} catch (Exception e) {
			
			e.printStackTrace();
			return getJsonResult(false,"删除失败,请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");
	}
	
	@RequestMapping("/deleteFromApp")
	@ResponseBody
	public Result deleteFromApp(String id,String userId){
		try {
			User user =userMng.findById(userId);
			Attachment bean=attachmentMng.findById(id);
			bean.setFlag("0");
			bean.setUpdator(user);
			bean.setUpdateTime(new Date());
			attachmentMng.updateDefault(bean);
		} catch (Exception e) {
			
			e.printStackTrace();
			return getJsonResult(false,"删除失败,请联系管理员！");
		}
		return getJsonResult(true,"删除成功！");
	}
	
	private String getSavePath(){
		return request.getServletContext().getRealPath(FileUpLoadUtil.getSavePath());
	}
	
	/*private String getJyywSavePath(){
		return request.getServletContext().getRealPath(FileUpLoadUtil.getJyywSavePath());
	}
	
	private String getFxpgSavePath(){
		return request.getServletContext().getRealPath(FileUpLoadUtil.getFxpgSavePath());
	}
	
	private String getXstbSavePath(){
		return request.getServletContext().getRealPath(FileUpLoadUtil.getXstbSavePath());
	}
	
	private String getGrwwSavePath(){
		return request.getServletContext().getRealPath(FileUpLoadUtil.getGrwwSavePath());
	}
	
	private String getJcxxSavePath(){
		return request.getServletContext().getRealPath(FileUpLoadUtil.getJcxxSavePath());
	}
	
	private String getReceptionSavePath(){
		return request.getServletContext().getRealPath(FileUpLoadUtil.getReceptionSavePath());
	}
	
	private String getLetterReceptionSavePath(){
		return request.getServletContext().getRealPath(FileUpLoadUtil.getLetterReceptionSavePath());
	}
	
	private String getItemNoteSavePath() {
        return request.getServletContext().getRealPath(FileUpLoadUtil.getItemNoteSavePath());
    }*/
	
	private String getDemoSavePath(){
		return request.getServletContext().getRealPath(FileUpLoadUtil.getDemoSavePath());
	}
	
	@RequestMapping("/download/{id}")
	@ResponseBody
	public Result download(@PathVariable String id,HttpServletResponse response){
//		OutputStream out = null;
//		InputStream in = null;
		try {
			Attachment bean=attachmentMng.findById(id);
//			File file = new File(bean.getFileName());
//			File file = new File(getDemoSavePath()+bean.getFileUrl());
//			if(file.exists()){
//				in = new FileInputStream(file);
//				response.setContentType("APPLICATION/OCTET-STREAM");
//				response.setContentLength(in.available());
//				response.setHeader(
//						"Content-Disposition",
//						"attachment; filename=\""
//								+ new String(bean.getOriginalName().getBytes("gbk"), "iso8859-1")
//								+ "\"");
//				out = response.getOutputStream();
//				byte[] bt = new byte[1000];
//				int a; 
//				while ((a = in.read(bt, 0, 1000)) > 0) {
//					out.write(bt, 0, a);
//					out.flush();
//				}
//			}else{
//				return getJsonResult(false,"文件不存在！");
//			}
			String filename = bean.getFileName();
			String originalName = bean.getOriginalName();
			FtpUtils.download(filename, originalName, response);
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@RequestMapping("/downloadImg/{id}")
	@ResponseBody
	public Result downloadImg(@PathVariable String id,HttpServletResponse response){
		try {
			Attachment bean=attachmentMng.findById(id);
			String filename = bean.getFileName();
			String originalName = bean.getOriginalName();
			FtpUtils.downloadImg(filename, originalName, response);
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@RequestMapping("/reView/{id}")
	@ResponseBody
	public void reView(@PathVariable String id,HttpServletResponse response){
		OutputStream out = null;
		InputStream in = null;
		try {
			Attachment bean=attachmentMng.findById(id);
			File file = new File(bean.getFileName());
//			File file = new File(getDemoSavePath()+bean.getFileUrl());
			if(file.exists()){
				in = new FileInputStream(file);
				response.setContentType("application/pdf");
				response.setContentLength(in.available());
				response.setHeader(
						"Content-Disposition",
						"attachment; filename=\""
								+ new String(bean.getOriginalName().getBytes("gbk"), "iso8859-1")
								+ "\"");
				out = response.getOutputStream();
				byte[] bt = new byte[1000];
				int a; 
				while ((a = in.read(bt, 0, 1000)) > 0) {
					out.write(bt, 0, a);
					out.flush();
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/downloadQZ/{id}")
	@ResponseBody
	public Result downloadQZ(@PathVariable String id,HttpServletResponse response){
//		OutputStream out = null;
//		InputStream in = null;
		try {
			List<Attachment> beanList=attachmentMng.findByProperty("foreseeableUser", id);
			if(beanList.isEmpty()){
				return getJsonResult(false,"文件不存在！");
			}
			Attachment bean  = beanList.get(0);
//			File file = new File(bean.getFileName());
//			File file = new File(getDemoSavePath()+bean.getFileUrl());
//			if(file.exists()){
//				in = new FileInputStream(file);
//				response.setContentType("APPLICATION/OCTET-STREAM");
//				response.setContentLength(in.available());
//				response.setHeader(
//						"Content-Disposition",
//						"attachment; filename=\""
//								+ new String(bean.getOriginalName().getBytes("gbk"), "iso8859-1")
//						+ "\"");
//				out = response.getOutputStream();
//				byte[] bt = new byte[1000];
//				int a; 
//				while ((a = in.read(bt, 0, 1000)) > 0) {
//					out.write(bt, 0, a);
//					out.flush();
//				}
//			}else{
//				return getJsonResult(false,"文件不存在！");
//			}
			String filename = bean.getFileName();
			String originalName = bean.getOriginalName();
			FtpUtils.download(filename, originalName, response);
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
		return null;
	}


	@RequestMapping("/downloadFxpg/{id}")
	@ResponseBody
	public Result downloadFxpg(@PathVariable String id,HttpServletResponse response){
		OutputStream out = null;
		InputStream in = null;
		try {
			Attachment bean=attachmentMng.findById(id);
			File file = new File(getSavePath()+bean.getFileUrl());
			if(file.exists()){
				in = new FileInputStream(file);
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setContentLength(in.available());
				response.setHeader(
						"Content-Disposition",
						"attachment; filename=\""
								+ new String(bean.getOriginalName().getBytes("gbk"), "iso8859-1")
								+ "\"");
				out = response.getOutputStream();
				byte[] bt = new byte[1000];
				int a; 
				while ((a = in.read(bt, 0, 1000)) > 0) {
					out.write(bt, 0, a);
					out.flush();
				}
			}else{
				return getJsonResult(false,"文件不存在！");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return getJsonResult(false,"出现错误,请联系管理员！");
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return getJsonResult(true,"操作成功！");
	}
	
	
	@RequestMapping("/downloadXstb/{id}")
	@ResponseBody
	public Result downloadXstb(@PathVariable String id,HttpServletResponse response){
		OutputStream out = null;
		InputStream in = null;
		try {
			Attachment bean=attachmentMng.findById(id);
			File file = new File(getSavePath()+bean.getFileUrl());
			if(file.exists()){
				in = new FileInputStream(file);
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setContentLength(in.available());
				response.setHeader(
						"Content-Disposition",
						"attachment; filename=\""
								+ new String(bean.getOriginalName().getBytes("gbk"), "iso8859-1")
								+ "\"");
				out = response.getOutputStream();
				byte[] bt = new byte[1000];
				int a; 
				while ((a = in.read(bt, 0, 1000)) > 0) {
					out.write(bt, 0, a);
					out.flush();
				}
			}else{
				return getJsonResult(false,"文件不存在！");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return getJsonResult(false,"出现错误,请联系管理员！");
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return getJsonResult(true,"操作成功！");
	}
	
	/**
	 * 通用下载附件
	 * @author ZhangXun
	 * @since 2016-12
	 * @param id 附件id
	 * @param type 附件功能类型
	 * @return
	 */
	@RequestMapping("/downloadAtt/{id}&&{type}")
	@ResponseBody
	public Result downloadAtt(@PathVariable String id, @PathVariable String type,HttpServletResponse response){
		OutputStream out = null;
		InputStream in = null;
		try {
			Attachment bean=attachmentMng.findById(id);
			String savePath = "";
			if(!StringUtil.isEmpty(type) && type.equals("grww")){
				savePath = getSavePath();
			}
			if(!StringUtil.isEmpty(type) && type.equals("jcxx")){
				savePath = getSavePath();
			}
			if("reception".equals(type)){
				savePath = getSavePath();
			}
			if("letterReception".equals(type)){
				savePath = getSavePath();
			}
			if ("itemNote".equals(type)) {
                savePath = getSavePath();
            }
			File file = new File(savePath+bean.getFileUrl());
			if(file.exists()){
				in = new FileInputStream(file);
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setContentLength(in.available());
				response.setHeader(
						"Content-Disposition",
						"attachment; filename=\""
								+ new String(bean.getOriginalName().getBytes("gbk"), "iso8859-1")
								+ "\"");
				out = response.getOutputStream();
				byte[] bt = new byte[1000];
				int a; 
				while ((a = in.read(bt, 0, 1000)) > 0) {
					out.write(bt, 0, a);
					out.flush();
				}
			}else{
				return getJsonResult(false,"文件不存在！");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return getJsonResult(false,"出现错误,请联系管理员！");
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return getJsonResult(true,"操作成功！");
	}
	
	
	/*
	 * 发票的上传附件
	 * @author 沈帆
	 * @createtime 2020-02-26
	 * @updatetime 2020-02-26
	 */
	@RequestMapping("/uploadFp")
	@ResponseBody
	public Result uploadFp(
			ModelMap model,
			String serviceType,
			String pathNum,
			@RequestParam(value = "attFiles", required = false) MultipartFile attFiles,String index) {

		try {
			// 保存附件到服务器
			String id = attachmentMng.uploadAjax1(attFiles, serviceType,
					FileUpLoadUtil.getSavePathByName(pathNum), index,getUser());
			if (id != null) {
				return getJsonResult(true, id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, e.getMessage());
		}
		return null;
	}
	
	/*
	 * app通用的上传附件
	 * @author 沈帆
	 * @createtime 2020-06-28
	 * @updatetime 2020-06-28
	 */
	@RequestMapping("/uploadAttApp")
	@ResponseBody
	public Result uploadAttApp(
			String serviceType,
			String pathNum,
			String base64,String userId,String fileName) {

		try {
			User user =userMng.findById(userId);
			// 保存附件到服务器
			String ids = attachmentMng.uploadAppImage(Base64Util.base64ToMultipart(base64), serviceType,
					FileUpLoadUtil.getSavePathByName(pathNum), user,fileName);
			if (ids != null) {
				return getJsonResult(true, ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, e.getMessage());
		}
		return null;
	}
	@RequestMapping("/uploadAttApp1")
	@ResponseBody
	public Result uploadAttApp1(
			ModelMap model,
			String serviceType,
			String pathNum,String userId,
			@RequestParam(value = "attFiles", required = false) MultipartFile[] attFiles) {

		try {
			User user =userMng.findById(userId);
			// 保存附件到服务器
			String ids = attachmentMng.uploadAjax(attFiles, serviceType,
					FileUpLoadUtil.getSavePathByName(pathNum), user);
			if (ids != null) {
				return getJsonResult(true, ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, e.getMessage());
		}
		return null;
	}
}

