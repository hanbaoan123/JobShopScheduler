package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;



/**
 * EDD规则，工件交货期越早越优先
 * 
 * @author hba
 *
 */
public class OperationODD implements Comparator<Operation>, Serializable {

	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		double d1 = operTask1.getPart().getDueDate() - operTask1.getPart().getTotalWorkTime();
		double d2 = operTask2.getPart().getDueDate() - operTask2.getPart().getTotalWorkTime();
		try {
			if (d1 > d2)
				i = 1;
			else if (d1 == d2) {
				if (operTask1.getPart().getDueDate() > operTask2.getPart().getDueDate())
					i = 1;
				else if (operTask1.getPart().getDueDate() - operTask2.getPart().getDueDate() == 0) {
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
