getIntangibleAssetDatas();
getFixedAssetDatas();
getUseFixedAssetDatas();
getScrapAssetDatas();
function getIntangibleAssetDatas() { // 无形资产
	
	
	
	 // 固定资产
	var app = {};
	option = null;
	$.ajax({
		url:base+'/cockpit/intangibleStorageAmount',
		type:'post',
		async:'false',
		data:{type:'ZCLX-02'},
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
				legend:{
					data: ['减少','增加'],
					textStyle: {
						color: 'rgba(255,255,255,1)',
						fontFamily: 'Microsoft YaHei',
						fontSize: 10,
					},
					right:'2%',
					top:'7%'
				},
				xAxis: {
					type: 'value',
					boundaryGap: [0, 0.01],
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
						margin: 8
					},
					splitLine: {
						show: false,
					},
				},
				yAxis: {
					axisLine: {
						show: false
					},
					type: 'category',
					data: ['无形资产'],
					axisLabel: { // 刻度标签
						color: 'rgba(153,157,165,1)',
						fontFamily: 'Microsoft YaHei',
						fontWeight: 400,
					}
				},
				grid: {
					left: '20%',
					bottom: '20%',
					height: '40%'
				},
				series: [
					{
						name: '减少',
						type: 'bar',
						data: [data[1]],
						itemStyle: {
						  color: 'rgba(231,197,59,1)'
						},
					},
					{
						name: '增加',
						type: 'bar',
						data: [data[0]],
						itemStyle: {
						  color: 'rgba(132,210,204,1)'
						},
					}
				]
			}
			var dom = document.getElementById("assetIntangible-echart-histogram");
			var myChart = echarts.init(dom);
			if (option && typeof option === "object") {
			    myChart.setOption(option, true);
			}
			myChart.on('click',function(params) {
				//console.log(params);
				window.location.href=base+'/cockpit/assetsLedger';
			})
		}
	})

	
	
	
	
	
	
	
	
	
	
	
	
	
};
function getFixedAssetDatas() { // 固定资产
	var app = {};
	option = null;
	$.ajax({
		url:base+'/cockpit/fixedStorageAmount',
		type:'post',
		async:'false',
		data:{type:'ZCLX-02'},
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
				legend:{
					right: '2%',
					top:'8%',
					data: ['减少','增加'],
					textStyle: {
						color: 'rgba(255,255,255,1)',
						fontFamily: 'Microsoft YaHei',
						fontSize: 10
					}
				},
				xAxis: {
					type: 'value',
					boundaryGap: [0, 0.01],
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
						margin:10
					},
					splitLine: {
						show: false,
					},
				},
				yAxis: {
					axisLine: {
						show: false
					},
					type: 'category',
					data: ['固定资产'],
					axisLabel: { // 刻度标签
						color: 'rgba(153,157,165,1)',
						fontFamily: 'Microsoft YaHei',
						fontWeight: 400,
					}
				},
				grid: {
					left: '20%',
					bottom: '25%',
					height: '40%'
				},
				series: [
					{
						name: '减少',
						type: 'bar',
						data: [data[1]],
						itemStyle: {
						  color: 'rgba(231,197,59,1)'
						},
					},
					{
						name: '增加',
						type: 'bar',
						data: [data[0]],
						itemStyle: {
						  color: 'rgba(132,210,204,1)'
						},
					}
				]
			}
			var dom = document.getElementById("assetFixed-echart-histogram");
			var myChart = echarts.init(dom);
			if (option && typeof option === "object") {
			    myChart.setOption(option, true);
			}
			myChart.on('click',function(params) {
				//console.log(params);
				window.location.href=base+'/cockpit/assetsLedger';
			})
		}
	})
};
// 可用固定资产
function getUseFixedAssetDatas() {
	var app = {};
	option = null;
	$.ajax({
		url:base+'/cockpit/fixedAvailableAmount',
		type:'post',
		async:'false',
		data:{type:'ZCLX-02'},
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
				xAxis: {
					data: data.namestr,
					axisTick: {
					    show: false
					},
					axisLine: {
						lineStyle: {
							color:'rgba(112,112,112,1)'
						}
					},
					axisLabel: {
						formatter:function(value)  
                        {  
                            var ret = "";//拼接加\n返回的类目项  
                            var maxLength = 2;//每项显示文字个数  
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
					}
				},
				yAxis: {
					axisLine: {
						show: false
					},
					splitLine: {
					  show: false
					},
					axisTick: { // 刻度
						show: false
					},
					axisLabel: { // 刻度标签
						color: 'rgba(153,157,165,1)',
						fontFamily: 'Microsoft YaHei',
					},
					splitNumber: 2
				},
				grid: {
					bottom: '25%',
					height: '50%'
				},
				series: [{
				    name: "主要在库可用固定资产(数量)",
				    type: "bar",
				    data: data.amountstr,
					itemStyle: {
					  color: 'rgba(132,210,204,1)',
					},
				}]
			};
			var dom = document.getElementById("assetFixedUse-echart-histogram");
			var myChart = echarts.init(dom);
			if (option && typeof option === "object") {
			    myChart.setOption(option, true);
			}
			myChart.on('click',function(params) {
				//console.log(params);
				window.location.href=base+'/cockpit/assetsLedger';
			})
		}
	})
};
function getScrapAssetDatas() {
	var app = {};
	option = null;
	$.ajax({
		url:base+'/cockpit/fixedAvailablePercentage',
		type:'post',
		async:'false',
		data:{type:'ZCLX-02'},
		dataType:'json',
		success:function (data){
			console.log(data);
			option = {
				tooltip:{
				     show:true,
				     trigger:'axis',
				     axisPointer: {            // 坐标轴指示器，坐标轴触发有效
				         type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				     },
				    },
				legend: {
					right: '2%',
					top:'15%',
					data: ['可用', '不可用'],
					textStyle: {
						color: 'rgba(255,255,255,1)',
						fontFamily: 'Microsoft YaHei',
						fontSize:10
					}
				},
				xAxis: {
					axisLabel: {
					    formatter:function(value,index) {
					     var percent = value*100;
					     return percent+ "%";
					    },
					    margin:12
					},
					type: 'value',
					axisLine: {
						lineStyle: {
							color:'rgba(112,112,112,1)'
						}
					},
					splitLine: {
						show: false,
					},
				},
				yAxis: {
					type: 'category',
					data:['']
				},
				grid:{
					left:'5%',
					bottom:'25%',
					height: '22%'
				},
				series: [
					{
						name: '可用',
						type: 'bar',
						stack: '总量',
						data: [data.ZCKYZT01],
						itemStyle: {
						  color: 'rgba(132,210,204,1)'
						},
					},
					{
						name: '不可用',
						type: 'bar',
						stack: '总量',
						data: [data.ZCKYZT02],
						itemStyle: {
						  color: 'rgba(243,124,120,1)'
						},
					}
				]
			}
			var dom = document.getElementById("assetScrap-echart-histogram");
			var myChart = echarts.init(dom);
			if (option && typeof option === "object") {
			    myChart.setOption(option, true);
			}
			myChart.on('click',function(params) {
				//console.log(params);
				window.location.href=base+'/cockpit/assetsLedger';
			})
		}
	})
}