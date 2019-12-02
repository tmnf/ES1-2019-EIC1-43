package Models;

import Enums.Test;

public class NormalRule extends DefaultRule {

	private boolean AND;
	private String nomeDaRegra;
	private float metric1, metric2;

	public NormalRule(String nomeDaRegra, float metric1, float metric2, boolean AND, Test test) {
		super(test);

		this.AND = AND;
		this.metric1 = metric1;
		this.metric2 = metric2;
		this.nomeDaRegra = nomeDaRegra;
	}

	public boolean getAnd() {
		return AND;
	}

	public String getNomeDaRegra() {
		return nomeDaRegra;
	}

	public float getMetric1() {
		return metric1;
	}

	public float getMetric2() {
		return metric2;
	}

	@Override
	public String toString() {
		return nomeDaRegra + " (" + test + ")";
	}

}
