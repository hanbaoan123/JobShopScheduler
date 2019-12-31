package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * LPT/TWKR规则，选择工序加工时间与剩余加工时间比值最大的工件
 * 
 * @author hba
 *
 */
public class OperationLPTTWKRRatio extends OperationRule implements Comparator<Operation>, Serializable {
	public OperationLPTTWKRRatio() {
		super(12, "LPTTWKRRatio");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		double r1 = operTask1.getWorkTime() / operTask1.getRemainWorkTime();
		double r2 = operTask2.getWorkTime() / operTask2.getRemainWorkTime();
		try {
			if (r1 < r2)
				i = 1;
			else if (r1 - r2 == 0) {
				if (operTask1.getWorkTime() < operTask2.getWorkTime())
					i = 1;
				else if (operTask1.getWorkTime() == operTask2.getWorkTime()) {
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
