/**
 * hba
 */
package mm_scheduler.instanceScheduler.instance.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.CandidateProcess;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Machine;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Part;
import mm_scheduler.instanceScheduler.util.FileHandle;

/**
 * @author: hba
 * @description: 根据http://people.brunel.ac.uk/~mastjjb/jeb/info.html中的案例数据创建调度案例
 * @date: 2019年12月16日
 *
 */
public class InstanceGenManager {
	/**
	 * @author hba
	 *
	 *         下午2:33:53
	 */
	private FileHandle fileHandle = new FileHandle();

	/**
	 * 
	 * @author: hba
	 * @description: 案例数据文件中包含一定的案例个数和作业数量
	 * @param path
	 *            文件路径
	 * @param instanceNums
	 *            案例个数(125)
	 * @param jobNums
	 *            作业数量(40,50,100)
	 * @throws Exception
	 * @date: 2019年12月16日
	 *
	 */
	public void genSingleMachineInstance(String path, int instanceNums, int jobNums) throws Exception {
		try {
			List<String> strings = fileHandle.readTxt(path);
			int rowsSum = 0;
			int instanceIndex = 0;
			List<String> arrayListAll = new ArrayList<>();
			Instance instance = new Instance();
			// 单机
			instance.setInstanceType(0);
			instance.setMachineNum(1);
			instance.setPartNum(jobNums);
			HashMap<String, Machine> machineMap = new HashMap<>();
			Machine machine = new Machine(1, "M" + 1);
			machineMap.put(machine.getName(), machine);
			instance.setMachineMap(machineMap);
			for (String string : strings) {
				String[] strArray = string.trim().split("\\s+");
				List<String> arrayList = new ArrayList<String>(Arrays.asList(strArray));
				arrayListAll.addAll(arrayList);
				rowsSum += strArray.length;
				// 工时
				if (rowsSum == jobNums) {
					int jobIndex = 1;
					int instanceTotalWorkTime = 0;
					for (String strEle : arrayListAll) {
						if (instance != null && instance.getPartMap().get("P" + jobIndex) != null) {
							Part part = instance.getPartMap().get("P" + jobIndex);
							int workTime = Integer.valueOf(strEle.trim());
							part.setTotalWorkTime(workTime);
							Operation operation = part.getOpList().get(0);
							operation.setPreTime(0);
							operation.setRunTime(workTime);
							CandidateProcess cProcess = instance.getCandidateProMap().get("P" + jobIndex + "_" + 1)
									.get(0);
							cProcess.setSetupTime(0);
							cProcess.setRunTime(workTime);
							cProcess.setDuration(workTime);
						} else {
							Part part = new Part();
							part.setID(jobIndex);
							part.setName("P" + jobIndex);
							// 标准案例 没有投放时间 默认为0
							part.setStart(0);
							part.setOperationNum(1);
							List<Operation> opList = new ArrayList<Operation>();
							Map<String, List<CandidateProcess>> caMap = new HashMap<String, List<CandidateProcess>>();
							part.setPlanQty(1);
							double totalworkTime = Integer.valueOf(strEle.trim());
							instanceTotalWorkTime += totalworkTime;
							// 创建工序任务
							for (int opIndex = 0; opIndex < 1; opIndex++) {
								Operation operation = new Operation();
								operation.setCandidateProNum(1);
								operation.setSeq(opIndex + 1);
								operation.setID(opIndex + 1);
								operation.setPartID(part.getID());
								operation.setName(part.getName() + "_" + operation.getID());
								operation.setPlanQty(1);
								double setupTime = 0;
								// 加工时间
								double runTime = totalworkTime;
								List<CandidateProcess> caList = new ArrayList<CandidateProcess>();
								Map<Integer, String> selectedMap = new HashMap<Integer, String>();
								// 对每一台可选的设备进行设置
								for (int j = 0; j < 1; j++) {
									CandidateProcess cp = new CandidateProcess();
									int machineIndex = 1;
									if (selectedMap.containsKey(machineIndex)) {
										j = j - 1;
										continue;
									} else {
										selectedMap.put(machineIndex, "");
									}
									cp.setMachineID(machineIndex);
									cp.setMachineName("M" + machineIndex);
									cp.setSetupTime(setupTime);
									cp.setRunTime(runTime);
									cp.setDuration((int) (setupTime + 1 * runTime));
									caList.add(cp);
									operation.setPreTime(setupTime);
									operation.setRunTime(runTime);
								}
								opList.add(operation);
								// 创建工序可选设备集合
								String opID = "P" + jobIndex + "_" + (opIndex + 1);
								caMap.put(opID, caList);
								instance.getCandidateProMap().putAll(caMap);
								instance.getOperationMap().put(operation.getName(), operation);
							}
							part.setOpList(opList);
							instance.getPartMap().put(part.getName(), part);
						}
						jobIndex++;
					}
					instance.setTotalWorkTime(instanceTotalWorkTime);
					instance.setTotalOpNum(jobNums);
					arrayListAll.clear();
				}
				// 权重
				if (rowsSum == 2 * jobNums) {
					int jobIndex = 1;
					for (String strEle : arrayListAll) {
						if (instance != null && instance.getPartMap().get("P" + jobIndex) != null) {
							Part part = instance.getPartMap().get("P" + jobIndex);
							part.setWeight(Double.valueOf(strEle.trim()));
						}
						jobIndex++;
					}
					arrayListAll.clear();
				}
				// 权重
				if (rowsSum == 3 * jobNums) {
					int jobIndex = 1;
					for (String strEle : arrayListAll) {
						if (instance != null && instance.getPartMap().get("P" + jobIndex) != null) {
							Part part = instance.getPartMap().get("P" + jobIndex);
							part.setDueDate(Double.valueOf(strEle.trim()));
						}
						jobIndex++;
					}
					// 写出案例
					instance.setIndex(instanceIndex + 1);
					fileHandle.writeInstance(instance.getInstanceCategory(), instanceIndex + 1, instance);
					rowsSum = 0;
					instanceIndex++;
					arrayListAll.clear();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
