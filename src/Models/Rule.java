package Models;

public class Rule {

	private boolean AND;
	private String nomeDaRegra;
	private float metric1, metric2;
	public Object setName;

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

}
