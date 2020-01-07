/**
 * hba
 */
package mm_scheduler.instanceScheduler.algorithm.ga.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import io.jenetics.PermutationChromosome;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;
import io.jenetics.util.IntRange;
import mm_scheduler.instanceScheduler.algorithm.ga.GAScheduleManager;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.CandidateProcess;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Operation;

/**
 * @author: hba
 * @description: 使用遗传算法求解FJSP调度问题，默认
 * @date: 2019年12月23日
 *
 */
public class GAFJSPScheduleProblem implements Problem<int[][], IntegerGene, Double> {
	private Instance instance;
	private GAScheduleManager gaScheduleManager = new GAScheduleManager();

	public GAFJSPScheduleProblem(Instance instance) {
		super();
		this.instance = instance;
		this.instance.init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.jenetics.engine.Problem#fitness()
	 */
	@Override
	public Function<int[][], Double> fitness() {
		// TODO Auto-generated method stub
		return p -> {
			return gaScheduleManager.schedulePriority(instance, p);
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.jenetics.engine.Problem#codec()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Codec<int[][], IntegerGene> codec() {
		// TODO Auto-generated method stub
		Genotype ENCODING = Genotype.of(
				(Chromosome) PermutationChromosome.of(IntRange.of(0, instance.getOperationMap().size() - 1),
						instance.getOperationMap().size() - 1),
				(Chromosome) IntegerChromosome.of(0, instance.getMachineMap().size() - 1,
						instance.getOperationMap().size() - 1));
		return Codec.of(ENCODING, gt -> gt.stream().map(ch -> ch.stream().mapToInt(IntegerGene::intValue).toArray())
				.toArray(int[][]::new));
	}
	/**
	 * @author hbassddsfsdfs
	 *
	 *         上午10:38:26
	 */

}
