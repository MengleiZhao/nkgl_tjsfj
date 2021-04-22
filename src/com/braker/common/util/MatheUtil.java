package com.braker.common.util;

import java.math.BigDecimal;


/**
 * 
 * <p>Description: 数据计算类</p>
 * @author:安达
 * @date： 2019年7月4日下午5:42:57
 */
public class MatheUtil {
	
	/**
	 * 
	* @author:安达
	* @Title: add 
	* @Description: 相加
	* @param a1
	* @param b1
	* @return
	* @return double    返回类型 
	* @date： 2019年7月4日下午5:45:19 
	* @throws
	 */
	 public static double add(double a1, double b1) {  
         BigDecimal a2 = new BigDecimal(Double.toString(a1));  
         BigDecimal b2 = new BigDecimal(Double.toString(b1));  
         return a2.add(b2).doubleValue();  
     }
	
	 /**
	  * 
	 * @author:安达
	 * @Title: sub 
	 * @Description: 相减
	 * @param a1
	 * @param b1
	 * @return
	 * @return double    返回类型 
	 * @date： 2019年7月4日下午5:45:29 
	 * @throws
	  */
	 public static double sub(double a1, double b1) {  
          BigDecimal a2 = new BigDecimal(Double.toString(a1));  
          BigDecimal b2 = new BigDecimal(Double.toString(b1));  
          return a2.subtract(b2).doubleValue();  
      }
	
	 /**
	  * 
	 * @author:安达
	 * @Title: mul 
	 * @Description: 相乘 
	 * @param a1
	 * @param b1
	 * @return
	 * @return double    返回类型 
	 * @date： 2019年7月4日下午5:45:46 
	 * @throws
	  */
	 public static double mul(double a1, double b1) {  
          BigDecimal a2 = new BigDecimal(Double.toString(a1));  
          BigDecimal b2 = new BigDecimal(Double.toString(b1));  
          return a2.multiply(b2).doubleValue();  
      }
	 
	 
	 /**
	  * 
	 * @author:安达
	 * @Title: div 
	 * @Description: 相除
	 * @param a1
	 * @param b1
	 * @param scale 保存小数点位数
	 * @return
	 * @return double    返回类型 
	 * @date： 2019年7月4日下午5:45:55 
	 * @throws
	  */
	 public static double div(double a1, double b1, int scale) {
	        if (scale < 0) {  
	            throw new IllegalArgumentException("error");  
	        }
	        BigDecimal a2 = new BigDecimal(Double.toString(a1));  
	        BigDecimal b2 = new BigDecimal(Double.toString(b1));  
	        return a2.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();  
	    }
	 
	 
	/**
	 * 返回中文数字 
	 * @param intInput
	 * @return
	 * @author 赵孟雷
	 * @createtime 2020年6月5日
	 * @updator 赵孟雷
	 * @updatetime 2020年6月5日
	 */
	public static String ToCH(int intInput) {
		String si = String.valueOf(intInput);
		String sd = "";
		if (si.length() == 1) // 個
		{
			sd += GetCH(intInput);
			return sd;
		} else if (si.length() == 2)// 十
		{
			if (si.substring(0, 1).equals("1"))
				sd += "十";
			else
				sd += (GetCH(intInput / 10) + "十");
			sd += ToCH(intInput % 10);
		} else if (si.length() == 3)// 百
		{
			sd += (GetCH(intInput / 100) + "百");
			if (String.valueOf(intInput % 100).length() < 2)
				sd += "零";
			sd += ToCH(intInput % 100);
		} else if (si.length() == 4)// 千
		{
			sd += (GetCH(intInput / 1000) + "千");
			if (String.valueOf(intInput % 1000).length() < 3)
				sd += "零";
			sd += ToCH(intInput % 1000);
		} else if (si.length() == 5)// 萬
		{
			sd += (GetCH(intInput / 10000) + "萬");
			if (String.valueOf(intInput % 10000).length() < 4)
				sd += "零";
			sd += ToCH(intInput % 10000);
		}
 
		return sd;
	}
 
	private static String GetCH(int input) {
		String sd = "";
		switch (input) {
		case 1:
			sd = "一";
			break;
		case 2:
			sd = "二";
			break;
		case 3:
			sd = "三";
			break;
		case 4:
			sd = "四";
			break;
		case 5:
			sd = "五";
			break;
		case 6:
			sd = "六";
			break;
		case 7:
			sd = "七";
			break;
		case 8:
			sd = "八";
			break;
		case 9:
			sd = "九";
			break;
		default:
			break;
		}
		return sd;
	}
}
