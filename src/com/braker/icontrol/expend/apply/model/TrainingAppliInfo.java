package com.braker.icontrol.expend.apply.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.braker.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 培训费申请信息model
 * 是培训费申请信息的model类
 * @author 叶崇晖
 * @createtime 2018-06-13
 * @updatetime 2018-06-13
 */
@Entity
@Table(name = "T_TRAINING_APPLI_INFO")
public class TrainingAppliInfo extends BaseEntity{
	@Id
	@Column(name = "F_T_ID")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer tId;				//主键ID
	
	@Column(name = "F_G_ID")
	private Integer gId;				//基本信息ID
	
	@Column(name = "F_R_ID")
	private Integer rId;				//报销信息ID
	
	@Column(name = "F_TRAINING_NAME")
	private String trainingName;		//培训名称
	
	@Column(name = "F_TRAINING_TYPE")
	private String trainingType;		//培训类型
	
	@Column(name = "F_TRAINING_ORGANIZER")
	private String organizer;			//培训举办机构
	
	@Column(name = "F_TRAINING_METHOD")
	private String trainingModel;		//培训方式
	
	@Column(name = "F_TRAINING_DATE_START")
	private Date trDateStart;			//培训日期（开始）
	
	@Column(name = "F_TRAINING_DATE_END")
	private Date trDateEnd;				//培训日期（结束）
	
	@Column(name = "F_TRAINING_DAY_NUM")
	private String trDayNum;			//培训天数
	
	@Column(name = "F_TRAINING_PLACE")
	private String trPlace;				//培训地点
	
	@Column(name = "F_TRAINING_PLACE_EXPLAIN")
	private String trPlaceExplain;		//培训地点说明
	
	@Column(name = "F_ATTEND_PEOPLE")
	private String trAttendPeop;		//参加培训人员
	
	@Column(name = "F_ATTEND_NUM")
	private String trAttendNum;			//参训人数
	
	@Column(name = "F_STAFF_NUM")
	private String trStaffNum;			//工作人员数量
	
	@Column(name = "F_TRAINING_CONTENT")
	private String trContent;			//培训内容
	
	@Column(name = "F_FLOW_STAUTS")
	private String flowStauts;		//审批状态
	
	@Transient
	private Integer num;				//序号(数据库中没有)
	
	
	@Column(name = "F_LESSON_HOURS")
	private String lessonHours;			//学时总计
	
	//住宿费：标准、金额
	@Column(name = "COST_HOTEL_STD")
	private Double hotelStd;
	@Column(name = "COST_HOTEL_MONEY")
	private Double hotelMoney;
	//伙食费：标准、金额
	@Column(name = "COST_FOOD_STD")
	private Double foodStd;
	@Column(name = "COST_FOOD_MONEY")
	private Double foodMoney;
	//培训资料费：金额
	@Column(name = "COST_DATA_MONEY")
	private Double dataMoney;
	//培训场地费：金额
	@Column(name = "COST_SPACE_MONEY")
	private Double spaceMoney;
	//交通费：金额(市内交通)
	@Column(name = "COST_TRANSPORT_MONEY")
	private Double transportMoney;
	//其它费用：金额
	@Column(name = "COST_OTHER_MONEY")
	private Double otherMoney;	
	//院士、全国知名专家讲师  讲课费：是否异地、学时、标准、金额
	@Column(name = "OUTSIDE_IS_OR_NO1")
	private String outsideIsorNo1;
	@Column(name = "LESSON_TIME1")
	private String lessonTime1;
	@Column(name = "COST_LESSON_STD1")
	private Double lessonStd1;
	@Column(name = "COST_LESSON_MONEY1")
	private Double lessonMoney1;
	//正高级技术职称讲师  讲课费：是否异地、学时、标准、金额
	@Column(name = "OUTSIDE_IS_OR_NO2")
	private String outsideIsorNo2;
	@Column(name = "LESSON_TIME2")
	private String lessonTime2;
	@Column(name = "COST_LESSON_STD2")
	private Double lessonStd2;
	@Column(name = "COST_LESSON_MONEY2")
	private Double lessonMoney2;
	//副高级技术职称讲师  讲课费：是否异地、学时、标准、金额
	@Column(name = "OUTSIDE_IS_OR_NO3")
	private String outsideIsorNo3;
	@Column(name = "LESSON_TIME3")
	private String lessonTime3;
	@Column(name = "COST_LESSON_STD3")
	private Double lessonStd3;
	@Column(name = "COST_LESSON_MONEY3")
	private Double lessonMoney3;
	//长途交通费：金额(城市间交通)
	@Column(name = "COST_LONGTRAFFIC_MONEY")
	private Double longTrafficMoney;
	
	//综合预算金额
	@Column(name = "COST_ZONGHE_MONEY")
	private Double zongheMoney;
	
	//师资费-讲课费金额
	@Column(name = "COST_LESSONS_MONEY")
	private Double lessonsMoney;
	
	//是否安排住宿
	@Column(name = "IS_HOTEL")
	private String isHotel;
	
	//是否安排伙食
	@Column(name = "IS_FOOD")
	private String isFood;

	public Integer gettId() {
		return tId;
	}

	public void settId(Integer tId) {
		this.tId = tId;
	}

	public Integer getgId() {
		return gId;
	}

	public void setgId(Integer gId) {
		this.gId = gId;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public String getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getTrDateStart() {
		return trDateStart;
	}

	public void setTrDateStart(Date trDateStart) {
		this.trDateStart = trDateStart;
	}

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getTrDateEnd() {
		return trDateEnd;
	}

	public void setTrDateEnd(Date trDateEnd) {
		this.trDateEnd = trDateEnd;
	}

	public String getTrDayNum() {
		return trDayNum;
	}

	public void setTrDayNum(String trDayNum) {
		this.trDayNum = trDayNum;
	}

	public String getTrPlace() {
		return trPlace;
	}

	public void setTrPlace(String trPlace) {
		this.trPlace = trPlace;
	}

	public String getTrPlaceExplain() {
		return trPlaceExplain;
	}

	public void setTrPlaceExplain(String trPlaceExplain) {
		this.trPlaceExplain = trPlaceExplain;
	}

	public String getTrAttendPeop() {
		return trAttendPeop;
	}

	public void setTrAttendPeop(String trAttendPeop) {
		this.trAttendPeop = trAttendPeop;
	}

	public String getTrAttendNum() {
		return trAttendNum;
	}

	public void setTrAttendNum(String trAttendNum) {
		this.trAttendNum = trAttendNum;
	}

	public String getTrStaffNum() {
		return trStaffNum;
	}

	public void setTrStaffNum(String trStaffNum) {
		this.trStaffNum = trStaffNum;
	}

	public String getTrContent() {
		return trContent;
	}

	public void setTrContent(String trContent) {
		this.trContent = trContent;
	}

	

	public String getTrainingModel() {
		return trainingModel;
	}

	public void setTrainingModel(String trainingModel) {
		this.trainingModel = trainingModel;
	}

	public String getFlowStauts() {
		return flowStauts;
	}

	public void setFlowStauts(String flowStauts) {
		this.flowStauts = flowStauts;
	}

	public String getLessonHours() {
		return lessonHours;
	}

	public void setLessonHours(String lessonHours) {
		this.lessonHours = lessonHours;
	}

	public Double getHotelStd() {
		return hotelStd;
	}

	public void setHotelStd(Double hotelStd) {
		this.hotelStd = hotelStd;
	}

	public Double getHotelMoney() {
		return hotelMoney;
	}

	public void setHotelMoney(Double hotelMoney) {
		this.hotelMoney = hotelMoney;
	}

	public Double getFoodStd() {
		return foodStd;
	}

	public void setFoodStd(Double foodStd) {
		this.foodStd = foodStd;
	}

	public Double getFoodMoney() {
		return foodMoney;
	}

	public void setFoodMoney(Double foodMoney) {
		this.foodMoney = foodMoney;
	}

	public Double getDataMoney() {
		return dataMoney;
	}

	public void setDataMoney(Double dataMoney) {
		this.dataMoney = dataMoney;
	}

	public Double getSpaceMoney() {
		return spaceMoney;
	}

	public void setSpaceMoney(Double spaceMoney) {
		this.spaceMoney = spaceMoney;
	}

	public Double getTransportMoney() {
		return transportMoney;
	}

	public void setTransportMoney(Double transportMoney) {
		this.transportMoney = transportMoney;
	}

	public Double getOtherMoney() {
		return otherMoney;
	}

	public void setOtherMoney(Double otherMoney) {
		this.otherMoney = otherMoney;
	}

	public String getOutsideIsorNo1() {
		return outsideIsorNo1;
	}

	public void setOutsideIsorNo1(String outsideIsorNo1) {
		this.outsideIsorNo1 = outsideIsorNo1;
	}

	public String getLessonTime1() {
		return lessonTime1;
	}

	public void setLessonTime1(String lessonTime1) {
		this.lessonTime1 = lessonTime1;
	}

	public Double getLessonStd1() {
		return lessonStd1;
	}

	public void setLessonStd1(Double lessonStd1) {
		this.lessonStd1 = lessonStd1;
	}

	public Double getLessonMoney1() {
		return lessonMoney1;
	}

	public void setLessonMoney1(Double lessonMoney1) {
		this.lessonMoney1 = lessonMoney1;
	}

	public String getOutsideIsorNo2() {
		return outsideIsorNo2;
	}

	public void setOutsideIsorNo2(String outsideIsorNo2) {
		this.outsideIsorNo2 = outsideIsorNo2;
	}

	public String getLessonTime2() {
		return lessonTime2;
	}

	public void setLessonTime2(String lessonTime2) {
		this.lessonTime2 = lessonTime2;
	}

	public Double getLessonStd2() {
		return lessonStd2;
	}

	public void setLessonStd2(Double lessonStd2) {
		this.lessonStd2 = lessonStd2;
	}

	public Double getLessonMoney2() {
		return lessonMoney2;
	}

	public void setLessonMoney2(Double lessonMoney2) {
		this.lessonMoney2 = lessonMoney2;
	}

	public String getOutsideIsorNo3() {
		return outsideIsorNo3;
	}

	public void setOutsideIsorNo3(String outsideIsorNo3) {
		this.outsideIsorNo3 = outsideIsorNo3;
	}

	public String getLessonTime3() {
		return lessonTime3;
	}

	public void setLessonTime3(String lessonTime3) {
		this.lessonTime3 = lessonTime3;
	}

	public Double getLessonStd3() {
		return lessonStd3;
	}

	public void setLessonStd3(Double lessonStd3) {
		this.lessonStd3 = lessonStd3;
	}

	public Double getLessonMoney3() {
		return lessonMoney3;
	}

	public void setLessonMoney3(Double lessonMoney3) {
		this.lessonMoney3 = lessonMoney3;
	}

	public Double getLongTrafficMoney() {
		return longTrafficMoney;
	}

	public void setLongTrafficMoney(Double longTrafficMoney) {
		this.longTrafficMoney = longTrafficMoney;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getZongheMoney() {
		return zongheMoney;
	}

	public void setZongheMoney(Double zongheMoney) {
		this.zongheMoney = zongheMoney;
	}

	public Double getLessonsMoney() {
		return lessonsMoney;
	}

	public void setLessonsMoney(Double lessonsMoney) {
		this.lessonsMoney = lessonsMoney;
	}

	public Integer getrId() {
		return rId;
	}

	public void setrId(Integer rId) {
		this.rId = rId;
	}

	public String getIsHotel() {
		return isHotel;
	}

	public void setIsHotel(String isHotel) {
		this.isHotel = isHotel;
	}

	public String getIsFood() {
		return isFood;
	}

	public void setIsFood(String isFood) {
		this.isFood = isFood;
	}
	
	
}
