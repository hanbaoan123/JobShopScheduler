package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * MINSEQ规则，准备时间越少越优先
 * 
 * @author hba
 *
 */
public class OperationMINSEQ extends OperationRule implements Comparator<Operation>, Serializable {
	public OperationMINSEQ() {
		super(7, "MINSEQ");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		try {
			if (operTask1.getPreTime() > operTask2.getPreTime())
				i = 1;
			else if (operTask1.getPreTime() - operTask2.getPreTime() == 0) {
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
