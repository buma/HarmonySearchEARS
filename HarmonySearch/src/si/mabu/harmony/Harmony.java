package si.mabu.harmony;

import org.um.feri.ears.problems.Individual;

public class Harmony implements Comparable<Harmony> {
	
	Individual i;
	double fitness = 0;
	boolean calculateFitness = true;
	
	public Harmony(Individual i) {
		this.i = i;
	}

	public Individual getI() {
		return i;
	}

	public double getFitness() {
		if (calculateFitness) {
			fitness = i.getEval();
			calculateFitness = false;
		}
		return fitness;
	}

	@Override
	public String toString() {
		return "Harmony [fitness=" + fitness + ", calculateFitness="
				+ calculateFitness + "]";
	}

	@Override
	public int compareTo(Harmony o) {
		return Double.compare(this.getFitness(), o.getFitness());
	}
	
	

}
