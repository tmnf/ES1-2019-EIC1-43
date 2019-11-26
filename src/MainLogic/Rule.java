package MainLogic;

public class Rule {

	private boolean AND;
	private boolean validRule;
	private String nomeDaRegra;
	private String LOC;
	private String CYCLO;

	public Rule(String nomeDaRegra, String LOC, String CYCLO, boolean AND) {
		this.nomeDaRegra=nomeDaRegra;
		this.LOC = LOC;
		this.CYCLO=CYCLO;
		this.AND=AND;
	}

	public boolean isAND() {
		return AND;
	}

	public void setAND(boolean aND) {
		AND = aND;
	}

	public String getNomeDaRegra() {
		return nomeDaRegra;
	}

	public void setNomeDaRegra(String nomeDaRegra) {
		this.nomeDaRegra = nomeDaRegra;
	}

	public String getLOC() {
		return LOC;
	}

	public void setLOC(String lOC) {
		LOC = lOC;
	}

	public String getCYCLO() {
		return CYCLO;
	}

	public void setCYCLO(String cYCLO) {
		CYCLO = cYCLO;
	}

	public boolean isValidRule() {
		return validRule;
	}
}
