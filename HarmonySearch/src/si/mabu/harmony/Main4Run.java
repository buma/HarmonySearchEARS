package si.mabu.harmony;


import org.um.feri.ears.problems.EnumStopCriteria;
import org.um.feri.ears.problems.StopCriteriaException;
import org.um.feri.ears.problems.Task;
import org.um.feri.ears.problems.unconstrained.ProblemSphere;

public class Main4Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Task t = new Task(EnumStopCriteria.EVALUATIONS, 3000, 0.0001,
				new ProblemSphere(5));
		HarmonySearch test = new HarmonySearch();
		try {
			System.out.println(test.run(t));
		} catch (StopCriteriaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
