/**
 * hba
 */
package mm_scheduler.instanceScheduler.constant;

/**
 * @author: hba
 * @description: 单机调度案例常量
 * @date: 2019年12月17日
 *
 */
public class SMInstanceConstant {
	/**
	 * 学习最大代数
	 */
	public static int maxEpoch = 10000;
	/**
	 * 随机数种子
	 */
	public static int seed = 333;
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
	public static int targetDqnUpdateFreq = 1000;
	/**
	 * 预热步数
	 */
	public static int updateStart = 10000;
	/**
	 * 报酬比例
	 */
	public static double rewardFactor = 0.001;
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
	public static double errorClamp = 10.0;
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
	public static int numHiddenNodes = 5;
	/**
	 * 用参数向量的L2范数作为正则化项
	 */
	public static double l2 = 0.001;
	/**
	 * 工件状态数量
	 */
	public static int OPERATION_State_Count = 3;
	/**
	 * 工件行为数量
	 */
	public static final int OPERATION_Action_Count = 6;
	/**
	 * 机床状态数量
	 */
	public static final int MACHINE_State_Count = 10;
	/**
	 * 机床行为数量
	 */
	public static final int MACHINE_Action_Count = 7;
}
