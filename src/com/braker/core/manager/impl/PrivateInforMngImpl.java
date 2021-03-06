package com.braker.core.manager.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braker.common.hibernate.BaseManagerImpl;
import com.braker.common.hibernate.Condition;
import com.braker.common.hibernate.Finder;
import com.braker.common.hibernate.OrderBy;
import com.braker.common.hibernate.Updater;
import com.braker.common.page.Pagination;
import com.braker.common.util.StringUtil;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.PrivateInforMng;
import com.braker.core.manager.ShenTongMng;
import com.braker.core.model.PrivateInformation;
import com.braker.core.model.User;

@Service
public class PrivateInforMngImpl extends BaseManagerImpl<PrivateInformation> implements PrivateInforMng {
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private ShenTongMng shenTongMng;

	@Override
	public List<PrivateInformation> countInformation(User user) {
		Finder finder = Finder.create( "FROM PrivateInformation WHERE  fStauts=1 AND fReadStauts=0 AND fUserID='"+user.getId()+"'"); 
		return super.find(finder);
	}

	@Override
	public Pagination queryList(PrivateInformation bean, User user,	Integer pageNo, Integer pageSize) {
		Finder finder = Finder.create(" FROM PrivateInformation WHERE fStauts=1 AND fUserID='"+user.getId()+"'");
		
		if(!StringUtil.isEmpty(bean.getfReadStauts())){
			finder.append(" AND fReadStauts =:fReadStauts");
			finder.setParam("fReadStauts", bean.getfReadStauts());
		}
		if(!StringUtil.isEmpty(bean.getfMessageStauts())){
			finder.append(" AND fMessageStauts =:fMessageStauts");
			finder.setParam("fMessageStauts", bean.getfMessageStauts());
		}
		if(!StringUtil.isEmpty(bean.getfMessage())){
			finder.append(" AND fMessage LIKE:fMessage");
			finder.setParam("fMessage", "%"+bean.getfMessage()+"%");
		}
		finder.append(" order by fSendTime desc");
		Pagination p=super.find(finder, pageNo, pageSize);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d=new Date();
		List<PrivateInformation> beanList=(List<PrivateInformation>) p.getList();
		for (int i = 0; i < beanList.size(); i++) {
			if(dateFormat.format(d).equals(dateFormat.format(beanList.get(i).getfSendTime()))){
				//??????
				beanList.get(i).setToday("1");
			}else {
				//?????????
				beanList.get(i).setToday("0");
			}
		}
		//???????????????????????????
		PrivateInformation temp; // ?????????????????????   
	    int size = beanList.size(); // ????????????   
	    for (int i = 0; i < size - 1; i++) {   
	        for (int j = i + 1; j < size; j++) {   
	            if (Integer.valueOf(beanList.get(i).getToday()) < Integer.valueOf(beanList.get(j).getToday())) { // ?????????????????????   

	                temp = beanList.get(i);   
	                beanList.set(i,  beanList.get(j)) ;   
	                beanList.set(j,  temp) ;   
	            }   
	        }   
	    }  
		p.setList(beanList);
		return p;
	}

	@Override
	public List<PrivateInformation> unread(String userId) {
		Finder finder = Finder.create( "FROM PrivateInformation WHERE fStauts=1 AND fUserID='"+userId+"' AND fReadStauts=0"); 
		return super.find(finder);
	}


	@Override
	public List<PrivateInformation> allunRead(PrivateInformation bean, User user) {
		Finder finder = Finder.create(" FROM PrivateInformation WHERE fStauts=1 AND fUserID='"+user.getId()+"' AND fReadStauts=0");
		finder.append(" order by fSendTime desc");
		List<PrivateInformation> beanlist =super.find(finder);
		for (int i = 0; i < beanlist.size(); i++) {
			beanlist.get(i).setNumber(i+1);
		}
		return beanlist;
	}

	@Override
	public void updateReadStauts(String[] str,String readStauts) {
		for (int i = 0; i < str.length; i++) {
			PrivateInformation bean = super.findById(Integer.valueOf(str[i]));
			bean.setfReadStauts(readStauts);
			super.saveOrUpdate(bean);
		}
	}

	@Override
	public void deletePart(String[] str) {
		for (int i = 0; i < str.length; i++) {
			PrivateInformation bean = super.findById(Integer.valueOf(str[i]));
			bean.setfStauts("99");
			super.saveOrUpdate(bean);
		}
		
	}

	@Override
	public void updateMessageStauts(String id, String MessageStauts) {
		PrivateInformation bean = super.findById(Integer.valueOf(id));
		String ms="";
		if("1".equals(bean.getfMessageStauts())){
			ms="0";
		}else if("0".equals(bean.getfMessageStauts())){
			ms="1";
		}
		bean.setfMessageStauts(ms);
		super.saveOrUpdate(bean);
	}

	@Override
	public void setMsg(String title, String msg, String userId,String type) {
		
		//??????????????????????????????
		PrivateInformation pi=new PrivateInformation();
		Integer id =shenTongMng.getSeq("T_PRIVATE_INFORMATION_SEQ");
		pi.setIfID(id);;
		//??????????????????0?????????1????????????2?????????3???????????????
		pi.setfType(type);
		//??????????????????
		pi.setfMessage(msg);
		//????????????
		pi.setfTitle(title);
		//?????????????????????
		pi.setfUserID(userId);
		pi.setfSendUser("???????????????");
		pi.setfSendTime(new Date());
		pi.setfMessageStauts("0");
		pi.setfReadStauts("0");
		pi.setfStauts("1");
		super.merge(pi);
		//????????????
		// ????????????   0????????? 1????????????????????????2???????????????????????????
		personalWorkMng.sendMessageToUser(userId,1);
	}

}
