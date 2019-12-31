package mm_scheduler.instanceScheduler.rules;

import java.io.Serializable;
import java.util.Comparator;

import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;
import mm_scheduler.instanceScheduler.rules.basic.OperationRule;

/**
 * S-8规则，每单位剩余工作量松弛时间越小越优先（SL/WKR）
 * 
 * @author hba
 *
 */
public class OperationS8 extends OperationRule implements Comparator<Operation>, Serializable {
	public OperationS8() {
		super(30, "S8");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Operation operTask1, Operation operTask2) {
		if (operTask1.equals(operTask2))
			return 0;
		int i = -1;
		// 首道工序取工件的交货期
		double s1 = (operTask1.getPrepOp() == null ? operTask1.getPart().getDueDate()
				: (operTask1.getPart().getDueDate() - operTask1.getPrepOp().getFinish())
						- operTask1.getRemainWorkTime())
				/ operTask1.getRemainWorkTime();
		double s2 = (operTask2.getPrepOp() == null ? operTask2.getPart().getDueDate()
				: (operTask2.getPart().getDueDate() - operTask2.getPrepOp().getFinish())
						- operTask2.getRemainWorkTime())
				/ operTask2.getRemainWorkTime();
		try {
			if (s1 > s2)
				i = 1;
			else if (s1 == s2) {
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
