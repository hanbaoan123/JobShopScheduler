package mm_scheduler.instanceScheduler.util;

/**  
 * Project Name:mms_svn  
 * File Name:ColorMap.java  
 * Package Name:com.mes.schedule.util  
 * Date:Jan 4, 20178:19:32 PM  
 * Copyright (c) 2017, MM-MES All Rights Reserved.  
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: hba
 * @description: 颜色集合，用于显示在甘特图中不同的工件显示不同的颜色
 * @date: 2019年12月26日
 *
 */
public class ColorMap {
	public Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
	public Map<String, Color> newColorMap = new HashMap<String, Color>();

	public ColorMap() {
		// 11种基本颜色

		colorMap.put(1, new Color(3, 168, 158));
		// 棕褐色
		colorMap.put(2, new Color(210, 180, 140));
		// 青绿色
		colorMap.put(3, new Color(64, 244, 205));
		// 淡紫色
		colorMap.put(4, new Color(218, 112, 214));
		// 黄绿色
		colorMap.put(5, new Color(127, 255, 0));
		// 沙棕色
		colorMap.put(6, Color.ORANGE);
		// 草绿色
		colorMap.put(7, new Color(160, 32, 240));
		// 玫瑰红
		colorMap.put(8, new Color(188, 143, 143));
		// 萝卜黄
		colorMap.put(9, new Color(237, 145, 33));
		// 黑黄
		colorMap.put(10, new Color(85, 102, 0));
		// 粉红
		colorMap.put(11, new Color(255, 192, 203));
		// 象牙灰
		colorMap.put(12, new Color(251, 242, 242));
		// 蛋壳色
		colorMap.put(13, new Color(252, 230, 201));
		// 天蓝灰
		colorMap.put(14, new Color(202, 235, 216));
		// 镉黄
		colorMap.put(15, new Color(85, 170, 255));
		// 番茄红
		colorMap.put(16, new Color(255, 99, 71));
		// 青色
		colorMap.put(17, new Color(255, 255, 0));
		// 土色
		colorMap.put(18, new Color(199, 97, 20));
		// 森林绿
		colorMap.put(19, new Color(34, 139, 34));
		// 金黄
		colorMap.put(20, new Color(255, 215, 20));
		// 砖红
		colorMap.put(21, new Color(156, 102, 31));
		// 暖灰
		colorMap.put(22, new Color(128, 188, 105));
		// 珊瑚红
		colorMap.put(23, new Color(255, 127, 80));
		// 杏仁灰
		colorMap.put(24, new Color(255, 235, 205));
		// 橘黄
		colorMap.put(25, new Color(255, 128, 0));
		// 肖贡土色
		colorMap.put(26, new Color(160, 32, 240));
		// 石板灰
		colorMap.put(27, new Color(188, 138, 135));
		// 印度红
		colorMap.put(28, new Color(176, 23, 31));
		// 镉红
		colorMap.put(29, new Color(227, 23, 13));
		// 石板灰
		colorMap.put(30, new Color(118, 128, 105));
		// 孔雀蓝
		// colorMap.put(1, new Color(51, 161, 201));
		// colorMap.put(2, Color.ORANGE);
		// colorMap.put(3, Color.YELLOW);
		// colorMap.put(4, Color.GREEN);
		// colorMap.put(5, Color.CYAN);
		// colorMap.put(6, new Color(173, 216, 230));
		// colorMap.put(7, Color.PINK);
		// colorMap.put(8, new Color(138, 43, 226));
		// colorMap.put(9, new Color(160, 82, 45));
		// colorMap.put(10, Color.MAGENTA);
		// colorMap.put(11, new Color(128, 42, 42));
		// // 自定义颜色
		// // 冷灰
		// colorMap.put(12, new Color(128, 138, 135));
		// // 蛋壳色
		// colorMap.put(13, new Color(252, 230, 201));
		// // 镉黄
		// colorMap.put(14, new Color(255, 153, 18));
		// // 转红
		// colorMap.put(15, new Color(156, 102, 31));
		// // 草莓色
		// colorMap.put(16, new Color(135, 38, 87));
		// // 香蕉色
		// colorMap.put(17, new Color(227, 207, 87));
		// // Azure4
		// colorMap.put(18, new Color(131, 139, 139));
		// // 玫瑰红
		// colorMap.put(19, new Color(188, 143, 143));
		// // 钴色
		// colorMap.put(20, new Color(61, 89, 171));
		// // 草绿色
		// colorMap.put(21, new Color(107, 142, 35));
		// // 紫色
		// colorMap.put(22, new Color(160, 32, 240));
		// colorMap.put(23, Color.RED);
		// // 胡萝卜色
		// colorMap.put(24, new Color(237, 145, 33));
		// // 米色
		// colorMap.put(25, new Color(163, 148, 128));
		// // jackie blue
		// colorMap.put(26, new Color(11, 23, 70));
		// // 青色
		// colorMap.put(27, new Color(0, 255, 255));
		// // 绿土
		// colorMap.put(28, new Color(56, 94, 15));
		// // 淡紫色
		// colorMap.put(29, new Color(218, 112, 214));
		// // DarkOrchid
		// colorMap.put(30, new Color(191, 62, 255));
		// 浅灰蓝色
		colorMap.put(31, new Color(176, 224, 230));
		// 碧绿色
		colorMap.put(32, new Color(127, 255, 212));
		// 深红色
		colorMap.put(33, new Color(250, 128, 114));
		// 深蓝色
		colorMap.put(34, new Color(25, 25, 112));
		// lightCyan2
		colorMap.put(35, new Color(180, 205, 205));
		// 巧克力色
		colorMap.put(36, new Color(210, 105, 30));
		// 品蓝
		colorMap.put(37, new Color(65, 105, 225));
		// 青绿色
		colorMap.put(38, new Color(64, 224, 208));
		//
		colorMap.put(39, new Color(138, 43, 150));
		// 白色
		colorMap.put(40, Color.WHITE);
		// MistyRose1
		colorMap.put(41, new Color(255, 228, 225));
		// PeachPuff4
		colorMap.put(42, new Color(139, 119, 101));
		// DarkOrange3
		colorMap.put(43, new Color(205, 133, 0));
		colorMap.put(44, new Color(80, 120, 45));
		// 森林绿
		colorMap.put(45, new Color(34, 139, 34));
		// LemonChiffon3
		colorMap.put(46, new Color(205, 201, 165));
		// RosyBrown2
		colorMap.put(47, new Color(238, 180, 180));
		// Goldenrod1
		colorMap.put(48, new Color(255, 193, 37));
		// IndianRed1
		colorMap.put(49, new Color(255, 106, 106));
		// snow2
		colorMap.put(50, new Color(238, 233, 233));
	}
}
