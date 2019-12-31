package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * SRPT规则，剩余工时越少越优先
 * 
 * @author hba
 *
 */
public class OperationSRPT extends OperationRule implements Comparator<Operation>, Serializable {

	public OperationSRPT() {
		super(5, "SRPT");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		try {
			if (operTask1.getRemainWorkTime() > operTask2.getRemainWorkTime())
				i = 1;
			else if (operTask1.getRemainWorkTime() - operTask2.getRemainWorkTime() == 0) {
				if (operTask1.getWorkTime() > operTask2.getWorkTime())
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
