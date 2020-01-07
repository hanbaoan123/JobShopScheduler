package mm_scheduler.instanceScheduler.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.deeplearning4j.rl4j.util.Constants;

import com.alibaba.fastjson.JSON;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import mm_scheduler.instanceScheduler.constant.InstanceConstant;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.CandidateProcess;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.FJSPInstance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Machine;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Part;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.SMInstance;
import mm_scheduler.instanceScheduler.instance.domain.solution.InstanceSolution;
import mm_scheduler.instanceScheduler.instance.domain.solution.InstanceSolutionStep;

/**
 * 
 * @author: hba
 * @description: 文件处理类，用于从本地读取案例、展示调度结果，并可以输出学习过程至本地。
 * @date: 下午4:24:40
 *
 */
public class FileHandle {
	/**
	 * home路径
	 */
	final private String home = System.getProperty("user.home");
	final private ObjectMapper mapper = new ObjectMapper();
	/**
	 * 状态root目录：rl4j-data
	 */
	private String stateRoot = home + "/" + Constants.DATA_DIR;
	/**
	 * 调度数据root目录：schedule-result-data
	 */
	private String dataRoot = home + "/" + InstanceConstant.RESULT_DIR;
	/**
	 * 调度案例路径
	 */
	private String instanceRoot = home + "/instance/";
	/**
	 * 当前路径
	 */
	private String currentDir;

	/**
	 * 按照区域读取Excel数据
	 * 
	 * @param filePath
	 *            文件路径
	 * @param sheetINdex
	 *            表单索引
	 * @param fromRow
	 *            开始行，从0开始
	 * @param toRow
	 *            结束行
	 * @param fromCol
	 *            开始列
	 * @param toCol
	 *            结束列
	 */
	public Object[][] readExcelByRegion(String filePath, int sheetINdex, int fromRow, int toRow, int fromCol,
			int toCol) {
		Object[][] list = null;
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook rwb = Workbook.getWorkbook(is);
			// 这里有两种方法获取sheet表:名字和下标（从0开始）
			// Sheet st = rwb.getSheet("original");
			// Sheet st = rwb.getSheet(0);
			// Sheet的下标是从0开始
			// 获取第一张Sheet表
			Sheet rst = rwb.getSheet(sheetINdex);
			// 获取Sheet表中所包含的总列数
			int rsColumns = rst.getColumns();
			// System.out.println(rsColumns);
			// 获取Sheet表中所包含的总行数
			int rsRows = rst.getRows();
			// 获取指定单元格的对象引用
			list = new Object[rsRows][rsColumns];
			int col, row;
			col = 0;
			row = 0;
			for (int i = fromRow; i <= toRow; i++) {
				for (int j = fromCol; j <= toCol; j++) {
					Cell cell = rst.getCell(j, i);
					System.out.print(cell.getContents() + " ");
					list[row][col] = cell.getContents();
					col++;
				}
				System.out.println();
				row++;
				col = 0;
			}
			// 关闭
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 读取excel中某个sheet的所有数据
	 * 
	 * @param filePath
	 * @param sheetINdex
	 * @return
	 */
	public Object[][] readExcel(String filePath, Object sheetINdex) {
		Object[][] list = null;
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook rwb = Workbook.getWorkbook(is);
			// 这里有两种方法获取sheet表:名字和下标（从0开始）
			// Sheet st = rwb.getSheet("original");
			// Sheet st = rwb.getSheet(0);
			// Sheet的下标是从0开始
			// 获取第一张Sheet表
			Sheet rst = null;
			if (sheetINdex instanceof String) {
				rst = rwb.getSheet((String) sheetINdex);
			}
			if (sheetINdex instanceof Integer) {
				rst = rwb.getSheet((Integer) sheetINdex);
			}

			// 获取Sheet表中所包含的总列数
			int rsColumns = rst.getColumns();
			// System.out.println(rsColumns);
			// 获取Sheet表中所包含的总行数
			int rsRows = rst.getRows();
			// 获取指定单元格的对象引用
			list = new Object[rsRows][rsColumns];
			int col, row;
			col = 0;
			row = 0;
			for (int i = 0; i < rsRows; i++) {
				for (int j = 0; j < rsColumns; j++) {
					Cell cell = rst.getCell(j, i);
					// System.out.print(cell.getContents() + " ");
					list[row][col] = cell.getContents();
					col++;
				}
				// System.out.println();
				row++;
				col = 0;
			}
			// 关闭
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Object[] readExcelByRow(String filePath, int sheetINdex, int rowIndex) {
		Object[] list = null;
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook rwb = Workbook.getWorkbook(is);
			// 这里有两种方法获取sheet表:名字和下标（从0开始）
			// Sheet st = rwb.getSheet("original");
			// Sheet st = rwb.getSheet(0);
			// Sheet的下标是从0开始
			// 获取第一张Sheet表
			Sheet rst = rwb.getSheet(sheetINdex);
			// 获取Sheet表中所包含的总列数
			int rsColumns = rst.getColumns();
			// System.out.println(rsColumns);
			// 获取Sheet表中所包含的总行数
			// 获取指定单元格的对象引用
			int col = 0;
			boolean insetContents = false;// 判断是否开始插入数据
			for (int j = 0; j < rsColumns; j++) {
				Cell cell = rst.getCell(j, rowIndex);
				if (!insetContents) {
					if (cell.getContents() != "" && cell.getContents() != null) {
						list = new Object[rsColumns - j];
						insetContents = true;
						System.out.print(cell.getContents() + " ");
						list[col] = cell.getContents();
						col++;
					}
				} else {
					System.out.print(cell.getContents() + " ");
					list[col] = cell.getContents();
					col++;
				}
			}
			System.out.println();

			// 关闭
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void writeExcel(OutputStream os) {
		try {
			/**
			 * 只能通过API提供的 工厂方法来创建Workbook，而不能使用WritableWorkbook的构造函数，
			 * 因为类WritableWorkbook的构造函数为 protected类型： 方法一：直接从目标文件中读取
			 * WritableWorkbook wwb = Workbook.createWorkbook(new
			 * File(targetfile)); 方法 二：如下实例所示 将WritableWorkbook直接写入到输出流
			 */
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			// 创建Excel工作表 指定名称和位置
			WritableSheet ws = wwb.createSheet("Test Sheet 1", 0);
			/** ************往工作表中添加数据**************** */
			// 1.添加Label对象
			Label label = new Label(0, 0, "测试");
			ws.addCell(label);
			// 添加带有字型Formatting对象
			WritableFont wf = new WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD, true);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			Label labelcf = new Label(1, 0, "this is a label test", wcf);
			ws.addCell(labelcf);
			// 添加带有字体颜色的Formatting对象
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_YELLOW);
			WritableCellFormat wcfFC = new WritableCellFormat(wfc);
			Label labelCF = new Label(1, 0, "Ok", wcfFC);
			ws.addCell(labelCF);
			// 2.添加Number对象
			Number labelN = new Number(0, 1, 3.1415926);
			ws.addCell(labelN);
			// 添加带有formatting的Number对象
			NumberFormat nf = new NumberFormat("#.##");
			WritableCellFormat wcfN = new WritableCellFormat(nf);
			Number labelNF = new jxl.write.Number(1, 1, 3.1415926, wcfN);
			ws.addCell(labelNF);

			// 3.添加Boolean对象
			jxl.write.Boolean labelB = new jxl.write.Boolean(0, 2, true);
			ws.addCell(labelB);
			jxl.write.Boolean labelB1 = new jxl.write.Boolean(1, 2, false);
			ws.addCell(labelB1);
			// 4.添加DateTime对象
			jxl.write.DateTime labelDT = new jxl.write.DateTime(0, 3, new java.util.Date());
			ws.addCell(labelDT);
			// 5.添加带有formatting的DateFormat对象
			DateFormat df = new DateFormat("dd MM yyyy hh:mm:ss");
			WritableCellFormat wcfDF = new WritableCellFormat(df);
			DateTime labelDTF = new DateTime(1, 3, new java.util.Date(), wcfDF);
			ws.addCell(labelDTF);
			// 6.添加图片对象,jxl只支持png格式图片
			// File image = new File("d:\\1.png");
			// WritableImage wimage = new WritableImage(0,4,6,17,image);
			// ws.addImage(wimage);
			// 7.写入工作表
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String createSubdir() throws IOException {

		File dr = new File(dataRoot);
		dr.mkdirs();
		File[] rootChildren = dr.listFiles();

		int i = 1;
		while (childrenExist(rootChildren, i + ""))
			i++;

		File f = new File(dataRoot + "/" + i);
		f.mkdirs();

		File epochDr = new File(dataRoot + "/" + i + "/epoch");
		epochDr.mkdirs();

		File qDr = new File(dataRoot + "/" + i + "/q");
		qDr.mkdirs();

		currentDir = f.getAbsolutePath();
		// log.info("Created training data directory: " + currentDir);

		File result = new File(currentDir + "/result");
		result.createNewFile();
		File partQModel = new File(currentDir + "/PartQModel");
		partQModel.createNewFile();
		File deviceQModel = new File(currentDir + "/DeviceQModel");
		deviceQModel.createNewFile();
		File statEntry = new File(currentDir + "/stat");
		statEntry.createNewFile();

		return f.getAbsolutePath();
	}

	public void writeInfo(String toWrite) throws IOException {

		Path infoPath = Paths.get(currentDir + "/result");

		Files.write(infoPath, toWrite.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
	}

	public void writePartQModel(String toWrite) throws IOException {

		Path infoPath = Paths.get(currentDir + "/PartQModel");

		Files.write(infoPath, toWrite.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
	}

	public void writeDeviceQModel(String toWrite) throws IOException {

		Path infoPath = Paths.get(currentDir + "/DeviceQModel");

		Files.write(infoPath, toWrite.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
	}

	/**
	 * 
	 * hba
	 * 
	 * @param path
	 *            路径
	 * @param fileName
	 *            文件名称
	 * @param instance
	 *            案例
	 * @throws IOException
	 *             上午11:28:29
	 */
	public void writeInstance(String path, long fileName, Instance instance) throws IOException {

		File f = new File(instanceRoot + "/" + path + "/sample/" + fileName + "/" + fileName + ".ins");
		if (!f.exists()) {
			mkdir(f.getParentFile());
			f.createNewFile();
		}
		Path infoPath = Paths.get(instanceRoot + "/" + path + "/sample/" + fileName + "/" + fileName + ".ins");
		String toWrite = mapper.writeValueAsString(instance) + "\n";

		Files.write(infoPath, toWrite.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
	}

	private void mkdir(File dir) {
		// TODO Auto-generated method stub
		if (!dir.getParentFile().exists()) {
			mkdir(dir.getParentFile());
		}
		dir.mkdir();
	}

	public void writeInstanceSolution(String path, String fileName, InstanceSolution solution) throws IOException {
		File f = new File(instanceRoot + "/" + path + "/sample/" + fileName + "/" + fileName + "_solution");
		if (!f.exists()) {
			mkdir(f.getParentFile());
			f.createNewFile();
		}
		Path infoPath = Paths.get(instanceRoot + "/" + path + "/sample/" + fileName + "/" + fileName + "_solution");
		String toWrite = mapper.writeValueAsString(solution) + "\n";

		Files.write(infoPath, toWrite.getBytes(), StandardOpenOption.APPEND);
	}

	/**
	 * 
	 * @author: hba
	 * @description: 根据案例规模读取本地案例
	 * @param instanceScale（6x6）
	 * @return
	 * @throws IOException
	 * @date: 下午4:31:12
	 *
	 */
	public List<Instance> readInstance(String instanceScale) throws IOException {

		List<File> files = new ArrayList<File>();
		getFiles(files, instanceRoot + instanceScale);
		List<String> list = new ArrayList<>();
		if (files.size() <= 0) {
			return null;
		}
		// 按修改时间升序排列
		Collections.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		List<Instance> instances = new ArrayList<>();
		for (File f : files) {
			// 定义一个fileReader对象，用来初始化BufferedReader
			FileReader reader = new FileReader(f.getAbsolutePath());
			// new一个BufferedReader对象，将文件内容读取到缓存
			BufferedReader bReader = new BufferedReader(reader);
			String s = "";
			// 逐行读取文件内容，不读取换行符和末尾的空格
			while ((s = bReader.readLine()) != null) {
				// sb.append(s + "\n");// 将读取的字符串添加换行符后累加存放在缓存中
				list.add(s);
			}
			bReader.close();
		}
		// 转换成对象
		if (list != null) {
			for (String string : list) {
				Instance instance = JSON.parseObject(string, Instance.class);
				instances.add(instance);
			}
		}
		return instances;
	}

	private void getFiles(List<File> files, String instancePath) {
		// TODO Auto-generated method stub
		File file = new File(instancePath);// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles();
		if (fileList.length > 0) {
			for (File f1 : fileList) {
				if (f1.isFile() && f1.getName().endsWith(".ins")) {
					files.add(f1);
				}
				if (f1.isDirectory()) {
					getFiles(files, f1.getAbsolutePath());
				}
			}
		}
	}

	public File[] readInstancePath() throws IOException {

		File file = new File(instanceRoot);// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles();
		if (fileList == null || fileList.length <= 0) {
			return null;
		}
		Arrays.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return -1;
				else if (diff == 0)
					return 0;
				else
					return 1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		return fileList;
	}

	public File[] readInstanceResult(String path) throws IOException {

		File file = new File(path);// 定义一个file对象，用来初始化FileReader
		File[] fileList = new File[1];
		if (file != null) {
			fileList[0] = file;
		}
		return fileList;
	}

	/**
	 * 根据案例分类路径获取案例文件夹（1,2,3等） hba
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 *             上午10:44:15
	 */
	public File[] readInstanceIndex(String path) throws IOException {

		File file = new File(path);// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles();
		if (fileList == null || fileList.length <= 0) {
			return null;
		}

		Arrays.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return -1;
				else if (diff == 0)
					return 0;
				else
					return 1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		return fileList;
	}


	public List<String> readDeepState() throws IOException {

		File file = new File(stateRoot);// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles();
		List<String> list = new ArrayList<>();
		if (fileList.length <= 0) {
			return list;
		}
		Arrays.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		File modelFile = fileList[fileList.length - 1];
		FileReader reader = new FileReader(modelFile.getAbsolutePath() + "/state");// 定义一个fileReader对象，用来初始化BufferedReader
		BufferedReader bReader = new BufferedReader(reader);// new一个BufferedReader对象，将文件内容读取到缓存
		String s = "";
		while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
			// sb.append(s + "\n");// 将读取的字符串添加换行符后累加存放在缓存中
			list.add(s);
		}
		bReader.close();
		return list;
	}

	public String readDeviceQModel() throws IOException {

		File file = new File(dataRoot);// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles();
		if (fileList.length <= 0) {
			return "";
		}
		Arrays.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});

		File modelFile = fileList[fileList.length - 1];
		FileReader reader = new FileReader(modelFile.getAbsolutePath() + "/DeviceQModel");// 定义一个fileReader对象，用来初始化BufferedReader
		BufferedReader bReader = new BufferedReader(reader);// new一个BufferedReader对象，将文件内容读取到缓存
		StringBuilder sb = new StringBuilder();// 定义一个字符串缓存，将字符串存放缓存中
		String s = "";
		while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
			sb.append(s + "\n");// 将读取的字符串添加换行符后累加存放在缓存中
		}
		bReader.close();
		String str = sb.toString();
		return str;
	}

	public List<String> readTabularQLStatEntry() throws IOException {

		File file = new File(dataRoot);// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles();
		List<String> list = new ArrayList<>();
		if (fileList.length <= 0) {
			return list;
		}
		Arrays.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		File modelFile = fileList[fileList.length - 1];
		FileReader reader = new FileReader(modelFile.getAbsolutePath() + "/stat");// 定义一个fileReader对象，用来初始化BufferedReader
		BufferedReader bReader = new BufferedReader(reader);// new一个BufferedReader对象，将文件内容读取到缓存
		String s = "";
		while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
			// sb.append(s + "\n");// 将读取的字符串添加换行符后累加存放在缓存中
			list.add(s);
		}
		bReader.close();
		return list;
	}

	private boolean childrenExist(File[] files, String children) {
		boolean exists = false;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(children)) {
				exists = true;
				break;
			}
		}
		return exists;
	}

	/**
	 * 读取调度结果目录 Administrator
	 * 
	 * @return
	 * @throws IOException
	 *             上午11:54:15
	 */
	public File[] readScheduleResult() throws IOException {

		File file = new File(dataRoot);// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles();
		if (fileList.length <= 0) {
			return null;
		}
		Arrays.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return -1;
				else if (diff == 0)
					return 0;
				else
					return 1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		return fileList;
	}

	/**
	 * 读取所有代调度结果 Administrator
	 * 
	 * @param scheduleResultPath
	 *            调度结果路径
	 * @return
	 * @throws IOException
	 *             上午11:57:52
	 */
	public File[] readEpochResult(String scheduleResultPath) throws IOException {

		File file = new File(scheduleResultPath + "\\epoch");// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles();
		if (fileList == null || fileList.length <= 0) {
			return null;
		}
		Arrays.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		return fileList;
	}

	public File[] readEpochQ(String qPath) throws IOException {

		File file = new File(qPath + "\\q");// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles();
		if (fileList == null || fileList.length <= 0) {
			return null;
		}
		Arrays.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		return fileList;
	}

	/**
	 * 读取具体的一个调度结果 Administrator
	 * 
	 * @param epochPath
	 * @return
	 * @throws IOException
	 *             下午12:19:10
	 */
	public List<String> readSomeEpoch(String epochPath, int line) throws IOException {
		List<String> list = new ArrayList<>();
		FileReader reader = new FileReader(epochPath);// 定义一个fileReader对象，用来初始化BufferedReader
		LineNumberReader bReader = new LineNumberReader(reader);
		// BufferedReader bReader = new BufferedReader(reader);//
		// new一个BufferedReader对象，将文件内容读取到缓存

		String s = "";
		bReader.setLineNumber(line);
		int lines = 0;
		while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
			if (lines == line) {
				list.add(s);
				break;
			}
			lines++;
		}
		bReader.close();
		return list;
	}

	public List<String> readSomeInstanceStep(String stepPath, int line) throws IOException {
		List<String> list = new ArrayList<>();
		FileReader reader = new FileReader(stepPath);// 定义一个fileReader对象，用来初始化BufferedReader
		LineNumberReader bReader = new LineNumberReader(reader);
		// BufferedReader bReader = new BufferedReader(reader);//
		// new一个BufferedReader对象，将文件内容读取到缓存

		String s = "";
		bReader.setLineNumber(line);
		int lines = 0;
		while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
			if (lines == line) {
				list.add(s);
				break;
			}
			lines++;
		}
		bReader.close();
		return list;
	}

	public List<String> readSomeQ(String epochPath) throws IOException {
		List<String> list = new ArrayList<>();
		FileReader reader = new FileReader(epochPath);// 定义一个fileReader对象，用来初始化BufferedReader
		LineNumberReader bReader = new LineNumberReader(reader);
		// BufferedReader bReader = new BufferedReader(reader);//
		// new一个BufferedReader对象，将文件内容读取到缓存

		String s = "";
		@SuppressWarnings("unused")
		int lines = 0;
		while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
			list.add(s);
			lines++;
		}
		bReader.close();
		return list;
	}

	/**
	 * 案例调度中调度步存储，用于动画显示 hba
	 * 
	 * @param instanceCategory
	 * @param string
	 * @param instanceSolutionStep
	 *            上午9:30:24
	 * @throws IOException
	 */
	public void writeInstanceSolutionStep(String path, String index, String fileName,
			InstanceSolutionStep instanceSolutionStep) throws IOException {
		// TODO Auto-generated method stub

		File f = new File(instanceRoot + "/" + path + "/sample/" + index + "/" + fileName + "_dynamic");
		if (!f.exists()) {
			mkdir(f.getParentFile());
			f.createNewFile();
		}
		Path infoPath = Paths.get(instanceRoot + "/" + path + "/sample/" + index + "/" + fileName + "_dynamic");
		String toWrite = mapper.writeValueAsString(instanceSolutionStep) + "\n";

		Files.write(infoPath, toWrite.getBytes(), StandardOpenOption.APPEND);

	}

	/**
	 * 读取案例动态分派结果 hba
	 * 
	 * @param scheduleResultPath
	 * @return 上午10:59:46
	 */
	public File[] readInstanceSolutionStep(String scheduleResultPath) {

		File file = new File(scheduleResultPath);// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// 把dir 和name都封装到一个文件对象里
				File file = new File(dir, name);
				return file.isFile() && file.getName().contains("dynamic");
			}
		});
		if (fileList == null || fileList.length <= 0) {
			return null;
		}

		Arrays.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return -1;
				else if (diff == 0)
					return 0;
				else
					return 1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		return fileList;
	}

	/**
	 * 读取案例调度结果汇总 hba
	 * 
	 * @param scheduleResultPath
	 * @return 下午7:21:29
	 */
	public List<String> readInstanceSolution(String instanceSolutionPath) throws IOException {

		File file = new File(instanceSolutionPath);// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// 把dir 和name都封装到一个文件对象里
				File file = new File(dir, name);
				return file.isFile() && file.getName().contains("solution");
			}
		});
		if (fileList == null || fileList.length <= 0) {
			return null;
		}

		List<String> list = new ArrayList<>();
		for (File f : fileList) {
			FileReader reader = new FileReader(f.getAbsolutePath());// 定义一个fileReader对象，用来初始化BufferedReader
			LineNumberReader bReader = new LineNumberReader(reader);
			// BufferedReader bReader = new BufferedReader(reader);//
			// new一个BufferedReader对象，将文件内容读取到缓存

			String s = "";
			@SuppressWarnings("unused")
			int lines = 0;
			while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
				list.add(s);
				lines++;
			}
			bReader.close();
		}
		return list;
	}

	/**
	 * 比较各算法每步分派时制造期的变化 hba
	 * 
	 * @param instanceSolutionPath
	 * @return
	 * @throws IOException
	 *             下午9:21:56
	 */
	@SuppressWarnings("rawtypes")
	public List<List> readInstanceSolutionDynamic(String instanceSolutionPath) throws IOException {
		List<List> returnList = new ArrayList<List>();
		File file = new File(instanceSolutionPath);// 定义一个file对象，用来初始化FileReader
		File[] fileList = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// 把dir 和name都封装到一个文件对象里
				File file = new File(dir, name);
				return file.isFile() && file.getName().contains("dynamic");
			}
		});
		if (fileList == null || fileList.length <= 0) {
			return null;
		}

		for (File f : fileList) {
			List<String> list = new ArrayList<>();
			FileReader reader = new FileReader(f.getAbsolutePath());// 定义一个fileReader对象，用来初始化BufferedReader
			LineNumberReader bReader = new LineNumberReader(reader);
			// BufferedReader bReader = new BufferedReader(reader);//
			// new一个BufferedReader对象，将文件内容读取到缓存

			String s = "";
			@SuppressWarnings("unused")
			int lines = 0;
			while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
				list.add(s);
				lines++;
			}
			bReader.close();
			returnList.add(list);
		}
		return returnList;
	}

	/**
	 * 
	 * @author: hba
	 * @description: 读取txt文件
	 * @param txtPath
	 * @return
	 * @throws IOException
	 * @date: 2019年12月16日
	 *
	 */
	public List<String> readTxt(String txtPath) throws IOException {
		List<String> list = new ArrayList<>();
		FileReader reader = new FileReader(txtPath);// 定义一个fileReader对象，用来初始化BufferedReader
		BufferedReader bReader = new BufferedReader(reader);// new一个BufferedReader对象，将文件内容读取到缓存
		String s = "";
		while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
			// sb.append(s + "\n");// 将读取的字符串添加换行符后累加存放在缓存中
			list.add(s);
		}
		bReader.close();
		return list;
	}

	/**
	 * @author: hba
	 * @description: 读取单机调度案例
	 * @param string
	 * @return
	 * @date: 2019年12月17日
	 *
	 */
	public List<Instance> readSingleMachineInstance(String instanceScale) throws IOException {

		List<File> files = new ArrayList<File>();
		getFiles(files, instanceRoot + instanceScale);
		List<String> list = new ArrayList<>();
		if (files.size() <= 0) {
			return null;
		}
		// 按修改时间升序排列
		Collections.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		List<Instance> instances = new ArrayList<>();
		for (File f : files) {
			// 定义一个fileReader对象，用来初始化BufferedReader
			FileReader reader = new FileReader(f.getAbsolutePath());
			// new一个BufferedReader对象，将文件内容读取到缓存
			BufferedReader bReader = new BufferedReader(reader);
			String s = "";
			// 逐行读取文件内容，不读取换行符和末尾的空格
			while ((s = bReader.readLine()) != null) {
				// sb.append(s + "\n");// 将读取的字符串添加换行符后累加存放在缓存中
				list.add(s);
			}
			bReader.close();
		}
		// 转换成对象
		if (list != null) {
			for (String string : list) {
				Instance instance = JSON.parseObject(string, SMInstance.class);
				instances.add(instance);
			}
		}
		return instances;
	}

	/**
	 * 
	 * @author: hba
	 * @description:读取柔性作业车间调度benchmark路径），并转换为标准的调度案例
	 * @param instancePath
	 * @return
	 * @throws IOException
	 * @date: 2019年12月23日
	 *
	 */
	public List<Instance> readFJSPInstances(String instancePath) throws IOException {
		List<Instance> instanceList = new ArrayList<Instance>();
		File file = new File(instancePath);
		String[] filelist = file.list();
		for (int i = 0; i < filelist.length; i++) {
			FJSPInstance instance = new FJSPInstance();
			String node = filelist[i];
			instance.setName(node);
			instance.setInstanceUid(node);
			String fileName = instancePath + "\\" + node;
			System.out.println("案例名称是：" + fileName);
			String lineInfo;
			// 打开一个随机访问文件流，按读方式
			// RandomAccessFile randomFile;
			try {
				BufferedReader bufr = new BufferedReader(new FileReader(fileName));
				try {
					int lineIndex = 0; // lineIndex从0开始，比文件行数少1

					while ((lineInfo = bufr.readLine()) != null) { // readLine方法返回的时候是不带换行符的。
						if (lineIndex == 0) {
							String strAry[] = stringToNum(lineInfo);
							instance.setPartNum(Integer.parseInt(strAry[0]));
							instance.setMachineNum(Integer.parseInt(strAry[1]));
							// 作业车间调度问题
							if (strAry.length <= 2) {
								instance.setMeanOpMachineNum(0);
								instance.setInstanceType(Instance.JSP);
							} else {
								// 柔性作业车间调度问题
								instance.setMeanOpMachineNum(Float.parseFloat(strAry[2]));
								instance.setInstanceType(Instance.FJSP);
							}
							// 赋值设备map
							instance.setMachineMap(createMachine(instance.getMachineNum()));
							// 创造零件map
							instance.setPartMap(new HashMap<String, Part>());
							// 创造工序map
							instance.setOperationMap(new HashMap<String, Operation>());
							// 创造工序可选设备集合map
							instance.setCandidateProMap(new HashMap<String, List<CandidateProcess>>());
						} else if (lineIndex > 0 && lineIndex < instance.getPartNum() + 1) {
							if (instance.getInstanceType() == Instance.FJSP) {
								this.createInstanceForFJSP(instance, lineIndex, lineInfo);
							}
							if (instance.getInstanceType() == Instance.JSP) {
								this.createInstanceForJSP(instance, lineIndex, lineInfo);
							}
						}
						lineIndex = lineIndex + 1;
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					try {
						bufr.close();
						// randomFile.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			instanceList.add(instance);
		}
		return instanceList;
	}

	/**
	 * 
	 * @author: hba
	 * @description:读取柔性作业车间调度benchmark文件（后缀名为.fjs），并转换为标准的调度案例
	 * @param instancePath
	 * @return
	 * @throws IOException
	 * @date: 2019年12月23日
	 *
	 */
	public FJSPInstance readFJSPInstance(String fileName) throws IOException {
		FJSPInstance instance = new FJSPInstance();
		String lineInfo;
		try {
			BufferedReader bufr = new BufferedReader(new FileReader(fileName));
			try {
				int lineIndex = 0; // lineIndex从0开始，比文件行数少1
				while ((lineInfo = bufr.readLine()) != null) { // readLine方法返回的时候是不带换行符的。
					if (lineIndex == 0) {
						String strAry[] = stringToNum(lineInfo);
						instance.setPartNum(Integer.parseInt(strAry[0]));
						instance.setMachineNum(Integer.parseInt(strAry[1]));
						// 作业车间调度问题
						if (strAry.length <= 2) {
							instance.setMeanOpMachineNum(0);
							instance.setInstanceType(Instance.JSP);
						} else {
							// 柔性作业车间调度问题
							instance.setMeanOpMachineNum(Float.parseFloat(strAry[2]));
							instance.setInstanceType(Instance.FJSP);
						}
						// 赋值设备map
						instance.setMachineMap(createMachine(instance.getMachineNum()));
						// 创造零件map
						instance.setPartMap(new HashMap<String, Part>());
						// 创造工序map
						instance.setOperationMap(new HashMap<String, Operation>());
						// 创造工序可选设备集合map
						instance.setCandidateProMap(new HashMap<String, List<CandidateProcess>>());
					} else if (lineIndex > 0 && lineIndex < instance.getPartNum() + 1) {
						if (instance.getInstanceType() == Instance.FJSP) {
							this.createInstanceForFJSP(instance, lineIndex, lineInfo);
						}
						if (instance.getInstanceType() == Instance.JSP) {
							this.createInstanceForJSP(instance, lineIndex, lineInfo);
						}
					}
					lineIndex = lineIndex + 1;
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					bufr.close();
					// randomFile.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return instance;
	}

	/**
	 * 根据字符串生成零件数量，机器数量，工序的平均设备数量
	 * 
	 * @param str
	 * @return
	 */
	public String[] stringToNum(String str) {
		String regex = "\\s+";
		String[] strAry = str.split(regex);
		return strAry;
	}

	/**
	 * 创建设备
	 * 
	 * @param machineNum
	 * @return
	 */
	public Map<String, Machine> createMachine(int machineNum) {
		Map<String, Machine> machineMap = new HashMap<String, Machine>();

		for (int i = 1; i <= machineNum; i++) {
			Machine machine = new Machine(i, "M" + i);
			machineMap.put(machine.getName(), machine);
		}
		return machineMap;
	}

	/**
	 * 根据字符串生成part
	 * 
	 * @param str
	 * @return
	 */
	public Part stringToPart(String str, int lineIndex) {
		Part part = new Part();
		part.setID(lineIndex);
		part.setName("P" + lineIndex);
		// 标准案例 没有投放时间 默认为0
		part.setStart(0);
		String regex = "\\s+";
		String strAry[] = str.split(regex);
		ArrayList<Integer> opStrList = new ArrayList<Integer>();
		List<Integer> opNumList = new ArrayList<Integer>();
		List<Operation> opList = new ArrayList<Operation>();

		for (int i = 0; i < strAry.length; i++) {
			if (strAry[i] != null && strAry[i] != "" && strAry[i].length() != 0 && !strAry[i].isEmpty()) {
				opStrList.add(Integer.parseInt(strAry[i]));
			}
		}
		// fjsp问题按照这样方式获取工序数量
		int operationNum = opStrList.get(0);
		// if (instance.getInstanceType() == Instance.JSP) {
		// // 对于jsp问题，工序数等于机床数
		// operationNum = instance.getMachineNum();
		// }
		opStrList.remove(0);
		// 零件的工序数量
		part.setOperationNum(operationNum);
		for (int i = 0; i < operationNum; i++) {
			// 工序可选设备数
			int opNum = opStrList.get(0);
			for (int j = 0; j <= opNum * 2; j++) {
				opNumList.add(opStrList.get(j));
			}
			Operation operation = stringToOperation(opNumList);
			// Operation operation = new Operation();
			operation.setSeq(i + 1);
			operation.setID(i + 1);
			operation.setPartID(part.getID());
			operation.setName(part.getName() + "_" + operation.getID());
			opList.add(operation);
			opNumList.clear();
			for (int j = 0; j <= opNum * 2; j++) {
				opStrList.remove(0);
			}
		}
		part.setOpList(opList);
		return part;
	}

	/**
	 * 根据字符串生成operation
	 * 
	 * @param str
	 * @return
	 */
	public Operation stringToOperation(List<Integer> opNumList) {
		Operation operation = new Operation();
		int candidateProNum = opNumList.get(0);
		operation.setCandidateProNum(candidateProNum);
		// CandidateProcess cp = new CandidateProcess();
		// operation.setCandidateProList(new ArrayList<CandidateProcess>());
		// for (int j = 0; j < candidateProNum; j++) {
		// CandidateProcess cp =new CandidateProcess();
		// int id = opNumList.get(2*j+1);
		// cp.setMachineID(id);
		// cp.setDuration(opNumList.get(2*j+2));
		// operation.getCandidateProList().add(cp);
		// }

		return operation;
	}

	private void createInstanceForFJSP(Instance instance, int lineIndex, String lineInfo) {
		// TODO Auto-generated method stub
		Part part = stringToPart(lineInfo, lineIndex);
		Map<String, List<CandidateProcess>> candidateProMap = stringToCaMap(lineInfo, lineIndex);
		instance.getCandidateProMap().putAll(candidateProMap);
		// 赋值零件map
		instance.getPartMap().put(part.getName(), part);
		double workTime = 0.0;
		instance.setTotalOpNum(instance.getTotalOpNum() + part.getOperationNum());
		// 赋值工序map
		for (int j = 0; j < part.getOperationNum(); j++) {
			Operation operation = part.getOpList().get(j);
			String operationName = operation.getName();
			instance.getOperationMap().put(operationName, operation);
			double opDua = 0.0;
			for (CandidateProcess candidateProcess : candidateProMap.get(part.getName() + "_" + operation.getID())) {
				opDua += candidateProcess.getDuration();
			}
			workTime += opDua / candidateProMap.get(part.getName() + "_" + operation.getID()).size();
			// 读取工序的可选设备集合
			// instance.getCandidateProMap().put(operationName,
			// candidateProMap.get(operationName));
		}
		part.setDueDate(workTime * 1.5);
		// partList.add(part);
	}

	/**
	 * 根据字符串生成caMap
	 * 
	 * @param str
	 * @param lineIndex
	 * @return
	 */
	public Map<String, List<CandidateProcess>> stringToCaMap(String str, int lineIndex) {
		String regex = "\\s+";
		String strAry[] = str.split(regex);
		ArrayList<Integer> opStrList = new ArrayList<Integer>();
		List<Integer> opNumList = new ArrayList<Integer>();
		for (int i = 0; i < strAry.length; i++) {
			if (strAry[i] != null && strAry[i] != "" && strAry[i].length() != 0 && !strAry[i].isEmpty()) {
				opStrList.add(Integer.parseInt(strAry[i]));
			}
		}
		int operationNum = opStrList.get(0);
		opStrList.remove(0);
		Map<String, List<CandidateProcess>> caMap = new HashMap<String, List<CandidateProcess>>();
		for (int i = 0; i < operationNum; i++) {
			int opNum = opStrList.get(0);

			for (int j = 0; j <= opNum * 2; j++) {
				opNumList.add(opStrList.get(j));
			}
			List<CandidateProcess> caList = stringToCaList(opNumList);
			String opID = "P" + lineIndex + "_" + (i + 1);
			caMap.put(opID, caList);

			opNumList.clear();
			for (int j = 0; j <= opNum * 2; j++) {
				opStrList.remove(0);

			}

		}
		return caMap;

	}

	/**
	 * 根据字符串生成caList
	 * 
	 * @param opNumList
	 * @return
	 */
	public List<CandidateProcess> stringToCaList(List<Integer> opNumList) {
		List<CandidateProcess> caList = new ArrayList<CandidateProcess>();
		int machineNum = opNumList.get(0);

		for (int j = 0; j < machineNum; j++) {
			CandidateProcess cp = new CandidateProcess();
			int id = opNumList.get(2 * j + 1);
			cp.setMachineID(id);
			cp.setMachineName("M" + id);
			cp.setDuration(opNumList.get(2 * j + 2));
			caList.add(cp);
		}
		return caList;
	}

	private void createInstanceForJSP(Instance instance, int lineIndex, String lineInfo) {
		// TODO Auto-generated method stub
		Part part = stringToPartForJSP(instance, lineInfo, lineIndex);
		Map<String, List<CandidateProcess>> candidateProMap = stringToCaMapForJSP(instance, lineInfo, lineIndex);
		instance.getCandidateProMap().putAll(candidateProMap);
		// 赋值零件map
		instance.getPartMap().put(part.getName(), part);
		// 赋值工序map
		for (int j = 0; j < part.getOperationNum(); j++) {
			Operation operation = part.getOpList().get(j);
			String operationName = operation.getName();
			instance.getOperationMap().put(operationName, operation);
			// 读取工序的可选设备集合
			// instance.getCandidateProMap().put(operationName,
			// candidateProMap.get(operationName));
		}
		// partList.add(part);
	}

	private Part stringToPartForJSP(Instance instance, String str, int lineIndex) {
		Part part = new Part();
		part.setID(lineIndex);
		part.setName("P" + lineIndex);
		// 标准案例 没有投放时间 默认为0
		part.setStart(0);
		String regex = "\\s+";
		String strAry[] = str.split(regex);
		ArrayList<Integer> opStrList = new ArrayList<Integer>();
		List<Integer> opNumList = new ArrayList<Integer>();
		List<Operation> opList = new ArrayList<Operation>();

		for (int i = 0; i < strAry.length; i++) {
			if (strAry[i] != null && strAry[i] != "" && strAry[i].length() != 0 && !strAry[i].isEmpty()) {
				opStrList.add(Integer.parseInt(strAry[i]));
			}
		}
		// 对于jsp问题，工序数等于机床数
		int operationNum = instance.getMachineNum();
		// 零件的工序数量
		part.setOperationNum(operationNum);
		double workTime = 0.0;
		for (int i = 0; i < operationNum; i++) {
			// 对于jsp问题没有可选设备集合
			for (int j = 0; j < 2; j++) {
				opNumList.add(opStrList.get(j));
			}
			Operation operation = stringToOperationForJSP(opNumList);
			// Operation operation = new Operation();
			operation.setSeq(i + 1);
			operation.setID(i + 1);
			operation.setPartID(part.getID());
			operation.setName(part.getName() + "_" + operation.getID());
			opList.add(operation);
			workTime += operation.getRunTime();
			opNumList.clear();
			for (int j = 0; j < 2; j++) {
				opStrList.remove(0);
			}
		}
		part.setOpList(opList);
		part.setDueDate(workTime * 1.5);
		return part;
	}

	private Operation stringToOperationForJSP(List<Integer> opNumList) {
		Operation operation = new Operation();
		// int candidateProNum = opNumList.get(0);
		operation.setCandidateProNum(1);
		operation.setMachineID(opNumList.get(0));
		operation.setRunTime(opNumList.get(1));
		return operation;
	}

	private Map<String, List<CandidateProcess>> stringToCaMapForJSP(Instance instance, String str, int lineIndex) {
		String regex = "\\s+";
		String strAry[] = str.split(regex);
		ArrayList<Integer> opStrList = new ArrayList<Integer>();
		List<Integer> opNumList = new ArrayList<Integer>();
		for (int i = 0; i < strAry.length; i++) {
			if (strAry[i] != null && strAry[i] != "" && strAry[i].length() != 0 && !strAry[i].isEmpty()) {
				opStrList.add(Integer.parseInt(strAry[i]));
			}
		}
		int operationNum = instance.getMachineNum();
		Map<String, List<CandidateProcess>> caMap = new HashMap<String, List<CandidateProcess>>();
		for (int i = 0; i < operationNum; i++) {
			int opNum = 1;

			for (int j = 0; j < opNum * 2; j++) {
				opNumList.add(opStrList.get(j));
			}
			List<CandidateProcess> caList = stringToCaListForJSP(opNumList);
			String opID = "P" + lineIndex + "_" + (i + 1);
			caMap.put(opID, caList);

			opNumList.clear();
			for (int j = 0; j < opNum * 2; j++) {
				opStrList.remove(0);
			}
		}
		return caMap;
	}

	private List<CandidateProcess> stringToCaListForJSP(List<Integer> opNumList) {
		List<CandidateProcess> caList = new ArrayList<CandidateProcess>();
		int machineNum = 1;

		for (int j = 0; j < machineNum; j++) {
			CandidateProcess cp = new CandidateProcess();
			int id = opNumList.get(2 * j) + 1;
			cp.setMachineID(id);
			cp.setMachineName("M" + id);
			cp.setDuration(opNumList.get(2 * j) + 1);
			caList.add(cp);
		}
		return caList;
	}
	/**
	 * 
	 * @author: hba
	 * @description: 案例调度中调度步存储，用于动画显示 hba
	 * @param path
	 * @param index
	 * @param fileName
	 * @param instanceSolutionStep
	 * @param isClear
	 *            是否清空已有内容
	 * @throws IOException
	 * @date: 2020年1月4日
	 *
	 */
	public void writeInstanceSolutionStep(String path, String index, String fileName,
			InstanceSolutionStep instanceSolutionStep, boolean isClear) throws IOException {
		// TODO Auto-generated method stub

		File f = new File(instanceRoot + "/" + path + "/sample/" + index + "/" + fileName + "_dynamic");
		if (!f.exists()) {
			mkdir(f.getParentFile());
			f.createNewFile();
		} else {
			if (isClear) {
				FileWriter fileWriter = new FileWriter(f);
				fileWriter.write("");
				fileWriter.flush();
				fileWriter.close();
			}
		}
		Path infoPath = Paths.get(instanceRoot + "/" + path + "/sample/" + index + "/" + fileName + "_dynamic");
		String toWrite = mapper.writeValueAsString(instanceSolutionStep) + "\n";
		Files.write(infoPath, toWrite.getBytes(), StandardOpenOption.APPEND);
	}
}
