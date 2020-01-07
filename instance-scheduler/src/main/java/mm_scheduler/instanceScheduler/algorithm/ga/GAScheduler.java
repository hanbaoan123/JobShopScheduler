/**
 * hba
 */
package mm_scheduler.instanceScheduler.algorithm.ga;

import java.util.ArrayList;
import java.util.List;

import io.jenetics.IntegerGene;
import io.jenetics.Mutator;
import io.jenetics.Optimize;
import io.jenetics.PartiallyMatchedCrossover;
import io.jenetics.Phenotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.SinglePointCrossover;
import io.jenetics.SwapMutator;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Problem;
import mm_scheduler.instanceScheduler.algorithm.ga.problem.GAFJSPScheduleProblem;
import mm_scheduler.instanceScheduler.algorithm.ga.problem.GAPriorityScheduleProblem;
import mm_scheduler.instanceScheduler.algorithm.ga.problem.GARuleScheduleProblem;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.util.InstanceUtil;

/**
 * @author: hba
 * @description:
 * @date: 2019年12月18日
 *
 */
public class GAScheduler {
	/**
	 * 规则编码（RuleCode，RC）：使用启发式规则的编码作为基因
	 */
	public static final String RULE_CODE = "RULE_CODE";
	/**
	 * 优先级编码（PriorityCode，PC）：每道工序对应一个优先级数值
	 */
	public static final String PRIORITY_CODE = "PRIORITY_CODE";
	private Instance instance;
	private Problem problem;
	private String codeType;

	public GAScheduler(Instance instance, String code) {
		super();
		// TODO Auto-generated constructor stub
		this.instance = instance;
		this.codeType = code;
		if (code.equals(GAScheduler.RULE_CODE)) {
			problem = new GARuleScheduleProblem(instance);
			System.out.println("开始执行规则编码的遗传算法！");
		}
		if (code.equals(GAScheduler.PRIORITY_CODE)) {
			problem = new GAPriorityScheduleProblem(instance);
			System.out.println("开始执行优先级编码的遗传算法！");
		}
	}

	/**
	 * 
	 * @author: hba
	 * @description: 执行调度
	 * @param instance
	 * @param mode
	 *            模式
	 * @throws Exception
	 * @date: 2019年12月18日
	 *
	 */
	public void schedule() throws Exception {
		long start = System.currentTimeMillis();
		List<Double> results = new ArrayList<Double>();
		// 独立运行次数
		int runTimes = 1;
		try {
			for (int runTime = 1; runTime <= runTimes; runTime++) {
				Engine<IntegerGene, Double> engine = Engine.builder(this.problem).executor(Runnable::run)
						.optimize(Optimize.MINIMUM).populationSize(500).selector(new RouletteWheelSelector<>())
						.alterers(new Mutator<>(0.2), new SinglePointCrossover<>(0.9)).build();

				Phenotype<IntegerGene, Double> best = engine.stream()
						// The evolution will stop after maximal 250
						// generations.
						.limit(1000)
						// Update the evaluation statistics after
						// each generation
						.peek(r -> System.out.println(r.getGeneration() + " : " + r.getBestPhenotype()))
						// Collect (reduce) the evolution stream to
						// its best phenotype.
						.collect(EvolutionResult.toBestPhenotype());
				// System.out.println(statistics);
				results.add(best.getFitness());
				System.out.println("Found min objective: " + best.getFitness());
				System.out.println("Found min chromosome: " + best.getGenotype().getChromosome());
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		System.out.println("平均" + instance.getObjective().getObjectiveName() + ":"
				+ results.stream().mapToDouble(Double::doubleValue).average().orElse(0D));
		System.out.println("最大" + instance.getObjective().getObjectiveName() + ":"
				+ results.stream().mapToDouble(Double::doubleValue).max().orElse(0D));
		System.out.println("最小" + instance.getObjective().getObjectiveName() + ":"
				+ results.stream().mapToDouble(Double::doubleValue).min().orElse(0D));
		long end = System.currentTimeMillis();
		instance.getObjective().setObjectiveValue(results.stream().mapToDouble(Double::doubleValue).min().orElse(0D));
		InstanceUtil.outputInstanceSolution(instance, "GA-" + this.codeType);
		System.out.println("调度用时:" + (end - start) / 1000.0 / 60.0 + "分钟");
	}

}
