package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;



/**
 * OPNDD规则，工序交货期越早越优先
 * 
 * @author hba
 *
 */
public class OperationOPNDD implements Comparator<Operation>, Serializable {

	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		try {
			if (operTask1.getDueDate() > operTask2.getDueDate()) {
				i = 1;
			} else if (operTask1.getDueDate() == operTask2.getDueDate()) {
				if (operTask1.getRemainWorkTime() > operTask2.getRemainWorkTime())
					i = 1;
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
