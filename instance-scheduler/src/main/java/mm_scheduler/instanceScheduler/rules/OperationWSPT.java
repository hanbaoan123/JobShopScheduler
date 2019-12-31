package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * WSPT规则，加权最短加工时间（权重/工时越大）优先
 * 
 * @author hba
 *
 */
public class OperationWSPT extends OperationRule implements Comparator<Operation>, Serializable {

	public OperationWSPT() {
		super(33, "WSPT");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		try {
			if (operTask1.getPart().getWeight() / operTask1.getWorkTime() < operTask2.getPart().getWeight()
					/ operTask2.getWorkTime())
				i = 1;
			else if (operTask1.getPart().getWeight() / operTask1.getWorkTime()
					- operTask2.getPart().getWeight() / operTask2.getWorkTime() == 0) {
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
