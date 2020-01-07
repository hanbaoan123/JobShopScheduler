package mm_scheduler.instanceScheduler.algorithm.ga.test;

import java.util.List;

/*
遗传算法调度
编码方案：
1、动作序列编码（整型1~规则数）
2、工件编码（1~工件总数）
3、工序编码（1~工序总数）
4、优先级编码（实数）
 */

import io.jenetics.IntegerGene;
import io.jenetics.Mutator;
import io.jenetics.Optimize;
import io.jenetics.Phenotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.SinglePointCrossover;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;
import mm_scheduler.instanceScheduler.algorithm.ga.problem.GARuleScheduleProblem;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.util.FileHandle;

/**
 * 
 * @author: hba
 * @description: 规则编码遗传算法测试
 * @date: 2019年12月19日
 *
 */
public class InstanceGAScheduleTest1 {
	final static EvolutionStatistics<Integer, ?> statistics = EvolutionStatistics.ofNumber();

	public static void main(final String[] args) throws Exception {
		FileHandle fileHandle = new FileHandle();
		List<Instance> instances = fileHandle.readSingleMachineInstance("0_40x1sample");
		for (Instance instance : instances) {
			GARuleScheduleProblem instanceSchedule = new GARuleScheduleProblem(instance);
			Engine<IntegerGene, Double> engine = Engine.builder(instanceSchedule).executor(Runnable::run)
					.optimize(Optimize.MINIMUM).populationSize(500).selector(new RouletteWheelSelector<>())
					.alterers(new Mutator<>(0.1), new SinglePointCrossover<>(0.7)).build();

			EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
			Phenotype<IntegerGene, Double> best = engine.stream()
					// Truncate the evolution stream after 25 "steady"
					// generations.
					.limit(Limits.bySteadyFitness(500))
					// The evolution will stop after maximal 250
					// generations.
					.limit(1000)
					// Update the evaluation statistics after
					// each generation
					.peek(statistics).peek(r -> System.out.println(r.getGeneration() + " : " + r.getBestPhenotype()))
					// Collect (reduce) the evolution stream to
					// its best phenotype.
					.collect(EvolutionResult.toBestPhenotype());
			System.out.println(statistics);
			System.out.println("Found min cmax: " + best.getFitness());
		}
	}
}
