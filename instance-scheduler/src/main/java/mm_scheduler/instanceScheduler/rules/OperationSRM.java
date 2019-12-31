package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * SRM规则，选择除当前考虑工序外剩余加工时间最短的工件
 * 
 * @author hba
 *
 */
public class OperationSRM extends OperationRule implements Comparator<Operation>, Serializable {
	public OperationSRM() {
		super(17, "SRM");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		double srm1 = operTask1.getRemainWorkTime() - operTask1.getWorkTime();
		double srm2 = operTask2.getRemainWorkTime() - operTask2.getWorkTime();
		try {
			if (srm1 > srm2)
				i = 1;
			else if (srm1 == srm2) {
				if (operTask1.getRemainWorkTime() > operTask2.getRemainWorkTime())
					i = 1;
				else if (operTask1.getRemainWorkTime() == operTask2.getRemainWorkTime()) {
					// 交货期
					if (operTask1.getID() > operTask2.getID())
						i = 1;
				}
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
