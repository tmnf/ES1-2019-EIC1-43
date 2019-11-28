package Models;

public class Rule {

	private boolean AND;
	private String nomeDaRegra;
	private int metric1, metric2;

	public Rule(String nomeDaRegra, int metric1, int metric2, boolean AND) {
		this.nomeDaRegra = nomeDaRegra;
		this.metric1 = metric1;
		this.metric2 = metric2;
		this.AND = AND;
	}

	public boolean getAnd() {
		return AND;
	}

	public String getNomeDaRegra() {
		return nomeDaRegra;
	}

	public int getMetric1() {
		return metric1;
	}

	public int getMetric2() {
		return metric2;
	}

}
