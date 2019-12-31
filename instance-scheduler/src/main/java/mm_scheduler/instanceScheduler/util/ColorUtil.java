package mm_scheduler.instanceScheduler.util;

import java.awt.Color;

public class ColorUtil {
	public static ColorMap colorMap = new ColorMap();

	/**
	 * 
	 * @author: hba
	 * @description:将颜色对象转换为字符串
	 * @param color
	 * @return String
	 * @date: 2019年12月12日
	 *
	 */
	public static String colorToString(Color color) {
		// TODO Auto-generated method stub
		try {
			if (color == null) {
				return "";
			}
			return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * @author: hba
	 * @description:通过颜色key得到颜色字符串
	 * @param colorKey
	 * @return String
	 * @date: 2019年12月12日
	 *
	 */
	public static String colorToString(int colorKey) {
		// TODO Auto-generated method stub
		try {
			Color color = colorMap.colorMap.get(colorKey);
			return colorToString(color);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}
}
