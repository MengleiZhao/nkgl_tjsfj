
/*getMeetfeeDatas();
getTrainfeeDatas();
getTravelfeeDatas()
getReceptionfeeDatas()
getAbroadfeeDatas();*/
getProjectDatas();


$.ajax({
	url:base+'/cockpit/getAllIndexDataSum',
	type:'post',
	async:'false',
	data:{type:'ZCLX-03'},
	dataType:'json',
	success:function (data){
		getMeetfeeDatas(data[0]);
		getTrainfeeDatas(data[1]);
		getTravelfeeDatas(data[2]);
		getReceptionfeeDatas(data[3]);
		getAbroadfeeDatas(data[4]);
	},
	error:function(data){
		
	},
	
});
$.ajax({
	url:base+'/cockpit/queryIndex',
	type:'post',
	async:'false',
	data:{type:'ZCLX-03'},
	dataType:'json',
	success:function (data){
		getColumBudgetDatas(data);
	},
	error:function(data){
		
	},
	
});

function getColumBudgetDatas(data) {
	var app = {};
	option = null;
	option = {
		tooltip:{
		     show:true,
		     trigger:'axis',
		     axisPointer: {            // 坐标轴指示器，坐标轴触发有效
		         type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		     },
	    },
		legend: {
			data: ['剩余', '冻结', '报销'],
			textStyle: {
				color: 'rgba(255,255,255,1)',
				fontSize: 12,
				fontFamily: 'Microsoft YaHei'
			},
			top:'7%',
			right:'2%'
		},
		xAxis: {
			type: 'value',
			axisLine: {
				lineStyle: {
					color: 'rgba(255,255,255,1)'
				}
			},
			splitLine: {
				show: false,
			},
		},
		grid:{
			bottom:'20%',
		},
		yAxis: {
			type: 'category',
			data: ['项目预算', '基本预算'],
			axisLabel: { // 刻度标签
				color: 'rgba(153,157,165,1)',
				fontFamily: 'Microsoft YaHei'
			}
		},
		series: [{
				name: '剩余',
				type: 'bar',
				stack: '总量',
				data: data[0][0],
				itemStyle: {
					color: 'rgba(231,197,59,1)'
				}
			},
			{
				name: '冻结',
				type: 'bar',
				stack: '总量',
				data: data[1][0],
				itemStyle: {
					color: 'rgba(243,124,120,1)'
				}
			}, {
				name: '报销',
				type: 'bar',
				stack: '总量',
				data: data[2][0],
				itemStyle: {
					color: 'rgba(132,210,204,1)'
				}
			}
		]
	}
	var dom = document.getElementById("budget-stackEchart-histogram");
	var myChart = echarts.init(dom);
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
	myChart.on('click',function(params) {
		//指标类型(0-基本指标；1-项目指标；)
		var indexType = '0';
		if (params.name == '项目预算') {
			indexType = '1';
		}
		window.location.href=base+'/cockpit/indexPlanForm?indexType=' + indexType;
	});
}
//会议费
function getMeetfeeDatas(data) {
	var app = {};
	option = null;
	option = {
		legend: {
			   show:false,
			   data: ['剩余', '冻结', '报销'],
			   textStyle: {
			    color: 'rgba(255,255,255,1)',
			    fontSize: 17,
			    fontFamily: 'Microsoft YaHei',
			    fontWeight: 300
			   }
			  },
		tooltip: {
	        trigger: 'item'
	    },
	    grid:{
	    	width:'100%',
	    	height:'100%',
	    	bottom:'10%'
	    },
		series: [{
			type: 'pie',
			data: [{
					value: data[0][0],
					 name: '剩余',
					itemStyle: {
						color: 'rgba(231,197,59,1)'
					},
				},
				{
					value: data[0][1],
					 name: '冻结',
					itemStyle: {
						color: 'rgba(243,124,120,1)'
					},
				},
				{
					value: data[0][2],
					 name: '报销',
					itemStyle: {
						color: 'rgba(132,210,204,1)'
					},
				},
			],
			radius: ['40%', '70%'],
			label: {
				show: false,
				position: 'center'
			},
		}]
	}
	var dom = document.getElementById("meetfee-pancake");
	var myChart = echarts.init(dom);
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
	myChart.on('click',function(params) {
		window.location.href=base+'/cockpit/reimburselist?type=2&subCode=30215';
	})
};
//培训费
function getTrainfeeDatas(data) {
	var app = {};
	option = null;
	option = {
			legend: {
				   show:false,
				   data: ['剩余', '冻结', '报销'],
				   textStyle: {
				    color: 'rgba(255,255,255,1)',
				    fontSize: 17,
				    fontFamily: 'Microsoft YaHei',
				    fontWeight: 300
				   }
				  },
			tooltip: {
		        trigger: 'item'
		    },
		    grid:{
		    	width:'100%',
		    	height:'100%',
		    	bottom:'5%'
		    },
		series: [{
			type: 'pie',
			data: [{
					value: data[0][0],
					 name: '剩余',
					itemStyle: {
						color: 'rgba(231,197,59,1)'
					},
				},
				{
					value: data[0][1],
					 name: '冻结',
					itemStyle: {
						color: 'rgba(243,124,120,1)'
					},
				},
				{
					value: data[0][2],
					 name: '报销',
					itemStyle: {
						color: 'rgba(132,210,204,1)'
					},
				},
			],
			radius: ['40%', '70%'],
			label: {
				show: false,
				position: 'center'
			},
		}]
	}
	var dom = document.getElementById("trainfee-pancake");
	var myChart = echarts.init(dom);
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
	myChart.on('click',function(params) {
		window.location.href=base+'/cockpit/reimburselist?type=3&subCode=30216';
	})
}
//差旅费
function getTravelfeeDatas(data) {
	var app = {};
	option = null;
	option = {
			legend: {
				   show:false,
				   data: ['剩余', '冻结', '报销'],
				   textStyle: {
				    color: 'rgba(255,255,255,1)',
				    fontSize: 17,
				    fontFamily: 'Microsoft YaHei',
				    fontWeight: 300
				   }
				  },
			tooltip: {
		        trigger: 'item'
		    },
		    grid:{
		    	width:'100%',
		    	height:'100%',
		    	bottom:'10%'
		    },
		series: [{
			type: 'pie',
			data: [{
					value: data[0][0],
					 name: '剩余',
					itemStyle: {
						color: 'rgba(231,197,59,1)'
					},
				},
				{
					value: data[0][1],
					 name: '冻结',
					itemStyle: {
						color: 'rgba(243,124,120,1)'
					},
				},
				{
					value: data[0][2],
					 name: '报销',
					itemStyle: {
						color: 'rgba(132,210,204,1)'
					},
				},
			],
			radius: ['40%', '70%'],
			label: {
				show: false,
				position: 'center'
			},
		}]
	}
	var dom = document.getElementById("travelfee-pancake");
	var myChart = echarts.init(dom);
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
	myChart.on('click',function(params) {
		window.location.href=base+'/cockpit/reimburselist?type=4&subCode=30211';
	})
}
//公务接待费
function getReceptionfeeDatas(data) {
	var app = {};
	option = null;
	option = {
			legend: {
				   show:false,
				   data: ['剩余', '冻结', '报销'],
				   textStyle: {
				    color: 'rgba(255,255,255,1)',
				    fontSize: 17,
				    fontFamily: 'Microsoft YaHei',
				    fontWeight: 300
				   }
				  },
			tooltip: {
		        trigger: 'item'
		    },
		    grid:{
		    	width:'100%',
		    	height:'100%',
		    	bottom:'10%'
		    },
		series: [{
			type: 'pie',
			data: [{
					value: data[0][0],
					 name: '剩余',
					itemStyle: {
						color: 'rgba(231,197,59,1)'
					},
				},
				{
					value: data[0][1],
					 name: '冻结',
					itemStyle: {
						color: 'rgba(243,124,120,1)'
					},
				},
				{
					value: data[0][2],
					 name: '报销',
					itemStyle: {
						color: 'rgba(132,210,204,1)'
					},
				},
			],
			radius: ['40%', '70%'],
			label: {
				show: false,
				position: 'center'
			},
		}]
	}
	var dom = document.getElementById("receptionfee-pancake");
	var myChart = echarts.init(dom);
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
	myChart.on('click',function(params) {
		window.location.href=base+'/cockpit/reimburselist?type=5&subCode=30217';
	})
}
//因公出国费
function getAbroadfeeDatas(data) {
	var app = {};
	option = null;
	option = {
			legend: {
				   show:false,
				   data: ['剩余', '冻结', '报销'],
				   textStyle: {
				    color: 'rgba(255,255,255,1)',
				    fontSize: 17,
				    fontFamily: 'Microsoft YaHei',
				    fontWeight: 300
				   }
				  },
			tooltip: {
		        trigger: 'item'
		    },
		    grid:{
		    	width:'100%',
		    	height:'100%',
		    	bottom:'10%'
		    },
		series: [{
			type: 'pie',
			data: [{
					value: data[0][0],
					 name: '剩余',
					itemStyle: {
						color: 'rgba(231,197,59,1)'
					},
				},
				{
					value: data[0][1],
					 name: '冻结',
					itemStyle: {
						color: 'rgba(243,124,120,1)'
					},
				},
				{
					value: data[0][2],
					 name: '报销',
					itemStyle: {
						color: 'rgba(132,210,204,1)'
					},
				},
			],
			radius: ['40%', '70%'],
			label: {
				show: false,
				position: 'center'
			},
		}]
	}
	var dom = document.getElementById("abroadfee-pancake");
	var myChart = echarts.init(dom);
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
	myChart.on('click',function(params) {
		window.location.href=base+'/cockpit/reimburselist?type=7&subCode=30212';
	});
};
//项目统计
function getProjectDatas() {
	var app = {};
	option = null;
	$.ajax({
		url:base+'/cockpit/queryProIndexDataSum',
		type:'post',
		async:'false',
		data:{type:'ZCLX-03'},
		dataType:'json',
		success:function (data){
			option = {
					tooltip:{
					     show:true,
					     trigger:'axis',
					     axisPointer: {            // 坐标轴指示器，坐标轴触发有效
					         type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					     },
				    },
					xAxis:{
						type:'category',
						axisLine: {
							lineStyle: {
								color:'rgba(112,112,112,1)'
							}
						},
						axisLabel: {
							formatter:function(value)  {  
                                var ret = "";//拼接加\n返回的类目项  
                                var maxLength = 4;//每项显示文字个数  
                                var valLength = value.length;//X轴类目项的文字个数  
                                var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                if (rowN > 1)//如果类目项的文字大于3,  
                                {  
                                    for (var i = 0; i < rowN; i++) {  
                                        var temp = "";//每次截取的字符串  
                                        var start = i * maxLength;//开始截取的位置  
                                        var end = start + maxLength;//结束截取的位置  
                                        //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                        temp = value.substring(start, end) + "...";  
                                        ret += temp; //凭借最终的字符串  
                                        break;
                                    }  
                                    return ret;  
                                }  
                                else {  
                                    return value;  
                                }  
                            }
						},
						data:data[0],
						textStyle: {
							color:'rgba(153,157,165,1)',
							fontSize: 12
						}
					},
					yAxis:[{
						type: 'value',
						axisLine: {
							show: false
						},
						axisTick: { // 刻度
							show: false
						},
						axisLabel: { // 刻度标签
							color: 'rgba(153,157,165,1)',
							fontFamily: 'Microsoft YaHei',
							fontSize: 12
						},
						splitNumber: 1,
						splitLine: {
						  show: false
						}
					}],
					grid: {
						 left: '10%',
						 bottom:'20%',
						 heigth:'100%',
						 width:'84%'
					},
					series: [{
						name: '报销',
						data: data[1],
						type: 'bar',
						stack:'使用情况',
						itemStyle: {
						  color: 'rgba(132,210,204,1)'
						},
						barWidth: 58
					},{
						name: '剩余',
						data: data[2],
						type: 'bar',
						stack:'使用情况',
						itemStyle: {
						  color: 'rgba(231,197,59,1)'
						},
						barWidth: 58
					},{
						name: '冻结',
						data: data[3],
						type: 'bar',
						stack:'使用情况',
						itemStyle: {
						  color: 'rgba(243,124,120,1)'
						},
						barWidth: 58
					}]
				}
				var dom = document.getElementById("scrap-Echart-histogram");
				var myChart = echarts.init(dom);
				if (option && typeof option === "object") {
				    myChart.setOption(option, true);
				}
				myChart.on('click',function(params) {
					window.location.href=base+'/cockpit/indexPlanForm';
				});
			
		},
		error:function(data){
			
		},
		
	});
	
}