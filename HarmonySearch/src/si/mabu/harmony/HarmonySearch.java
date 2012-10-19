package si.mabu.harmony;

import org.um.feri.ears.algorithms.Algorithm;
import org.um.feri.ears.algorithms.AlgorithmInfo;
import org.um.feri.ears.algorithms.Author;
import org.um.feri.ears.problems.Individual;
import org.um.feri.ears.problems.StopCriteriaException;
import org.um.feri.ears.problems.Task;

public class HarmonySearch extends Algorithm {

	boolean debug = false;
	
	public HarmonySearch() {
		this.debug = false;
		ai = new AlgorithmInfo("", "", "HS", "Harmony Search");
		au = new Author("mabu", "N/A");
	}
	
	public HarmonySearch(boolean d) {
		super();
		setDebug(d);
	}
	
	@Override
	public Individual run(Task taskProblem) throws StopCriteriaException {
		Individual best = taskProblem.getRandomIndividual();
		
		return best;
	}

	@Override
	public void resetDefaultsBeforNewRun() {
		// TODO Auto-generated method stub

	}

}
