package mm_scheduler.instanceScheduler.algorithm.ga.problem;

import java.util.function.Function;

import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.util.IntRange;
import mm_scheduler.instanceScheduler.algorithm.ga.GAScheduleManager;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;

/**
 * 
 * @author: hba
 * @description:GA算法中的调度问题定义(规则编码)
 * @date: 2019年12月12日
 *
 */
public class GARuleScheduleProblem implements Problem<int[], IntegerGene, Double> {
	private Instance instance;
	private GAScheduleManager gaScheduleManager = new GAScheduleManager();

	public GARuleScheduleProblem(Instance instance) {
		super();
		this.instance = instance;
		// 必须进行初始化
		this.instance.init();
	}

	@Override
	/**
	 * 适应度函数评估，单步执行编码
	 */
	public Function<int[], Double> fitness() {
		// 计算适应度函数，实际上就是解码，将编码进行调度得到调度目标
		return p -> {
			return gaScheduleManager.scheduleRules(instance, p);
		};
	}

	@Override
	/**
	 * 用规则编码进行编码
	 */
	public Codec<int[], IntegerGene> codec() {
		// TODO Auto-generated method stub
		// 注意这里的编码是指规则案例可选规则集合中的索引，在解码时需要通过该索引找到
		return Codec.of(
				Genotype.of(IntegerChromosome.of(IntRange.of(0, instance.getOperationRules().size() - 1),
						(int) instance.getTotalOpNum())),
				gt -> gt.getChromosome().as(IntegerChromosome.class).toArray());
	}

}
