package si.mabu.harmony;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.um.feri.ears.algorithms.Algorithm;
import org.um.feri.ears.algorithms.AlgorithmInfo;
import org.um.feri.ears.algorithms.Author;
import org.um.feri.ears.problems.Individual;
import org.um.feri.ears.problems.StopCriteriaException;
import org.um.feri.ears.problems.Task;

public class HarmonySearch extends Algorithm {

	boolean debug = false;
	
	int mem_size = 20;
	double consid_rate = 0.95;
	double adjust_rate = 0.7;
	double range = 0.05;
	int max_iter = 0;
	int factor = 1;
	
	public HarmonySearch() {
		this.debug = false;
		ai = new AlgorithmInfo("", "", "HS", "Harmony Search");
		au = new Author("mabu", "N/A");
	}
	
	public HarmonySearch(boolean d) {
		super();
		setDebug(d);
	}
	
	/**
	 * @param taskProblem
	 * @return
	 * Initialize memory with mem_size * factor. Then returns first mem_size individuals.
	 * @throws StopCriteriaException
	 */
	private List<Individual> initialize_harmony_memory(Task taskProblem) throws StopCriteriaException {
		List<Individual> memory = new ArrayList<Individual>(mem_size * factor);
		for(int i = 0; i < (mem_size * factor); i++) {
			memory.add(taskProblem.getRandomIndividual());
		}
		Collections.sort(memory, new SortIndividualByFitness());
		return memory.subList(0, mem_size);
	}
	
	@Override
	public Individual run(Task taskProblem) throws StopCriteriaException {
		max_iter = taskProblem.getMaxEvaluations();
		factor = taskProblem.getDimensions();
		Individual best = taskProblem.getRandomIndividual();
		List <Individual> memory = initialize_harmony_memory(taskProblem);
		System.out.println("run");
		for(Individual mem : memory) {
			System.out.println(mem);
		}
		System.out.println(memory.size());
		
		return best;
	}

	@Override
	public void resetDefaultsBeforNewRun() {
		// TODO Auto-generated method stub

	}

}
