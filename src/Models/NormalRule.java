package Models;

import Enums.Test;

public class NormalRule extends DefaultRule {

	private static final long serialVersionUID = -5690832323754691580L;

	private boolean and;
	private String ruleName;
	private float metric1, metric2;

	public NormalRule(String ruleName, float metric1, float metric2, boolean and, Test test) {
		super(test);

		this.and = and;
		this.metric1 = metric1;
		this.metric2 = metric2;
		this.ruleName = ruleName;
	}

	public boolean getAnd() {
		return and;
	}

	public String getRuleName() {
		return ruleName;
	}

	public float getMetric1() {
		return metric1;
	}

	public float getMetric2() {
		return metric2;
	}

	@Override
	public String toString() {
		return ruleName;
	}

}
