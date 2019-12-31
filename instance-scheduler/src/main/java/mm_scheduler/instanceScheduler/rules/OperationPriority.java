package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * 优先级规则，工件优先级越小越优先
 * 
 * @author hba
 *
 */
public class OperationPriority extends OperationRule implements Comparator<Operation>, Serializable {
	public OperationPriority() {
		super(0, "PRIORITY");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		try {
			// 优先级数值越小越优先
			if (operTask1.getOpPriority() > operTask2.getOpPriority())
				i = 1;
			// 优先级相同的情况用SPT
			else if (operTask1.getOpPriority() == operTask2.getOpPriority()) {
				if (operTask1.getWorkTime() > operTask2.getWorkTime())
					i = 1;
				else if (operTask1.getWorkTime() == operTask2.getWorkTime()) {
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
