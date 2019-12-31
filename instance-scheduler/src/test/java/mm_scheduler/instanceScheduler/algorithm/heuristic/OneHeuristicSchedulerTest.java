/**
 * hba
 */
package mm_scheduler.instanceScheduler.algorithm.heuristic;

import java.util.List;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.rules.*;
import mm_scheduler.instanceScheduler.util.FileHandle;
import mm_scheduler.instanceScheduler.util.InstanceUtil;

/**
 * @author: hba
 * @description: 简单规则调度器测试
 * @date: 2019年12月18日
 *
 */
public class OneHeuristicSchedulerTest {

	/**
	 * @author: hba
	 * @description:
	 * @param args
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FileHandle fileHandle = new FileHandle();
		List<Instance> instances = fileHandle.readSingleMachineInstance("0_40x1sample");
		// 一次执行运行一个启发式规则
		HeuristicScheduler heuristicScheduler = new HeuristicScheduler(new OperationEDD());
		for (Instance instance : instances) {
			instance.setWirteDynamic(true);
			heuristicScheduler.schedule(instance, 0);
			InstanceUtil.outputInstanceSolution(instance, heuristicScheduler.getOperationRule().getRuleName());
		}
	}
}
