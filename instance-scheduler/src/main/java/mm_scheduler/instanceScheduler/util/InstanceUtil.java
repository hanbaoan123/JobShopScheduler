/**
 * hba
 */
package mm_scheduler.instanceScheduler.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Part;
import mm_scheduler.instanceScheduler.instance.domain.solution.InstanceSolution;
import mm_scheduler.instanceScheduler.instance.domain.solution.InstanceSolutionStep;
import mm_scheduler.instanceScheduler.instance.manager.ScheduleManager;
import mm_scheduler.instanceScheduler.rules.*;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * @author: hba
 * @description: 案例相关工具类，用于设置案例规则，获取调度规则使用的特征等。
 * @date: 上午10:24:14
 *
 */
public class InstanceUtil {
	static FileHandle fileHandle = new FileHandle();
	static ColorMap color = new ColorMap();
	private final static Log log = LogFactory.getLog(InstanceUtil.class);

	/**
	 * 
	 * @author: hba
	 * @description:设置案例中工序的排序规则
	 * @param instance
	 * @param actionId
	 * @throws Exception
	 * @date: 上午10:36:07
	 *
	 */
	public static void setInstanceOperationComparator(Instance instance, int actionId) throws Exception {
		// TODO Auto-generated method stub
		switch (actionId) {
		case Action.PART_ACTION_FOPNR:
			instance.setOperationComparator(new OperationFOPNR());
			break;
		case Action.PART_ACTION_MOPNR:
			instance.setOperationComparator(new OperationMOPNR());
			break;
		case Action.PART_ACTION_SPT:
			instance.setOperationComparator(new OperationSPT());
			break;
		case Action.PART_ACTION_LPT:
			instance.setOperationComparator(new OperationLPT());
			break;
		case Action.PART_ACTION_SRPT:
			instance.setOperationComparator(new OperationSRPT());
			break;
		case Action.PART_ACTION_LRPT:
			instance.setOperationComparator(new OperationLRPT());
			break;
		case Action.PART_ACTION_MINSEQ:
			instance.setOperationComparator(new OperationMINSEQ());
			break;
		case Action.PART_ACTION_EDD:
			instance.setOperationComparator(new OperationEDD());
			break;
		case Action.PART_ACTION_SPT_TWK_RATIO:
			instance.setOperationComparator(new OperationSPTTWKRatio());
			break;
		case Action.PART_ACTION_LPT_TWK_RATIO:
			instance.setOperationComparator(new OperationLPTTWKRatio());
			break;
		case Action.PART_ACTION_SPT_TWKR_RATIO:
			instance.setOperationComparator(new OperationSPTTWKRRatio());
			break;
		case Action.PART_ACTION_LPT_TWKR_RATIO:
			instance.setOperationComparator(new OperationLPTTWKRRatio());
			break;
		case Action.PART_ACTION_SPT_TWK_MULTI:
			instance.setOperationComparator(new OperationSPTTWKMulti());
			break;
		case Action.PART_ACTION_LPT_TWK_MULTI:
			instance.setOperationComparator(new OperationLPTTWKMulti());
			break;
		case Action.PART_ACTION_SPT_TWKR_MULTI:
			instance.setOperationComparator(new OperationSPTTWKRMulti());
			break;
		case Action.PART_ACTION_LPT_TWKR_MULTI:
			instance.setOperationComparator(new OperationLPTTWKRMulti());
			break;
		case Action.PART_ACTION_SRM:
			instance.setOperationComparator(new OperationSRM());
			break;
		case Action.PART_ACTION_LRM:
			instance.setOperationComparator(new OperationLRM());
			break;
		case Action.PART_ACTION_SSO:
			instance.setOperationComparator(new OperationSSO());
			break;
		case Action.PART_ACTION_LSO:
			instance.setOperationComparator(new OperationLSO());
			break;
		case Action.PART_ACTION_SPT_SSO_SUM:
			instance.setOperationComparator(new OperationSPTSSOSum());
			break;
		case Action.PART_ACTION_LPT_LSO_SUM:
			instance.setOperationComparator(new OperationLPTLSOSum());
			break;
		case Action.PART_ACTION_S1:
			instance.setOperationComparator(new OperationS1());
			break;
		case Action.PART_ACTION_S2:
			instance.setOperationComparator(new OperationS2());
			break;
		case Action.PART_ACTION_S3:
			instance.setOperationComparator(new OperationS3());
			break;
		case Action.PART_ACTION_S4:
			instance.setOperationComparator(new OperationS4());
			break;
		case Action.PART_ACTION_S5:
			instance.setOperationComparator(new OperationS5());
			break;
		case Action.PART_ACTION_S6:
			instance.setOperationComparator(new OperationS6());
			break;
		case Action.PART_ACTION_S7:
			instance.setOperationComparator(new OperationS7());
			break;
		case Action.PART_ACTION_S8:
			instance.setOperationComparator(new OperationS8());
			break;
		case Action.PART_ACTION_GW:
			instance.setOperationComparator(new OperationGW());
			break;
		case Action.PART_ACTION_LW:
			instance.setOperationComparator(new OperationLW());
			break;
		case Action.PART_ACTION_WSPT:
			instance.setOperationComparator(new OperationWSPT());
			break;
		case Action.PART_ACTION_WLPT:
			instance.setOperationComparator(new OperationWLPT());
			break;
		// case Action.PART_ACTION_OPNDD:
		// scheme.setOperationTaskComparator(new TaskScheduleComparatorOPNDD());
		// break;
		default:
			log.error("无法找到编号为" + actionId + "的规则，请先在此注册！");
			throw new Exception("无法找到编号为" + actionId + "的规则，请先在此注册！");
			// instance.setOperationComparator(new OperationSPT());
			// break;
		}
	}

	public static void setInstanceOperationComparator(Instance instance, OperationRule operationRule) {
		instance.setOperationComparator(operationRule);
	}

	/**
	 * 
	 * @author: hba
	 * @description: 设置案例中机床的选择规则
	 * @param instance
	 * @param actionId
	 * @date: 上午10:36:39
	 *
	 */
	public static void setInstanceMachineComparator(Instance instance, int actionId) {

	}

	/**
	 * 
	 * @author: hba
	 * @description:获取案例中工序排序所使用的比较器特征
	 * @param operation
	 * @param actionId
	 * @return
	 * @date: 上午10:37:06
	 *
	 */
	public static double getInstanceOperationComparatorFeature(Operation operation, Integer actionId) {
		// TODO Auto-generated method stub
		double f = 0;
		switch (actionId) {
		case Action.PART_ACTION_FOPNR:
		case Action.PART_ACTION_MOPNR:
			f = operation.getRemainOpNum();
			break;
		case Action.PART_ACTION_SPT:
		case Action.PART_ACTION_LPT:
			f = operation.getWorkTime();
			break;
		case Action.PART_ACTION_SRPT:
		case Action.PART_ACTION_LRPT:
			f = operation.getRemainWorkTime();
			break;
		case Action.PART_ACTION_MINSEQ:
			f = operation.getPreTime();
			break;
		case Action.PART_ACTION_EDD:
			f = operation.getPart().getDueDate();
			break;
		case Action.PART_ACTION_SPT_TWK_RATIO:
		case Action.PART_ACTION_LPT_TWK_RATIO:
			f = operation.getWorkTime() / operation.getPart().getTotalWorkTime();
			break;
		case Action.PART_ACTION_SPT_TWKR_RATIO:
		case Action.PART_ACTION_LPT_TWKR_RATIO:
			f = operation.getWorkTime() / operation.getRemainWorkTime();
			break;
		case Action.PART_ACTION_SPT_TWK_MULTI:
		case Action.PART_ACTION_LPT_TWK_MULTI:
			f = operation.getWorkTime() * operation.getPart().getTotalWorkTime();
			break;
		case Action.PART_ACTION_SPT_TWKR_MULTI:
		case Action.PART_ACTION_LPT_TWKR_MULTI:
			f = operation.getWorkTime() * operation.getRemainWorkTime();
			break;
		case Action.PART_ACTION_SRM:
		case Action.PART_ACTION_LRM:
			f = operation.getRemainWorkTime() - operation.getWorkTime();
			break;
		case Action.PART_ACTION_SSO:
		case Action.PART_ACTION_LSO:
			f = operation.getSuccOp() == null ? 0 : operation.getSuccOp().getWorkTime();
			break;
		case Action.PART_ACTION_SPT_SSO_SUM:
		case Action.PART_ACTION_LPT_LSO_SUM:
			f = operation.getWorkTime() + (operation.getSuccOp() == null ? 0 : operation.getSuccOp().getWorkTime());
			break;
		case Action.PART_ACTION_S1:
			f = operation.getPrepOp() == null ? operation.getPart().getDueDate()
					: (operation.getPart().getDueDate() - operation.getPrepOp().getFinish());
			break;
		case Action.PART_ACTION_S2:
			f = (operation.getPrepOp() == null ? operation.getPart().getDueDate()
					: (operation.getPart().getDueDate() - operation.getPrepOp().getFinish()))
					/ operation.getRemainWorkTime();
			break;
		case Action.PART_ACTION_S3:
			f = operation.getPrepOp() == null ? operation.getPart().getDueDate()
					: (operation.getPart().getDueDate() - operation.getPrepOp().getFinish())
							- operation.getRemainWorkTime();
			break;
		case Action.PART_ACTION_S4:
			f = operation.getPrepOp() == null ? operation.getPart().getDueDate()
					: (operation.getPart().getDueDate() - operation.getPrepOp().getFinish()) - operation.getWorkTime();
			break;
		case Action.PART_ACTION_S5:
			f = (operation.getPrepOp() == null ? operation.getPart().getDueDate()
					: (operation.getPart().getDueDate() - operation.getPrepOp().getFinish())) / operation.getWorkTime();
			break;
		case Action.PART_ACTION_S6:
			f = (operation.getPrepOp() == null ? operation.getPart().getDueDate()
					: (operation.getPart().getDueDate() - operation.getPrepOp().getFinish()))
					/ operation.getRemainOpNum();
			break;
		case Action.PART_ACTION_S7:
			f = (operation.getPrepOp() == null ? operation.getPart().getDueDate()
					: (operation.getPart().getDueDate() - operation.getPrepOp().getFinish())
							- operation.getRemainWorkTime())
					/ operation.getRemainOpNum();
			break;
		case Action.PART_ACTION_S8:
			f = (operation.getPrepOp() == null ? operation.getPart().getDueDate()
					: (operation.getPart().getDueDate() - operation.getPrepOp().getFinish())
							- operation.getRemainWorkTime())
					/ operation.getRemainWorkTime();
			break;
		// case Action.PART_ACTION_OPNDD:
		// scheme.setOperationTaskComparator(new TaskScheduleComparatorOPNDD());
		// break;
		default:
			f = operation.getWorkTime();
			break;
		}
		return f;
	}

	/**
	 * 
	 * @author: hba
	 * @description:获取案例中机床选择所使用的比较器特征
	 * @param operation
	 * @param actionId
	 * @return
	 * @date: 上午10:38:12
	 *
	 */
	public static double getInstanceMachineComparatorFeature(Operation operation, Integer actionId) {
		return 0.0;
	}

	@SuppressWarnings("unchecked")
	public static <T> T deepClone(Object o) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(o);
			oos.close();

			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object object = ois.readObject();
			ois.close();
			return (T) object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author: hba
	 * @description:
	 * @param instance
	 * @date: 2019年12月19日
	 *
	 */
	public static void printResult(Instance instance) {
		// TODO Auto-generated method stub
		System.out.println(instance.getOperationComparator().getRuleName() + "**********************");
		System.out.println(
				instance.getObjective().getObjectiveName() + ":" + instance.getObjective().getObjectiveValue());
	}

	/**
	 * 
	 * @author: hba
	 * @description: 计算制造期
	 * @param instance
	 * @return
	 * @date: 2019年12月19日
	 *
	 */
	public static double calcCmax(Instance instance) {
		double cmax = 0;
		for (Part part : instance.getPartMap().values()) {
			if (part.getFinish() > cmax) {
				cmax = part.getFinish();
			}
		}
		return cmax;
	}

	/**
	 * 
	 * @author: hba
	 * @description: 保存调度结果到本地，用于展示甘特图
	 * @param instance
	 * @throws IOException
	 * @date: 2019年12月19日
	 *
	 */
	public static void outputSolutionStep(Instance instance, Operation operation) throws IOException {
		// 用于保存每步分派结果，用于动态显示甘特图
		InstanceSolutionStep instanceSolutionStep = new InstanceSolutionStep();
		instanceSolutionStep.setMachineNum(instance.getMachineNum());
		instanceSolutionStep.setTaskUid(operation.getName());
		instanceSolutionStep.setTaskName(operation.getName());
		instanceSolutionStep.setStart(operation.getStart());
		instanceSolutionStep.setEnd(operation.getFinish());
		instanceSolutionStep.setColor(ColorUtil.colorToString(color.colorMap.get(operation.getPartID())));
		instanceSolutionStep.setDeviceIndex(operation.getMachineID());
		instanceSolutionStep.setCurrObjective(instance.getObjective().getObjectiveValue());
		fileHandle.writeInstanceSolutionStep(instance.getInstanceCategory(), instance.getIndex() + "",
				instance.getAltorighmName(), instanceSolutionStep);
	}

	/**
	 * 
	 * @author: hba
	 * @description: 保存调度结果到本地，用于展示甘特图
	 * @param instance
	 * @param operation
	 * @param isClear
	 *            是否清空以前的结果
	 * @throws IOException
	 * @date: 2020年1月4日
	 *
	 */
	public static void outputSolutionStep(Instance instance, Operation operation, boolean isClear) throws IOException {
		// 用于保存每步分派结果，用于动态显示甘特图
		InstanceSolutionStep instanceSolutionStep = new InstanceSolutionStep();
		instanceSolutionStep.setMachineNum(instance.getMachineNum());
		instanceSolutionStep.setTaskUid(operation.getName());
		instanceSolutionStep.setTaskName(operation.getName());
		instanceSolutionStep.setStart(operation.getStart());
		instanceSolutionStep.setEnd(operation.getFinish());
		instanceSolutionStep.setColor(ColorUtil.colorToString(color.colorMap.get(operation.getPartID())));
		instanceSolutionStep.setDeviceIndex(operation.getMachineID());
		instanceSolutionStep.setCurrObjective(instance.getObjective().getObjectiveValue());
		fileHandle.writeInstanceSolutionStep(instance.getInstanceCategory(), instance.getIndex() + "",
				instance.getAltorighmName(), instanceSolutionStep, isClear);
	}

	/**
	 * @author: hba
	 * @description:将计算结果输出到案例所在目录下
	 * @param instance
	 * @throws IOException
	 * @date: 2019年12月19日
	 *
	 */
	public static void outputInstanceSolution(Instance instance, String algorithmName) throws IOException {
		// TODO Auto-generated method stub
		InstanceSolution instanceSolution = new InstanceSolution();
		instanceSolution.setAlgorithmName(algorithmName);
		instanceSolution.setInstanceName(instance.getName());
		double[] objectives = new double[1];
		objectives[0] = instance.getObjective().getObjectiveValue();
		instanceSolution.setObjectives(objectives);
		fileHandle.writeInstanceSolution(instance.getInstanceCategory(), instance.getIndex() + "", instanceSolution);
	}
}
