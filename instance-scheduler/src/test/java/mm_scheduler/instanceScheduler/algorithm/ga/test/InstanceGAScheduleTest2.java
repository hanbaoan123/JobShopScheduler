package mm_scheduler.instanceScheduler.algorithm.ga.test;

import java.io.IOException;
import java.util.List;

import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import io.jenetics.Mutator;
import io.jenetics.Optimize;
import io.jenetics.Phenotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.SinglePointCrossover;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;
import io.jenetics.util.IntRange;
import mm_scheduler.instanceScheduler.algorithm.ga.GAScheduleManager;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.solution.InstanceSolution;
import mm_scheduler.instanceScheduler.util.FileHandle;

/**
 * 
 * @author: hba
 * @description:规则编码遗传算法测试
 * @date: 2019年12月19日
 *
 */
public class InstanceGAScheduleTest2 {
	static private GAScheduleManager gaScheduleManager = new GAScheduleManager();
	static Instance instance;

	// 向量编码
	static double fitness(int[] ruleArray) {
		return gaScheduleManager.scheduleRules(instance, ruleArray);
	}

	public static Codec<int[], IntegerGene> codec() {
		// TODO Auto-generated method stub
		return Codec.of(
				Genotype.of(IntegerChromosome.of(IntRange.of(0, instance.getOperationRules().size() - 1),
						(int) instance.getTotalOpNum())),
				gt -> gt.getChromosome().as(IntegerChromosome.class).toArray());
	}

	final static EvolutionStatistics<Integer, ?> statistics = EvolutionStatistics.ofNumber();

	public static void main(final String[] args) throws Exception {
		FileHandle fileHandle = new FileHandle();
		List<Instance> instances = fileHandle.readSingleMachineInstance("0_40x1sample");
		for (Instance ins : instances) {
			ins.init();
			instance = ins;
			Engine<IntegerGene, Double> engine = Engine
					.builder(InstanceGAScheduleTest2::fitness, Codec.of(
							Genotype.of(IntegerChromosome.of(IntRange.of(0, instance.getOperationRules().size() - 1),
									(int) instance.getTotalOpNum())),
							gt -> gt.getChromosome().as(IntegerChromosome.class).toArray()))
					.executor(Runnable::run).optimize(Optimize.MINIMUM).populationSize(1000)
					.selector(new RouletteWheelSelector<>())
					.alterers(new Mutator<>(0.1), new SinglePointCrossover<>(0.7)).build();

			EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
			Phenotype<IntegerGene, Double> best = engine.stream()
					// The evolution will stop after maximal 250
					// generations.
					.limit(Limits.bySteadyFitness(500))
					// Update the evaluation statistics after
					// each generation
					.peek(statistics).peek(r -> System.out.println(r.getGeneration() + " : " + r.getBestPhenotype()))
					// Collect (reduce) the evolution stream to
					// its best phenotype.
					.collect(EvolutionResult.toBestPhenotype());
			// System.out.println(statistics);
			System.out.println("Found min cmax: " + best.getFitness());
			System.out.println("Found min cmax: " + best.getGenotype().getChromosome());
			InstanceSolution instanceSolution = new InstanceSolution();
			instanceSolution.setAlgorithmName("GA-Rule");
			instanceSolution.setInstanceName(instance.getName());
			double[] objectives = new double[1];
			objectives[0] = best.getFitness();
			instanceSolution.setObjectives(objectives);
			try {
				fileHandle.writeInstanceSolution(instance.getInstanceCategory(), instance.getIndex() + "",
						instanceSolution);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
