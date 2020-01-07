package mm_scheduler.instanceScheduler.algorithm.ga.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.jenetics.EnumGene;
import io.jenetics.Optimize;
import io.jenetics.PartiallyMatchedCrossover;
import io.jenetics.Phenotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.SwapMutator;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import mm_scheduler.instanceScheduler.algorithm.ga.GAScheduleManager;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.instance.domain.solution.InstanceSolution;
import mm_scheduler.instanceScheduler.util.FileHandle;

/**
 * 
 * @author: hba
 * @description: 优先级编码遗传算法测试
 * @date: 2019年12月19日
 *
 */
public class InstanceGAScheduleTest3 {
	static private GAScheduleManager gaScheduleManager = new GAScheduleManager();
	static Instance instance;

	static double fitness(int[] priorityArray) {
		return gaScheduleManager.schedulePriority(instance, priorityArray);
	}

	// 置换编码
	public static Codec<int[], EnumGene<Integer>> codec() {
		// TODO Auto-generated method stub
		return Codecs.ofPermutation((int) instance.getTotalOpNum());
	}

	final static EvolutionStatistics<Integer, ?> statistics = EvolutionStatistics.ofNumber();

	public static void main(final String[] args) throws IOException {
		long start = System.currentTimeMillis();
		FileHandle fileHandle = new FileHandle();
		List<Instance> instances = fileHandle.readSingleMachineInstance("0_40x1sample");
		List<Double> results = new ArrayList<Double>();
		int runTimes = 5;
		for (int runTime = 1; runTime <= runTimes; runTime++) {
			for (Instance ins : instances) {
				ins.init();
				instance = ins;
				Engine<EnumGene<Integer>, Double> engine = Engine
						.builder(InstanceGAScheduleTest3::fitness, Codecs.ofPermutation((int) instance.getTotalOpNum()))
						.executor(Runnable::run).optimize(Optimize.MINIMUM).populationSize(500)
						.selector(new RouletteWheelSelector<>())
						.alterers(new SwapMutator<>(0.2), new PartiallyMatchedCrossover<>(0.9)).build();

				Phenotype<EnumGene<Integer>, Double> best = engine.stream()
						// The evolution will stop after maximal 250
						// generations.
						.limit(5000)
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
				InstanceSolution instanceSolution = new InstanceSolution();
				instanceSolution.setAlgorithmName("GA-Priority");
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
		System.out.println(
				"average objective: " + results.stream().mapToDouble(Double::doubleValue).average().orElse(0D));
		System.out.println("max objective: " + results.stream().mapToDouble(Double::doubleValue).max().orElse(0D));
		System.out.println("min objective: " + results.stream().mapToDouble(Double::doubleValue).min().orElse(0D));
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000.0 / 60.0 + "分钟");
	}
}
