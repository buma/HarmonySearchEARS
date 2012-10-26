package si.mabu.harmony;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import org.um.feri.ears.algorithms.Algorithm;
import org.um.feri.ears.algorithms.AlgorithmInfo;
import org.um.feri.ears.algorithms.Author;
import org.um.feri.ears.algorithms.EnumAlgorithmParameters;
import org.um.feri.ears.algorithms.es.ES1p1sAlgorithm;
import org.um.feri.ears.benchmark.EnumBenchmarkInfoParameters;
import org.um.feri.ears.problems.Individual;
import org.um.feri.ears.problems.StopCriteriaException;
import org.um.feri.ears.problems.Task;

public class HarmonySearch extends Algorithm {

	boolean debug = false;
	
	int mem_size = 20;
	double consid_rate = 0.95; //r_accept
	double adjust_rate = 0.7; //r_pa
	double range = 0.05;
	int max_iter = 0;
	int factor = 1;
	Random random;
	
	public HarmonySearch() {
		this(20, 0.95, 0.7, 0.05);
	}
	
	public HarmonySearch(int mem_size, double consid_rate, double adjust_rate, double range) {
		this.mem_size = mem_size;
		this.consid_rate = consid_rate;
		this.adjust_rate = adjust_rate;
		this.range = range;
		this.debug = false;
		ai = new AlgorithmInfo("Harmony Search",
				"@book{brownlee2011clever,title={Clever Algorithms: Nature-Inspired Programming Recipes},  author={Brownlee, J.},  isbn={9781446785065},  url={http://books.google.si/books?id=SESWXQphCUkC},  year={2011},  publisher={Lulu.com}}",
				"HS", "Harmony Search based on the description by Yang, with refinement from Geem.");
		au = new Author("mabu", "N/A");
		resetDefaultsBeforNewRun();
		
		ai.addParameter(EnumAlgorithmParameters.POP_SIZE, "" + mem_size);
		ai.addParameter(EnumAlgorithmParameters.CONSIDER, "" + consid_rate);
		ai.addParameter(EnumAlgorithmParameters.PITCH_ADJUSTMENT, "" + adjust_rate);
		ai.addParameter(EnumAlgorithmParameters.PITCH_BANDWITH, "" + range);
	}
	
	public HarmonySearch(boolean d) {
		super();
		setDebug(d);
		random = new Random();
	}
	
	/**
	 * Initialize memory with mem_size * factor. Orders memory by fitness decreasing.
	 * @param taskProblem
	 * @return
	 *  first mem_size individuals.
	 * @throws StopCriteriaException
	 */
	private List<Individual> initialize_harmony_memory(Task taskProblem) {
		List<Individual> memory = new ArrayList<Individual>(mem_size * factor);
		try {
			for(int i = 0; i < (mem_size * factor); i++) {	
				memory.add(taskProblem.getRandomIndividual());
			}
		} catch (StopCriteriaException e) {
			Collections.sort(memory, new SortIndividualByFitness());
			return memory.subList(0, Math.min(memory.size(), mem_size));
		}
		Collections.sort(memory, new SortIndividualByFitness());
		return memory.subList(0, mem_size);
	}
	
	/**
	 * 
	 * @param min value
	 * @param max value
	 * @return
	 * random number between min <= x max
	 */
	private double random_double_in_constraints(double min, double max) {
		return min + (random.nextDouble() * (max - min));
	}
	
	/**
	 * Changes values of parameters
	 * @param memory
	 * @param taskProblem
	 * @return
	 */
	private double[] create_harmony(List<Individual> memory, Task taskProblem) {
		double[] ds = new double[this.factor];
		double[] intervalL = taskProblem.getIntervalLeft();
		double[] intervalR = taskProblem.getIntervalRight();
		for (int i = 0; i < this.factor; i++) {
			if (random.nextDouble() < this.consid_rate) {
				if (random.nextDouble() < this.adjust_rate) {
					int random_from_memory = random.nextInt(memory.size());
					double value = memory.get(random_from_memory).getNewX()[i];
					value = taskProblem.feasible(value + this.range*random_double_in_constraints(-1.0, 1.0), i);
					ds[i] = value;
				}

				//double value = memory.get(random_from_memory).getI().getX();
			}
			else
			{
				//DONE: has to be random number valid for this task
				ds[i] = random_double_in_constraints(intervalL[i], intervalR[i]);
			}
		}
		return ds;
	}
	
	@Override
	public Individual run(Task taskProblem) throws StopCriteriaException {
		max_iter = taskProblem.getMaxEvaluations();
		factor = taskProblem.getDimensions();
		
		random = new Random();
		List <Individual> memory = initialize_harmony_memory(taskProblem);
		Individual best = new Individual(memory.get(0));
		
		// we had a stopCriteria exception so we just return current best
		if (memory.size() != this.mem_size) {
			return best;
		}
		/*System.out.println("run");
		for(Individual mem : memory) {
			System.out.println(mem);
		}
		System.out.println(memory.size());*/
		while (taskProblem.isStopCriteria() == false) {
			Individual harm = taskProblem.eval(create_harmony(memory, taskProblem));
			if (taskProblem.isFirstBetter(harm, best)) {
				best = new Individual(harm);
			}
			memory.add(harm);
			Collections.sort(memory, new SortIndividualByFitness());
			memory.remove(memory.size()-1);
			if (debug) {
				String str = String.format(" > iteration=%d,  fitness=%.4f", taskProblem.getNumberOfEvaluations(), best.getEval());
				System.out.println(str);
			}
		}
		
		return best;
	}

	@Override
	public List<Algorithm> getAlgorithmParameterTest(
			EnumMap<EnumBenchmarkInfoParameters, String> parameters,
			int maxCombinations) {
		List<Algorithm> alternative = new ArrayList<Algorithm>();
        if (maxCombinations == 1) {
            alternative.add(this);
        } else {
        	double paramCombinations[][] =  { // {k, c}
            {20, 0.1, 0.7, 0.05 },
            {20, 0.1, 0.7, 0.1 },
            {20, 0.1, 0.9, 0.05 },
            {20, 0.7, 0.9, 0.05 },
            {20, 0.7, 0.85, 0.05 },
            {20, 0.95, 0.7, 0.05},
            {20, 0.95, 0.7, 0.1},
            {20, 0.95, 0.85, 0.1},
            };
        	/*double paramCombinations[][] = new double[121][4];
            int count = 0;
			paramCombinations[count][0] = 20;
			paramCombinations[count][1] = 0.95;
			paramCombinations[count][2] = 0.7;
			paramCombinations[count][3] = 0.05;
			count++;

            
            double consid = 0.7;
            double adjust = 0.0;
            double range = 0.05;
            
            while (consid < 1) {
            	adjust = 0.0;
            	while (adjust < 1) {
            		//range = 0.0;
            		//while (range < 0.2) {
            			//for (int mem_size=20; mem_size < 60; mem_size+=10) {
            				paramCombinations[count][0] = 30;
                			paramCombinations[count][1] = consid;
                			paramCombinations[count][2] = adjust;
                			paramCombinations[count][3] = range;
                			count++;
            			//}
            			//range +=0.05;
            			
            		//}
            		adjust +=0.05;
            		
            		
            	}
            	consid +=0.05;
            }
            System.err.println(count);*/
            int counter = 0;
            for (int i = 0; (i < paramCombinations.length) && (counter < maxCombinations); i++) {
                alternative.add(new HarmonySearch((int)paramCombinations[i][0], paramCombinations[i][1], paramCombinations[i][2], paramCombinations[i][3]));
                counter++;
            }
        }
        return alternative;
	}

	@Override
	public void resetDefaultsBeforNewRun() {
		// TODO Auto-generated method stub
		random = new Random();

	}

}
