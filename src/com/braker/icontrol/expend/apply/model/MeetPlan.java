package com.braker.icontrol.expend.apply.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.braker.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 培训日程model
 * @author 张迅
 * @createtime 2020-02-13
 */
@Entity
@Table(name = "t_meet_plan")
public class MeetPlan extends BaseEntity{
	@Id
	@Column(name = "F_P_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer planId;				//主键ID
	
	@Column(name = "F_J_ID")
	private Integer tId;				//培训信息ID
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@Column(name = "F_TIME_DATE")
	private Date date;				//日期
	
//	@DateTimeFormat(pattern = "HH:mm")
//	@JsonFormat(pattern="HH:mm",timezone = "GMT+8")
	@Column(name = "F_TIME_START")
	private String timeStart;				//起始时间
	
//	@DateTimeFormat(pattern = "HH:mm")
//	@JsonFormat(pattern="HH:mm",timezone = "GMT+8")
	@Column(name = "F_TIME_END")
	private String timeEnd;				//结束时间
	
	@Column(name = "F_ARRANGE")
	private String arrange;				//事项安排
	
	@Column(name = "F_LESSON_TIME")
	private String lessonTime;				//学时
	
	@Column(name = "F_LECTURER_NAME")
	private String lecturerName;				//讲师姓名
	
	@Transient
	private String status;				//状态值easyui插件中带的，装换json时用(数据库中没有)

	
	

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}





	public Integer gettId() {
		return tId;
	}

	public void settId(Integer tId) {
		this.tId = tId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getArrange() {
		return arrange;
	}

	public void setArrange(String arrange) {
		this.arrange = arrange;
	}

	public String getLessonTime() {
		return lessonTime;
	}

	public void setLessonTime(String lessonTime) {
		this.lessonTime = lessonTime;
	}

	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}
	
	
}
