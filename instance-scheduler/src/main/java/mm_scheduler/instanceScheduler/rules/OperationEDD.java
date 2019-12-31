package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * EDD规则，工件交货期越早越优先
 * 
 * @author hba
 *
 */
public class OperationEDD extends OperationRule implements Comparator<Operation>, Serializable {

	public OperationEDD() {
		super(8, "EDD");
		// TODO Auto-generated constructor stub
	}
	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		try {
			if (operTask1.getPart().getDueDate() > operTask2.getPart().getDueDate())
				i = 1;
			else if (operTask1.getPart().getDueDate() - operTask2.getPart().getDueDate() == 0) {
				if (operTask1.getWorkTime() > operTask2.getWorkTime())
					i = 1;
				else if (operTask1.getWorkTime() == operTask2.getWorkTime()) {
					if (operTask1.getID() > operTask2.getID())
						i = 1;
				}
			}
		} catch (

		RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
