getRegisterDatas();
/*function getRegisterDatas() {
	$.ajax({
		url : base + '/cockpit/getCGTypeDataSum',
		type : 'get',
		async : false,
		dataType : "json",
		success : function(json){
			option = null;
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
					nameGap:30,
					axisLabel: {
						margin:10
					},
					data:[{
						value: '政府采购',
						textStyle: {
							color:'rgba(153,157,165,1)'
						}
					},{
						value: '非政府采购',
						textStyle: {
							color:'rgba(153,157,165,1)'
						}
					}]
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
						fontWeight: 280,
						fontSize: 13
					},
					splitNumber: 1,
					splitLine: {
					  show: false
					}
				}],
				grid: {
					left: '27%',
				},
				series: [{
					name: '政府采购',
					data: [json[0][1],json[1][1]],
					type: 'bar',
					stack:'使用情况',
					itemStyle: {
					  color: 'rgba(132,210,204,1)'
					},
					barWidth: 58
				},{
					name: '非政府采购',
					data: [json[2][1],json[3][1]],
					type: 'bar',
					stack:'使用情况',
					itemStyle: {
					  color: 'rgba(231,197,59,1)'
					},
					barWidth: 58
				}]
			}
			var dom = document.getElementById("register-echart-histogram");
			var myChart = echarts.init(dom);
			if (option && typeof option === "object") {
			    myChart.setOption(option, true);
			}
			myChart.on('click',function(params) {
				window.location.href=base+'/cockpit/cgsqledgerlist';
			});
		}
	});
}*/
function getRegisterDatas() {
	var app = {};
	option = null;
	$.ajax({
		url:base+'/cockpit/getCGTypeDataSum',
		type:'post',
		async:'false',
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
						type:'value',
						axisLine: {
							lineStyle: {
								color:'rgba(112,112,112,1)'
							}
						},
						axisLabel: {
						    formatter:function(value,index) {
						     var percent = value/10000;
						     return percent;
						    },
						    margin:12
						},
						splitLine: {
							show: false,
						},
						nameTextStyle: {
							color:'rgba(153,157,165,1)',
							fontSize: 12
						}
					},
					yAxis:{
						type: 'category',
						data:['政府采购','非政府采购'],
						axisLine: {
							show: false
						},
						axisTick: { // 刻度
							show: false
						},
						axisLabel: { // 刻度标签
							color: 'rgba(153,157,165,1)',
							fontFamily: 'Microsoft YaHei',
							fontWeight: 400,
							fontSize: 12
						},
					},
					grid: {
						left: '22%',
						bottom:'28%',
						height:'60%'
					},
					series: [{
						name: '已登记',
						data: [data[0][1],data[2][1]],
						type: 'bar',
						stack:'使用情况',
						itemStyle: {
						  color: 'rgba(132,210,204,1)'
						},
					},{
						name: '采购中',
						data: [data[1][1],data[3][1]],
						type: 'bar',
						stack:'使用情况',
						itemStyle: {
						  color: 'rgba(231,197,59,1)'
						},
					}/*,{
						name: '冻结',
						data: data[3],
						type: 'bar',
						stack:'使用情况',
						itemStyle: {
						  color: 'rgba(243,124,120,1)'
						},
						barWidth: 58
					}*/]
				}
				var dom = document.getElementById("register-echart-histogram");
				var myChart = echarts.init(dom);
				if (option && typeof option === "object") {
				    myChart.setOption(option, true);
				}
				myChart.on('click',function(params) {
					window.location.href=base+'/cockpit/cgsqledgerlist';
				});
			
		},
		error:function(data){
			
		},
		
	});
	
}