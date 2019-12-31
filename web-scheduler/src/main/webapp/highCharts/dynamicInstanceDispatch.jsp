<!DOCTYPE>
<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>强化学习案例训练动态分派</title>

<style type="text/css">
#wrap {
	width: 100%;
	margin: 0 auto;
}

#schedule-result {
	float: left;
	width: 200px;
	padding: 0 10px;
	border: 1px solid #ccc;
	background: #abc;
	text-align: left;
	font-size: 20px;
	overflow-y: yes;
	overflow-x: hidden;
}

#schedule-result .epoch {
	font-size: 14px;
	margin: 18px 0;
	cursor: pointer;
}

#schedule-result .result {
	margin: 20px 0;
	cursor: pointer;
	font-size: 16px;
}

#container, #buttonGroup {
	max-width: 1200px;
	min-width: 320px;
	margin: 1em auto;
}

.hidden {
	display: none;
}

.main-container button {
	font-size: 12px;
	border-radius: 2px;
	border: 0;
	background-color: #ddd;
	padding: 13px 18px;
}

.main-container button[disabled] {
	color: silver;
}

.button-row button {
	display: inline-block;
	margin: 0;
}

.overlay {
	position: fixed;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	background: rgba(0, 0, 0, 0.3);
	transition: opacity 500ms;
}

.popup {
	margin: 70px auto;
	padding: 20px;
	background: #fff;
	border-radius: 5px;
	width: 300px;
	position: relative;
}

.popup input, .popup select {
	width: 100%;
	margin: 5px 0 15px;
}

.popup button {
	float: right;
	margin-left: 0.2em;
}

.popup .clear {
	height: 50px;
}

.popup input[type=text], .popup select {
	height: 2em;
	font-size: 16px;
}
</style>
</head>
<body>
	<script src="jquery-3.1.1.min.js"></script>
	<script src="./Highcharts-Gantt-7.0.3/code/highcharts-gantt.js"></script>
	<script src="./Highcharts-Gantt-7.0.3/code/modules/draggable-points.js"></script>
	<script src="./Highstock-7.0.3/code/modules/exporting.js"></script>
	<script src="./Highstock-7.0.3/code/modules/stock.js"></script>
	<script
		src="https://img.highcharts.com.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
	<script>
var scheduleUid="<%=request.getParameter("SCHEDULEUID")%>";
var deptId="<%=request.getParameter("DEPTID")%>";
var employeeId="<%=request.getParameter("EMPLOYEEID")%>";
var scheduleUid="<%=request.getParameter("SCHEDULEUID")%>";
var resUid="<%=request.getParameter("RESUID")%>";
var taskUid="<%=request.getParameter("TASKUID")%>";
var stateId=0;//"<%=request.getParameter("STATEID")%>";
var actionId=0;//"<%=request.getParameter("ACTIONID")%>";
		var sysPara = "?DEPTID=" + deptId + "&EMPLOYEEID=" + employeeId
				+ "&SCHEDULEUID=" + scheduleUid;
		var height = document.body.clientHeight + "px";
		var width = document.body.offsetWidth + "px";
		var closed = "true";//调度结果是否关闭
		var currentResult = "";//用于确定当前选择的调度结果
		var lastScheduleResult;//用于记录调度结果div的选择
		var lastInstanceIndex;//用于记录上次的案例序号
		var lastInstanceStep;
		var timeout = 1000;
		var each = Highcharts.each;
		var setTimeoutObj;
		var currStep = 0;
		var filePathGol;
	</script>

	<script type="text/javascript">
		/*
		 Simple demo showing some interactivity options of Highcharts Gantt. More
		 custom behavior can be added using event handlers and API calls. See
		 http://api.highcharts.com/gantt.
		 */
		var ganttChart;
		var today = new Date(), day = 1000 * 60 * 60 * 24, each = Highcharts.each, reduce = Highcharts.reduce, isAddingTask = false;

		// Set to 00:00:00:000 today
		today.setUTCHours(0);
		today.setUTCMinutes(0);
		today.setUTCSeconds(0);
		today.setUTCMilliseconds(0);
		today = today.getTime();
		$(document)
				.ready(
						function() {
							$("div#schedule-result").css({
								height : $(document).height() - 30
							});
							//初始化调度结果
							initScheduleResult();
							// Update disabled status of the remove button, depending on whether or not we
							// have any selected points.
							
						});

		function requestData(series) {
			//alert();
			// activate the button
			$.ajax({
				url : "/web-scheduler/chartServlet" + sysPara
						+ "&ACTION=get&METHOD=getGanttTask" + "&SCHEDULEUID="
						+ scheduleUid,
				type : "get",
				async : false,
				dataType : "json",
				success : function(response) {
					//定义一个数组
					$.each(response, function(i, d) {
						//定时刷新已安排的工序，动态添加至甘特图中
						// Add the point
						series.addPoint({
							start : d.start,
							end : d.end,
							y : d.y,
							name : d.name,
							milestone : d.milestone,
							color : d.color
						});
					});
				}
			});
		}
		//结果所在文件路径(如0_6x6sample)
		function initScheduleResult() {
			//先清空调度结果
			$("#schedule-result").children("div").remove();
			//可能需要到后台获取文件
			$
					.ajax({
						type : "post",
						url : "/web-scheduler/chartServlet"
								+ "?ACTION=get&METHOD=getInstanceSolutionPath",
						dataType : "json",

						success : function(data, textStatus, jqXHR) {
							$
									.each(
											data,
											function(name, obj) {
												$("#schedule-result")
														.append(
																"<div id=" +obj.id+ " class='result'"
													+ " closed='true'" 
													+ " path=" + obj.path
													+ ">"
																		+ obj.name
																		+ "</div>");
												$('#' + obj.id).css('color',
														'black');
												$('#' + obj.id).css(
														"background-color",
														'#3a87ad');
												$("#" + obj.id)
														.click(
																function() {
																	var closed1 = $(
																			'#'
																					+ obj.id)
																			.attr(
																					'closed');
																	if (closed1 == "true") {
																		closed1 = "false";
																	} else {
																		closed1 = "true";
																	}
																	$(
																			'#'
																					+ obj.id)
																			.attr(
																					'closed',
																					closed1);
																	if (closed1 == "true") {
																		//关闭了则移除其后workCenter=obj.id的div
																			$
																			.each(lastScheduleResult.children,function(name,obj){
																				$("div")
																				.remove(
																						"div[instanceIndex="
																								+ obj.name
																								+ "]");
																			});
																		$('div')
																				.remove(
																						"div[scheduleResult="
																								+ obj.id
																								+ "]");
																		
																	} else {
																		//打开则初始化代结果，并为每一个子div添加点击事件
																		initInstance(
																				obj.id,
																				obj.path);
																	}
																	if (lastScheduleResult) {
																		lastScheduleResult
																				.css(
																						"background-color",
																						'#3a87ad');
																		if (lastScheduleResult
																				.attr('closed') == "false") {
																			
																			$(
																					'div')
																					.remove(
																							"div[scheduleResult="
																									+ lastScheduleResult
																											.attr("id")
																									+ "]");
																		}
																	}
																	if (lastInstanceStep) {
																		lastInstanceStep
																				.css(
																						"background-color",
																						'#3a87ad');
																	}
																	$(this)
																			.css(
																					"background-color",
																					'#3a87ed');
																	lastScheduleResult = $(this);
																});
											});
						},
						error : function(jqXHR, textStatus, errorThrown) {// AJAX发送出错执行
							alert("加载调度结果时发生错误，请重试！");
							return;
						},
						complete : function() {

						}
					});
		}
		//获取案例（1,2,3等）
		function initInstance(result, scheduleResultPath) {
			//可能需要到后台获取文件
			$
					.ajax({
						type : "post",
						url : "/web-scheduler/chartServlet"
								+ "?ACTION=get&METHOD=getInstanceIndex"
								+ "&scheduleResultPath=" + scheduleResultPath,
						dataType : "json",

						success : function(data, textStatus, jqXHR) {
							$
									.each(
											data,
											function(name, obj) {
												$("#" + result)
														.after(
																"<div id="+obj.id
											+" closed='true'" 
											+" title="+obj.name
											+" class='epoch'"
											+" path=" + obj.path
											+" scheduleResult="+result	
											+">"
																		+ ("•" + obj.name)
																		+ "</div>");
												$('#' + obj.id).css('color',
														'black');
												$("#" + obj.id)
														.click(
																function() {
																	var closed1 = $(
																			'#'
																					+ obj.id)
																			.attr(
																					'closed');
																	if (closed1 == "true") {
																		closed1 = "false";
																	} else {
																		closed1 = "true";
																	}
																	$(
																			'#'
																					+ obj.id)
																			.attr(
																					'closed',
																					closed1);
																	if (closed1 == "true") {
																		//关闭了则移除其后workCenter=obj.id的div
																		$('div')
																				.remove(
																						"div[instanceIndex="
																								+ obj.name
																								+ "]");
																	} else {
																		//打开则初始化代结果，并为每一个子div添加点击事件
																		initInstanceSolutionStep(
																				obj.name,
																				obj.path);
																	}
																	if (lastInstanceIndex) {
																		lastInstanceIndex
																				.css(
																						"background-color",
																						'#3a87ad');
																		if (lastInstanceIndex
																				.attr('closed') == "false") {
																			$(
																					'div')
																					.remove(
																							"div[instanceIndex="
																									+ lastScheduleResult
																											.attr("name")
																									+ "]");
																		}
																	}
																	if (lastInstanceStep) {
																		lastInstanceStep
																				.css(
																						"background-color",
																						'#3a87ad');
																	}
																	$(this)
																			.css(
																					"background-color",
																					'#3a87ed');
																	lastInstanceIndex = $(this);
																});
											});
						},
						error : function(jqXHR, textStatus, errorThrown) {// AJAX发送出错执行
							alert("加载调度结果时发生错误，请重试！");
							return;
						},
						complete : function() {

						}
					});
		}
		function initInstanceSolutionStep(index, instanceIndexPath) {
			//可能需要到后台获取文件
			$
					.ajax({
						type : "post",
						url : "/web-scheduler/chartServlet"
								+ "?ACTION=get&METHOD=getInstanceSolutionStep"
								+ "&instanceIndexPath=" + instanceIndexPath,
						dataType : "json",

						success : function(data, textStatus, jqXHR) {
							$
									.each(
											data,
											function(name, obj) {
												$("#" + index)
														.after(
																"<div id="+obj.id
											+" title="+obj.name
											+" class='epoch'"
											+" path=" + obj.path
											+" instanceIndex="+index	
											+">"
																		+ ("√" + obj.name)
																		+ "</div>");
												$('#' + obj.id).css('color',
														'black');
												$("#" + obj.id)
														.click(
																function() {

																	if (lastInstanceStep) {
																		lastInstanceStep
																				.css(
																						"background-color",
																						'#3a87ad');
																	}
																	lastInstanceStep = $(this);
																	$(this)
																			.css(
																					"background-color",
																					'#3a87ed');
																});
												$("#" + obj.id)
														.dblclick(
																function() {
																	//初始化甘特图
																	loadGanttTask(obj.path);
																	filePathGol = obj.path;
																});
											});
						},
						error : function(jqXHR, textStatus, errorThrown) {// AJAX发送出错执行
							alert("加载调度结果时发生错误，请重试！");
							return;
						},
						complete : function() {

						}
					});
		}
		function getInstanceRes(filePath) {
			$.ajax({
				url : "/web-scheduler/chartServlet"
						+ "?ACTION=get&METHOD=getInstanceRes"
						+ "&instanceStepPath=" + filePath,
				type : "post",
				async : false,
				dataType : "json",
				success : function(response) {
					var devices = [];
					var maxLength = 0;
					$.each(response, function(i, d) {
						devices.push(d.deviceName + "(" + d.deviceUid + ")");
					});
					if (devices.length > 0) {
						maxLength = devices.length-1;
					}
					// Create the chart
					var chart = Highcharts
							.ganttChart(
									'container',
									{

										chart : {
											animation : false,
											spacingLeft : 1,
											events : {
												load : function() {
													var series = this.series[0], chart = this;
													//requestData(series);
													//activeLastPointToolip(chart);
													//整个load时间运行完后才能显示数据

												}
											}
										},

										title : {
											text : '深度强化学习分派动画'
										},

										subtitle : {
											text : ''
										},
										//去除水印
										credits : {
											enabled : false
										},
										plotOptions : {
											series : {
												animation : false, // Do not animate dependency connectors
												dragDrop : {
													draggableX : true,
													draggableY : true,
													dragMinY : 0,
													dragMaxY : maxLength,
													dragPrecisionX : 100
												// Snap to eight hours
												},
												dataLabels : {
													enabled : true,
													format : '{point.name}',
													style : {
														cursor : 'default',
														pointerEvents : 'none'
													}
												},
												allowPointSelect : true,
												point : {
													events : {

													}
												}
											}
										},

										yAxis : {
											type : 'category',
											categories : devices,
											min : 0,
											max :maxLength,
											uniqueNames : true
										},

										xAxis : {
											type:'datetime',
											dateTimeLabelFormats: {
												millisecond: '%S,%L'
											},
											minTickInterval:500
											//currentDateIndicator : true
										},
										navigator : {
											enabled : true,
											series : {
												type : 'gantt',
												//控制每列之间的距离值，当highcharts图表宽度固定的情况下，此值越大，柱子宽度越小，反之相反。默认此值为0.1
												pointPlacement : 0.5,
												pointPadding : 0.25
											},
											yAxis : {
												min : 0,
												max : maxLength,
												reversed : true,
												categories : []
											}
										},
										scrollbar : {
											enabled : true
										},
										rangeSelector : {
											buttons : [ {
												type : 'hour',
												count : 1,
												text : '1h'
											}, {
												type : 'day',
												count : 1,
												text : '1d'
											}, {
												type : 'week',
												count : 1,
												text : '1w'
											}, {
												type : 'month',
												count : 1,
												text : '1m'
											}, {
												type : 'month',
												count : 6,
												text : '6m'
											}, {
												type : 'ytd',
												text : 'YTD'
											}, {
												type : 'year',
												count : 1,
												text : '1y'
											}, {
												type : 'all',
												text : 'All'
											} ],
											allButtonsEnabled : true,
											enabled : true,
											selected : 7
										},
										tooltip : {
											xDateFormat : '%S,%L'
										},

										series : [ {
											name : '调度信息',
											data : []
										/* ,
																				dataLabels: [{
																					enabled: true,
																					//format: '<i style="font-size: 1.5em">{point.name}</i>',
																					useHTML: true,
																					align: 'center',
																					style: {
																					 color: '{point.color}'
																					}
																				}] */
										} ]
									});
					ganttChart = chart;
				}
			});
		}
		function loadGanttTask(filePath) {
			var currObjective=document.getElementById("currObjective");
			currObjective.value=0;
			//初始化机床
			getInstanceRes(filePath);
			//首先清除原有的甘特条
			ganttChart.update({
				series : [ {
					name : '调度信息',
					data : []
				} ]
			});
			currStep = 0;
			if (setTimeoutObj) {
				clearInterval(setTimeoutObj);
			}
			setTimeoutObj = setInterval(function() {
				$.ajax({
					type : "post",
					url : "/web-scheduler/chartServlet"
							+ "?ACTION=get&METHOD=getSomeStep" + "&stepPath="
							+ filePath + "&stepIndex=" + (currStep++),
					dataType : "json",
					success : function(data, textStatus, jqXHR) {
						var series = ganttChart.series[0];
						//分步画甘特条
						$.each(data, function(name, d) {
							series.addPoint({
								start : d.start,
								end : d.end,
								y : d.y-1,
								name : d.name,
								milestone : d.milestone,
								color : d.color
							});
							activeLastPointToolip(ganttChart);
							currObjective.value=d.currObjective;
						});
					}
				});
			}, timeout);
		}
		function activeLastPointToolip(chart) {
			var points = chart.series[0].points;
			//alert(points);
			chart.tooltip.refresh(points[points.length - 1]);
		}
		function loadStep(filePath) {
			$.ajax({
				type : "post",
				url : "/web-scheduler/chartServlet"
						+ "?ACTION=get&METHOD=getSomeStep" + "&stepPath="
						+ filePathGol + "&stepIndex=" + (currStep++),
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					var series = ganttChart.series[0];
					//分步画甘特条
					$.each(data, function(name, d) {
						series.addPoint({
							start : d.start,
							end : d.end,
							y : d.y,
							name : d.name,
							milestone : d.milestone,
							color : d.color
						});
						activeLastPointToolip(ganttChart);
					});
				}
			});
		}
		function onIntervalChange(id) {
			var newTimeout = document.getElementById(id).value * 1000;
			timeout = newTimeout;
			if (setTimeoutObj) {
				clearInterval(setTimeoutObj);
				setTimeoutObj = setInterval(function() {
					$.ajax({
						type : "post",
						url : "/web-scheduler/chartServlet"
								+ "?ACTION=get&METHOD=getSomeStep"
								+ "&stepPath=" + filePathGol + "&stepIndex="
								+ (currStep++),
						dataType : "json",
						success : function(data, textStatus, jqXHR) {
							var series = ganttChart.series[0];
							//分步画甘特条
							$.each(data, function(name, d) {
								series.addPoint({
									start : d.start,
									end : d.end,
									y : d.y,
									name : d.name,
									milestone : d.milestone,
									color : d.color
								});
								activeLastPointToolip(ganttChart);
							});
						}
					});
				}, newTimeout);
			}

		}
		function flush(){
			 $("div").remove(".result");
			 initScheduleResult();
		}
	</script>
	<div id='wrap'>
		<div id='schedule-result'>
			<h4>学习结果</h4>
			<p></p>
		</div>
		<div id='timeout'>
		<span>动画速度(s)：</span>
			<input id='timeoutInput' value='1' style = "width:100"
				onchange="onIntervalChange(this.id)"></input>
				<input type="button" value="刷新" onClick="flush()" />
		</div>
		<span>当前目标值：</span>
			<input id='currCmaxInput' value='0' style = "width:100" disabled="disabled"></input>
		<div id="container"
			style="width: 100%; height: 100%; margin: 0 auto; padding: 0"></div>
		<div style=''></div>
	</div>
</body>
</html>
