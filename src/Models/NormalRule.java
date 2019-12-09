package Models;

import Enums.Test;

/**
 * NormalRule extends DefaultRule and represents another type of rules in which
 * the user sets his own metrics and operators.
 */
public class NormalRule extends DefaultRule {

	private static final long serialVersionUID = -5690832323754691580L;

	/**
	 * Indicates if this rule operator is OR or AND (OR = !and)
	 */
	private boolean and;

	/**
	 * Indicates given custom name to this rule
	 */
	private String ruleName;

	/**
	 * Indicates both metrics given by the user (Custom LOC and CYCLO or Custom ATFD
	 * and LAA)
	 */
	private float metric1, metric2;

	/**
	 * NormalRule's Constructor. Creates a new rule with custom metrics and
	 * operators
	 * 
	 * @param ruleName custom rule name provided by user
	 * @param metric1  custom first metric given by the user (LOC or ATFD, depending
	 *                 on test)
	 * @param metric2  custom first metric given by the user (CYCLO or LAA,
	 *                 depending on test)
	 * @param and      custom operator given by the user (or = !and, and = and)
	 * @param test     major type of rule that defines this one
	 */
	public NormalRule(String ruleName, float metric1, float metric2, boolean and, Test test) {
		super(test);
		this.and = and;
		this.metric1 = metric1;
		this.metric2 = metric2;
		this.ruleName = ruleName;
	}

	/**
	 * @return and operator (if false means or)
	 */
	public boolean getAnd() {
		return and;
	}

	/**
	 * @return rule name given by the user
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * @return first metric introduced (LOC or ATFD depending on type of test)
	 */
	public float getMetric1() {
		return metric1;
	}

	/**
	 * @return second metric introduced (CYCLO or LAA depending on type of test)
	 */
	public float getMetric2() {
		return metric2;
	}

	/**
	 * Override Object's toString() method
	 */
	@Override
	public String toString() {
		return ruleName;
	}

}
