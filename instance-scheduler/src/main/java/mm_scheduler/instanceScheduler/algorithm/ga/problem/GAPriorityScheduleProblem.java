package mm_scheduler.instanceScheduler.algorithm.ga.problem;

import java.util.function.Function;

import io.jenetics.EnumGene;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Problem;
import mm_scheduler.instanceScheduler.algorithm.ga.GAScheduleManager;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;

/**
 * 
 * @author: hba
 * @description:GA算法中的调度问题定义(优先级编码)
 * @date: 2019年12月12日
 *
 */
public class GAPriorityScheduleProblem implements Problem<int[], EnumGene<Integer>, Double> {
	private Instance instance;
	private GAScheduleManager gaScheduleManager = new GAScheduleManager();

	public GAPriorityScheduleProblem(Instance instance) {
		super();
		this.instance = instance;
		this.instance.init();
	}

	@Override
	/**
	 * 适应度函数评估，单步执行编码
	 */
	public Function<int[], Double> fitness() {
		// 计算适应度函数，实际上就是解码，将编码进行调度得到制造期
		return p -> {
			return gaScheduleManager.schedulePriority(instance, p);
		};
	}

	@Override
	/**
	 * 用规则编码进行编码
	 */
	public Codec<int[], EnumGene<Integer>> codec() {
		// TODO Auto-generated method stub
		return Codecs.ofPermutation((int) instance.getTotalOpNum());
	}

}
