getContractDatas();
/*function getContractDatas() {
	$.ajax({
        type : "POST",
        url : base+'/cockpit/getContractAmount',
        async : 'false',
        success : function(data) {
        	data = JSON.parse(data);
			$('#progress').html(parseFormatNum(data.paidAll + data.arrearageAll));
			$('#paidAll').html(parseFormatNum(data.paidAll));
			$('#arrearageAll').html(parseFormatNum(data.arrearageAll));
			$('#contractCount').html(data.contractCount);
			
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
				xAxis:{
					type:'category',
					axisLine: {
						lineStyle: {
							color:'rgba(112,112,112,1)'
						}
					},
					axisLabel: {
						margin:10
					},
					nameGap:30,
					data:[{
						value: '新签',
						textStyle: {
							color:'rgba(153,157,165,1)',
						}
					},{
						value: '结转',
						textStyle: {
							color:'rgba(153,157,165,1)',
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
					name: '新签',
					data: [data.arrearage,data.paid],
					type: 'bar',
					stack:'使用情况',
					itemStyle: {
					  color: 'rgba(132,210,204,1)'
					},
					barWidth: 58
				},{
					name: '结转',
					data: [data.carryforwardArrearage,data.carryforwardPaid],
					type: 'bar',
					stack:'使用情况',
					itemStyle: {
					  color: 'rgba(231,197,59,1)'
					},
					barWidth: 58
				}]
			}
			var dom = document.getElementById("contract-echart-histogram");
			var myChart = echarts.init(dom);
			if (option && typeof option === "object") {
			    myChart.setOption(option, true);
			}
			myChart.on('click',function(params) {
				window.location.href=base+'/cockpit/contractList';
			});
        },
        error : function(e){
            console.log(e.status);
            console.log(e.responseText);
        }
    });
	
	
}*/




function getContractDatas() {
	var app = {};
	option = null;
	$.ajax({
		url:base+'/cockpit/getContractAmount',
		type:'post',
		async:'false',
		dataType:'json',
		success:function (data){
			$('#progress').html(parseFormatNum(data.paidAll + data.arrearageAll));
			$('#paidAll').html(parseFormatNum(data.paidAll));
			$('#arrearageAll').html(parseFormatNum(data.arrearageAll));
			$('#contractCount').html(data.contractCount);
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
						splitLine: {
							show: false,
						},
						axisLabel: {
							formatter:function(value,index) {
							     var percent = value/10000;
							     return percent;
							},
							margin: 15
						},
						nameTextStyle: {
							color:'rgba(153,157,165,1)',
							fontSize: 12
						}
					},
					yAxis:[{
						type: 'category',
						data:['新签','结转'],
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
					}],
					grid: {
						left: '12%',
						bottom:'28%',
						height: '50%'
					},
					series: [{
						name: '已结算',
						data: [data.paid,data.carryforwardPaid],
						type: 'bar',
						stack:'使用情况',
						itemStyle: {
						  color: 'rgba(132,210,204,1)'
						},
					},{
						name: '未结算',
						data: [data.arrearage,data.carryforwardArrearage],
						type: 'bar',
						stack:'使用情况',
						itemStyle: {
						  color: 'rgba(231,197,59,1)'
						},
					}]
				}
				var dom = document.getElementById("contract-echart-histogram");
				var myChart = echarts.init(dom);
				if (option && typeof option === "object") {
				    myChart.setOption(option, true);
				}
				myChart.on('click',function(params) {
					window.location.href=base+'/cockpit/contractList';
				});
			
		},
		error:function(data){
			
		},
		
	});
	
}







function parseFormatNum(number, n) {
	 if (number == null) {
	  number = ''
	  return;
	 }
	 if (n != 0) {
	  n = (n > 0 && n <= 20) ? n : 2;
	 }
	 number = parseFloat((number + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	 var sub_val = number.split(".")[0].split("").reverse();
	 var sub_xs = number.split(".")[1];
	 var show_html = "";
	 for (i = 0; i < sub_val.length; i++) {
	  show_html += sub_val[i] + ((i + 1) % 3 == 0 && (i + 1) != sub_val.length ? "," : "");
	 }
	 if (n == 0) {
	  return show_html.split("").reverse().join("");
	 } else {
	  return show_html.split("").reverse().join("") + "." + sub_xs;
	 }
};