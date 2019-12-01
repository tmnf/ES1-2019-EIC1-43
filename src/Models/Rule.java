package Models;

import GUI.Popup;

public class Rule {

	private boolean AND;
	private String nomeDaRegra;
	private float metric1, metric2;

	public Rule(String nomeDaRegra, float metric1, float metric2, boolean AND) {
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
		String ex = getNomeDaRegra();
		return ex;
	}

}
