package mm_scheduler.instanceScheduler.constant;
/**
 * 
 * @author: hba
 * @description: define the constant used in all classes
 * @date:上午9:51:36
 *
 */
public class InstanceConstant {
	/**
	 * 学习最大代数
	 */
	public static int maxEpoch = 10000;
	/**
	 * 随机数种子
	 */
	public static int seed = 123;
	/**
	 * 每代最大学习步数
	 */
	public static int maxEpochStep = 2000000;
	/**
	 * 总学习步数
	 */
	public static int maxStep = 29000;
	/**
	 * 经验回放的最大容量
	 */
	public static int expRepMaxSize = 100000;
	/**
	 * 经验回放采样批量
	 */
	public static int batchSize = 32;
	/**
	 * 目标网络更新频率
	 */
	public static int targetDqnUpdateFreq = 500;
	/**
	 * 预热步数
	 */
	public static int updateStart = 1000;
	/**
	 * 报酬比例
	 */
	public static double rewardFactor = 100;
	/**
	 * 报酬函数
	 */
	public static int rewardFunc = 2;// RewardManager.REWARD_AVERAGE_DEVICE_UTIL;
	/**
	 * 折扣率
	 */
	public static double gamma = 1.0;
	/**
	 * td-error clipping
	 */
	public static double errorClamp = 1.0;
	/**
	 * 最小的贪婪率
	 */
	public static float minEpsilon = 0.1f;
	/**
	 * num step for eps greedy anneal（eps下降频率）
	 */
	public static int epsilonNbStep = 19000;
	/**
	 * 是否采用双DQN
	 */
	public static boolean doubleDQN = true;
	/**
	 * 是否采用在线训练
	 */
	public static boolean trainOnLine = false;
	/**
	 * 是否采用延迟报酬
	 */
	public static boolean delayReward = false;
	/**
	 * 行为模式(0:规则选择，1：工序选择)
	 */
	public static int actionSelection = 0;
	/**
	 * 特征类型(0:一维向量，1：二维矩阵，2：图像)
	 */
	public static int featureType = 0;
	/**
	 * 隐藏层数
	 */
	public static int numLayer = 5;
	/**
	 * 隐藏层接节点数
	 */
	public static int numHiddenNodes = 32;
	/**
	 * 用参数向量的L2范数作为正则化项
	 */
	public static double l2 = 0.001;
	/**
	 * 设备数量，计算平均利用率时使用
	 */
	public static double deviceNum = 10;

	/**
	 * 工件状态数量
	 */
	public static int OPERATION_State_Count = 116;
	/**
	 * 工件行为数量
	 */
	public static final int OPERATION_Action_Count = 6;
	/**
	 * 工件状态划分
	 */
	public static final int OPERATION_State_Divide = 10;
	/**
	 * 机床状态数量
	 */
	public static final int MACHINE_State_Count = 10;
	/**
	 * 机床行为数量
	 */
	public static final int MACHINE_Action_Count = 7;
	/**
	 * 机床状态划分
	 */
	public static final double MACHINE_State_Divide = 0.1;
	/**
	 * 学习代数
	 */
	public static int Learn_Episode = 1000;
	/**
	 * 学习率
	 */
	public static double alpha = 0.1;

	/**
	 * 初始Q值
	 */
	public static double initQ = 0;
	/**
	 * 调度结果目录
	 */
	final public static String RESULT_DIR = "schedule-result-data";
	/**
	 * Q模型存储目录
	 */
	final public static String QMODEL_DIR = "schedule-result-data";
}
