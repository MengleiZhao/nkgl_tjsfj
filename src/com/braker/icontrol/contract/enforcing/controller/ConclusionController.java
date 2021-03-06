package com.braker.icontrol.contract.enforcing.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.ftp.FileUpload;
import com.braker.common.page.JsonPagination;
import com.braker.common.page.Pagination;
import com.braker.common.page.Result;
import com.braker.common.page.SimplePage;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.CheterMng;
import com.braker.core.manager.DepartMng;
import com.braker.core.model.CheterInfo;
import com.braker.icontrol.contract.Formulation.manager.FormulationMng;
import com.braker.icontrol.contract.Formulation.model.ContractBasicInfo;
import com.braker.icontrol.contract.enforcing.manager.ChangeMng;
import com.braker.icontrol.contract.enforcing.manager.ConclusionAttacMng;
import com.braker.icontrol.contract.enforcing.manager.ConclusionMng;
import com.braker.icontrol.contract.enforcing.manager.UptClauseMng;
import com.braker.icontrol.contract.enforcing.manager.UptMng;
import com.braker.icontrol.contract.enforcing.model.Conclusion;
import com.braker.icontrol.contract.enforcing.model.ConclusionAttac;
import com.braker.icontrol.contract.filing.manager.FilingMng;
import com.braker.workflow.entity.TProcessDefin;
import com.braker.workflow.manager.TProcessDefinMng;

@Controller
@RequestMapping("/Conclusion")
public class ConclusionController extends BaseController{

	@Autowired
	private ChangeMng changeMng;
	@Autowired
	private FilingMng filingMng;
	@Autowired
	private FormulationMng formulationMng;
	@Autowired
	private UptMng uptMng;
	@Autowired
	private UptClauseMng uptAttacMng;
	@Autowired
	private ConclusionMng conclusionMng;
	@Autowired
	private ConclusionAttacMng conclusionAttacMng;
	@Autowired
	private CheterMng cheterMng;
	@Autowired
	private DepartMng departMng;
	@Autowired
	private TProcessDefinMng tProcessDefinMng;
	/**
	 * ??????????????????
	 * @param model
	 * @return
	 * @author crc
	 * @createtime 2018-06-26
	 */
	@RequestMapping("/list")
	public String list(ModelMap model){
		return "/WEB-INF/view/contract/conclusion/conclusion_list";
	}
	
	/**
	 * ?????????????????????
	 * @param contractBasicInfo
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @author crc
	 * @createtime 2018-06-26
	 */
	@RequestMapping("/JsonPagination")
	@ResponseBody
	public JsonPagination jsonPagination(ContractBasicInfo contractBasicInfo,String sort,String order,Integer page,Integer rows,ModelMap model){
		if(page==null){page=1;}
    	if(rows==null){rows=SimplePage.DEF_COUNT;}
		Pagination p = changeMng.queryList(contractBasicInfo, getUser(),page,rows);
		List<ContractBasicInfo> li= (List<ContractBasicInfo>) p.getList();
    	for (int i = 0; i < li.size(); i++) {
			li.get(i).setNumber(i+1);
		}
    	p.setList(li);
		return getJsonPagination(p, page);
	}
	
	/**
	 * ?????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author crc
	 * @createtime 2018-06-26
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id,ModelMap model){
		ContractBasicInfo contractBasicInfo = changeMng.findById(Integer.valueOf(id));
		//????????????
		model.addAttribute("bean",contractBasicInfo);
		model.addAttribute("openType", "edit");
		if(filingMng.find_Sign_number(contractBasicInfo)>0){
			//????????????
			model.addAttribute("signInfo", filingMng.find_Sign(contractBasicInfo).get(0));
		}
		//????????????
		//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
		//?????????????????????????????????id
		String departId=departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",contractBasicInfo.getBeanCode());
		model.addAttribute("attac", formulationMng.findAttac(Integer.valueOf(id)));
		//??????????????????????????????
		if(filingMng.findHTAttac(Integer.valueOf(id)).size()>0){
			model.addAttribute("filingattac", filingMng.findHTAttac(Integer.valueOf(id)));
		}
		//????????????????????????
		if(filingMng.findOtherAttac(Integer.valueOf(id)).size()>0){
			model.addAttribute("filinOthergattac", filingMng.findOtherAttac(Integer.valueOf(id)));
		}
		//???????????????????????????
		if(uptAttacMng.findAllFile(id).size()>0){
			model.addAttribute("changegattac", uptAttacMng.findAllFile(id));
		}
		//??????????????????
		if(uptMng.findByFContId_U(id).size()>0){
			model.addAttribute("Upt", uptMng.findByFContId_U(id).get(0));
		}
		//??????????????????
		if(conclusionMng.findByfContId(id).size()>0){
			model.addAttribute("Conclusion", conclusionMng.findByfContId(id).get(0));
		}
		//?????????????????????
		if( conclusionAttacMng.findAllFile(id).size()>0){
			model.addAttribute("conclusionattac", conclusionAttacMng.findAllFile(id));
		}
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		//????????????????????????
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/contract/conclusion/conclusion_edit";
	}
	
	/**
	 * ??????
	 * @param contractBasicInfo
	 * @param conclusion
	 * @param fFileSrc_C
	 * @return
	 * @author crc
	 * @createtime 2018-06-26
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result finish(ContractBasicInfo contractBasicInfo,Conclusion conclusion,String files4){
		try {
			if(StringUtil.isEmpty(conclusion.getfFiType())){
				return getJsonResult(false, "????????????????????????");
			}else if(StringUtil.isEmpty(files4)){
				return getJsonResult(false, "??????????????????");
			}else{
				String[] conclusionFile= files4.split(",");
				List<ConclusionAttac> conclusionAttac=new ArrayList<ConclusionAttac>();
				for (int i = 0; i < conclusionFile.length; i++) {
					ConclusionAttac CA=new ConclusionAttac(); 
					String[] HT=conclusionFile[i].split("\\\\");
					CA.setfAttacName_C(HT[HT.length-1].trim());
					CA.setfFileSrc_C("HT/HTJX/"+HT[HT.length-1].trim());
					CA.setfAttacType_C(conclusion.getfFiType());
					conclusionAttac.add(CA);
				}
				conclusionMng.saveConclusion(contractBasicInfo, conclusion, getUser(), conclusionAttac);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getJsonResult(false, "????????????????????????????????????");
		}
		return getJsonResult(true, "????????????");
	}
	
	/**
	 * ????????????
	 * @param fileurl
	 * @return
	 */
	@RequestMapping("/conclusionFile")
	@ResponseBody
	public boolean applyFile(String fileurl){
		try {
			fileurl = java.net.URLDecoder.decode(fileurl,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//???????????????
		String[] names = fileurl.split("\\\\");
		String name = names[names.length-1].trim();
		//??????????????????
		FileUpload fu = new FileUpload();
		String url = fu.getFtpConfig("url");
		int port = Integer.parseInt(fu.getFtpConfig("port"));
		String username = fu.getFtpConfig("username");
		String password = fu.getFtpConfig("password");
		boolean flag = false;
		try {
			String path = "HT/HTJX";
			String filename = name.trim();
			String input = fileurl.trim();
			flag=fu.upLoadFromProduction(url, port,username,password,path,filename,input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * ????????????
	 * @param filename
	 * @return
	 */
	@RequestMapping("/conclusionFileDelete")
	@ResponseBody
	public boolean applyFileDelete(String filename){
		//??????????????????
		try {
			filename = java.net.URLDecoder.decode(filename,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] names = filename.split("\\\\");
		filename = names[names.length-1].trim();
		
		//??????????????????????????????????????????
		List<ConclusionAttac> li = conclusionAttacMng.findByFileName(filename);
		//??????????????????????????????????????????
		conclusionAttacMng.deleteByName(li);
		
		FileUpload fu = new FileUpload();
		String url = fu.getFtpConfig("url");
		int port = Integer.parseInt(fu.getFtpConfig("port"));
		String username = fu.getFtpConfig("username");
		String password = fu.getFtpConfig("password");
		String path = "HT/HTJX";
		
		boolean flag = false;
		try {
			fu.delFile(url, port, username, password, path, filename);
			flag = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * ???????????????????????????
	 * @param id
	 * @param model
	 * @return
	 * @author crc
	 * @createtime 2018-06-26
	 */
	@RequestMapping("/detail/{id}")
	public String deatil(@PathVariable String id,ModelMap model){
		ContractBasicInfo contractBasicInfo = changeMng.findById(Integer.valueOf(id));
		//????????????
		model.addAttribute("bean",contractBasicInfo);
		model.addAttribute("openType", "detail");
		if(filingMng.find_Sign_number(contractBasicInfo)>0){
			//????????????
			model.addAttribute("signInfo", filingMng.find_Sign(contractBasicInfo).get(0));
		}
		//????????????
		//model.addAttribute("CheckInfo", checkInfoMng.query(contractBasicInfo.getFcId()));
		//?????????????????????????????????id
		String departId=departMng.findDeptByUserId(contractBasicInfo.getfOperatorId())[0];
		//???????????????id
		TProcessDefin tProcessDefin=tProcessDefinMng.getByBusiAndDpcode("HTND", departId);
		model.addAttribute("fpId", tProcessDefin.getFPId());
		//????????????
		model.addAttribute("foCode",contractBasicInfo.getBeanCode());
		model.addAttribute("attac", formulationMng.findAttac(Integer.valueOf(id)));
		//??????????????????????????????
		if(filingMng.findHTAttac(Integer.valueOf(id)).size()>0){
			model.addAttribute("filingattac", filingMng.findHTAttac(Integer.valueOf(id)));
		}
		//????????????????????????
		if(filingMng.findOtherAttac(Integer.valueOf(id)).size()>0){
			model.addAttribute("filinOthergattac", filingMng.findOtherAttac(Integer.valueOf(id)));
		}
		//???????????????????????????
		if(uptAttacMng.findAllFile(id).size()>0){
			model.addAttribute("changegattac", uptAttacMng.findAllFile(id));
		}
		//??????????????????
		if(uptMng.findByFContId_U(id).size()>0){
			model.addAttribute("Upt", uptMng.findByFContId_U(id).get(0));
		}
		//??????????????????
		if(conclusionMng.findByfContId(id).size()>0){
			model.addAttribute("Conclusion", conclusionMng.findByfContId(id).get(0));
		}
		//?????????????????????
		if( conclusionAttacMng.findAllFile(id).size()>0){
			model.addAttribute("conclusionattac", conclusionAttacMng.findAllFile(id));
		}
		//????????????????????????
		List<CheterInfo> cheterInfo = cheterMng.getCheterInfoList("????????????");
		model.addAttribute("cheterInfo", cheterInfo);
		//????????????????????????
		model.addAttribute("splc", "1");
		return "/WEB-INF/view/contract/conclusion/conclusion_edit";
	}
}
