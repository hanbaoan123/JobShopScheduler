package mm_scheduler.instanceScheduler.algorithm.ga.test;

import java.io.IOException;
import java.util.stream.IntStream;


import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import io.jenetics.Mutator;
import io.jenetics.Optimize;
import io.jenetics.PartialAlterer;
import io.jenetics.PartiallyMatchedCrossover;
import io.jenetics.PermutationChromosome;
import io.jenetics.SwapMutator;
import io.jenetics.TournamentSelector;
import io.jenetics.UniformCrossover;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;
import mm_scheduler.instanceScheduler.algorithm.ga.GAScheduleManager;
import mm_scheduler.instanceScheduler.instance.domain.basicdata.Instance;
import mm_scheduler.instanceScheduler.util.FileHandle;
import mm_scheduler.instanceScheduler.util.InstanceUtil;

/**
 * Full Ones-Counting example.
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz
 *         Wilhelmstötter</a>
 * @version 3.6
 * @since 3.5
 */
public class InstanceGAScheduleTest5 {
	static private GAScheduleManager gaScheduleManager = new GAScheduleManager();
	static FileHandle fileHandle = new FileHandle();
	static Instance instance = null;
	static InstanceGAScheduleTest5 a = new InstanceGAScheduleTest5();

	InstanceGAScheduleTest5() {
		try {
			instance = fileHandle.readFJSPInstance(
					"E:\\personal\\study\\FJSP案例\\TextData\\Monaldo\\Fjsp\\Job_Data\\Brandimarte_Data\\Text\\Mk02.fjs\\");
			instance.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	final static EvolutionStatistics<Integer, ?> statistics = EvolutionStatistics.ofNumber();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Genotype ENCODING = Genotype.of(
			(Chromosome) PermutationChromosome.ofInteger(instance.getOperationMap().size()),
			(Chromosome) IntegerChromosome.of(0, instance.getMachineMap().size() - 1,
					instance.getOperationMap().size()));

	private static double fitness(final Genotype gt) {
		int[][] p = new int[2][instance.getOperationMap().size()];
		PermutationChromosome opChro = (PermutationChromosome) gt.getChromosome(0);
		// for (int i = 0; i < opChro.length(); i++) {
		// p[0][i] = (Integer) opChro.getGene(i).getAllele();
		// }
		p[0] = IntStream.range(0, opChro.length()).map(i -> (Integer) opChro.getGene(i).getAllele()).toArray();
		IntegerChromosome machineChro = (IntegerChromosome) gt.getChromosome(1);
		p[1] = machineChro.toArray();
		return gaScheduleManager.schedulePriority(instance, p);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(final String[] args) throws Exception {
		Engine<IntegerGene, Double> engine = Engine.builder(InstanceGAScheduleTest5::fitness, ENCODING)
				.executor(Runnable::run).optimize(Optimize.MINIMUM).populationSize(50000)
				.selector(new TournamentSelector<>(3))
				.alterers(PartialAlterer.of(new SwapMutator<IntegerGene, Double>(0.05), 0),
						PartialAlterer.of(new Mutator<IntegerGene, Double>(0.05), 1),
						PartialAlterer.of(new PartiallyMatchedCrossover(0.95), 0),
						PartialAlterer.of(new UniformCrossover<IntegerGene, Double>(0.95), 1))
				.build();

		EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
		Genotype<IntegerGene> best = (Genotype) engine.stream()
				// The evolution will stop after maximal 250
				// generations.
				.limit(Limits.bySteadyFitness(5000))
				// Update the evaluation statistics after
				// each generation
				.peek(statistics)
				.peek(r -> System.out.println(r.getGeneration() + " : " + r.getBestPhenotype().getGenotype().get(1)))
				// Collect (reduce) the evolution stream to
				// its best phenotype.
				.collect(EvolutionResult.toBestGenotype());

		System.out.println(statistics);
		System.out.println("Found min cmax: "/* + best.getFitness() */);
		instance.setWirteDynamic(true);
		instance.setAltorighmName("GA-Two1");
		InstanceGAScheduleTest5.fitness(best);
		InstanceUtil.outputInstanceSolution(instance, "GA-Two1");
		int[] p0 = { 42, 45, 9, 39, 10, 50, 44, 46, 20, 6, 0, 17, 19, 1, 24, 27, 11, 57, 23, 40, 2, 13, 4, 5, 54, 18,
				37, 38, 35, 36, 26, 7, 12, 48, 15, 41, 34, 31, 21, 28, 55, 30, 22, 3, 33, 25, 14, 49, 51, 29, 53, 8, 16,
				52, 56, 43, 47, 32 };
		int[] p1 = { 4, 0, 0, 5, 4, 0, 0, 2, 1, 0, 0, 2, 0, 1, 0, 5, 4, 0, 1, 3, 0, 0, 0, 0, 5, 5, 1, 4, 3, 1, 0, 3, 1,
				0, 1, 0, 0, 4, 5, 4, 0, 1, 5, 1, 1, 3, 0, 0, 0, 1, 1, 4, 4, 5, 0, 1, 0, 0 };
		int[][] p = new int[2][instance.getOperationMap().size()];
		p[0] = p0;
		p[1] = p1;
		instance.setAltorighmName("GA-Two2");
		gaScheduleManager.schedulePriority(instance, p);
		InstanceUtil.outputInstanceSolution(instance, "GA-Two2");
	}
}
