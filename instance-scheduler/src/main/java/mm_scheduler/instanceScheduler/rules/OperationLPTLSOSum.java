package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * LPT+LSO规则，选择当前工序加工时间与后继工序加工时间最长的工件
 * 
 * @author hba
 *
 */
public class OperationLPTLSOSum extends OperationRule implements Comparator<Operation>, Serializable {
	public OperationLPTLSOSum() {
		super(22, "LPTLSOSum");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		double sum1 = operTask1.getWorkTime()
				+ (operTask1.getSuccOp() == null ? 0 : operTask1.getSuccOp().getWorkTime());
		double sum2 = operTask2.getWorkTime()
				+ (operTask2.getSuccOp() == null ? 0 : operTask2.getSuccOp().getWorkTime());
		try {
			if (sum1 < sum2)
				i = 1;
			else if (sum1 - sum2 == 0) {
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
