package com.scheduler.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;

import mm_scheduler.instanceScheduler.instance.domain.solution.InstanceSolutionStep;
import mm_scheduler.instanceScheduler.util.FileHandle;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author hba 2018年8月1日16:48:06
 */
@Component("chartServlet")
public class ChartServlet extends HttpServlet {
	SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat dFormatDate = new SimpleDateFormat("MM-dd");

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext application = config.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("METHOD");
		String scheduleResultPath = request.getParameter("scheduleResultPath");
		String instanceStepPath = request.getParameter("instanceStepPath");
		String stepPath = request.getParameter("stepPath");
		String stepIndex = request.getParameter("stepIndex");
		String instanceIndexPath = request.getParameter("instanceIndexPath");
		response.setContentType("application/json;charset=utf-8");// 指定返回的格式为JSON格式
		response.setCharacterEncoding("UTF-8");// setContentType与setCharacterEncoding的顺序不能调换，否则还是无法解决中文乱码的问题
		JSONArray jsonArray = new JSONArray();
		try {
			switch (method) {
			// 调度案例获取（instance下）
			case "getInstanceSolutionPath":
				jsonArray = this.getInstanceSolutionPath();
				break;
			// 获取调度案例结果所在文件
			case "getInstanceIndex":
				jsonArray = this.getInstanceIndex(scheduleResultPath);
				break;
			case "getInstanceSolutionStep":
				jsonArray = this.getInstanceSolutionStep(instanceIndexPath);
				break;
			// 获取调度案例的机床
			case "getInstanceRes":
				jsonArray = this.getInstanceRes(instanceStepPath);
				break;
			// 获取调度案例结果中的某一行，即第几次分派
			case "getSomeStep":
				jsonArray = this.getSomeStep(stepPath, stepIndex);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(jsonArray.toString());
		out.close();

	}

	private JSONArray getInstanceSolutionPath() {
		// TODO Auto-generated method stub
		JSONArray jsonArray = new JSONArray();
		FileHandle fileHandle = new FileHandle();
		try {
			File[] files = fileHandle.readInstancePath();
			if (files != null && files.length > 0) {
				for (File file : files) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", file.getName());
					jsonObject.put("path", file.getAbsolutePath());
					jsonObject.put("name", file.getName());
					jsonArray.add(jsonObject);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonArray;
	}

	private JSONArray getInstanceIndex(String scheduleResultPath) {
		// TODO Auto-generated method stub
		JSONArray jsonArray = new JSONArray();
		FileHandle fileHandle = new FileHandle();
		try {
			File[] files = fileHandle.readInstanceIndex(scheduleResultPath + "/sample");
			if (files != null && files.length > 0) {
				for (File file : files) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", file.getName());
					jsonObject.put("path", file.getAbsolutePath());
					jsonObject.put("name", file.getName());
					jsonArray.add(jsonObject);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;
	}

	private JSONArray getInstanceSolutionStep(String scheduleResultPath) {
		// TODO Auto-generated method stub
		JSONArray jsonArray = new JSONArray();
		FileHandle fileHandle = new FileHandle();
		try {
			File[] files = fileHandle.readInstanceSolutionStep(scheduleResultPath);
			if (files != null && files.length > 0) {
				for (File file : files) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", file.getName());
					jsonObject.put("path", file.getAbsolutePath());
					jsonObject.put("name", file.getName());
					jsonArray.add(jsonObject);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;
	}

	private JSONArray getInstanceRes(String instanceStepPath) {
		// TODO Auto-generated method stub
		JSONArray jsonArray = new JSONArray();
		FileHandle fileHandle = new FileHandle();
		try {
			List<String> list = fileHandle.readSomeInstanceStep(instanceStepPath, 1);
			for (String obj : list) {
				InstanceSolutionStep step = JSON.parseObject(obj, InstanceSolutionStep.class);
				for (int i = 1; i <= step.getMachineNum(); i++) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("deviceUid", i);
					jsonObject.put("deviceName", "M" + i);
					jsonArray.add(jsonObject);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;
	}

	private JSONArray getSomeStep(String instanceStepPath, String epochIndex) {
		// TODO Auto-generated method stub
		JSONArray jsonArray = new JSONArray();
		FileHandle fileHandle = new FileHandle();
		try {
			List<String> list = fileHandle.readSomeInstanceStep(instanceStepPath, Integer.parseInt(epochIndex));
			for (String obj : list) {
				InstanceSolutionStep learnHis = JSON.parseObject(obj, InstanceSolutionStep.class);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("start", learnHis.getStart());
				jsonObject.put("end", learnHis.getEnd());
				jsonObject.put("y", learnHis.getDeviceIndex());
				jsonObject.put("name", learnHis.getTaskName());
				jsonObject.put("color", learnHis.getColor());
				jsonObject.put("currObjective", learnHis.getCurrObjective());
				jsonArray.add(jsonObject);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;
	}

	private JSONArray getInstanceSolutionDynamic(String instanceSolutionPath) {
		// TODO Auto-generated method stub
		JSONArray jsonArray = new JSONArray();

		return jsonArray;
	}

	/**
	 * 格式化JSON时间
	 * 
	 * @param date
	 * @return
	 */
	private String formatJSonTime(Date date) {

		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String datString = (calendar.get(Calendar.HOUR_OF_DAY) > 9 ? calendar.get(Calendar.HOUR_OF_DAY)
				: ("0" + calendar.get(Calendar.HOUR_OF_DAY))) + ":"
				+ (calendar.get(Calendar.MINUTE) > 9 ? calendar.get(Calendar.MINUTE)
						: ("0" + calendar.get(Calendar.MINUTE)));
		return datString;
	}

	/**
	 * 格式化JSON日期
	 * 
	 * @param date
	 * @return
	 */
	private String formatJSonDateTime(Date date) {

		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String datString = calendar.get(Calendar.YEAR) + "-"
				+ ((calendar.get(Calendar.MONTH) + 1) > 9 ? (calendar.get(Calendar.MONTH) + 1)
						: ("0" + (calendar.get(Calendar.MONTH) + 1)))
				+ "-"
				+ (calendar.get(Calendar.DATE) > 9 ? calendar.get(Calendar.DATE) : ("0" + calendar.get(Calendar.DATE)))
				+ "T"
				+ (calendar.get(Calendar.HOUR_OF_DAY) > 9 ? calendar.get(Calendar.HOUR_OF_DAY)
						: ("0" + calendar.get(Calendar.HOUR_OF_DAY)))
				+ ":" + (calendar.get(Calendar.MINUTE) > 9 ? calendar.get(Calendar.MINUTE)
						: ("0" + calendar.get(Calendar.MINUTE)))
				+ ":" + "00";
		return datString;
	}
}
