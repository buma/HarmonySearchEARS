package si.mabu.harmony;

import java.util.Comparator;

import org.um.feri.ears.problems.Individual;

public class SortIndividualByFitness implements Comparator<Individual> {

	@Override
	public int compare(Individual o1, Individual o2) {
		return Double.compare(o1.getEval(), o2.getEval());
	}

}
