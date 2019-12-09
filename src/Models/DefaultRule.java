package Models;

import java.io.Serializable;

import Enums.Test;

/**
 * DefaultRule represents the default type of rules, in which metrics and
 * operators are not added.
 */
public class DefaultRule implements Serializable {

	private static final long serialVersionUID = 4807672119585434880L;

	/**
	 * Indicates the rule's type of test
	 */
	protected Test test;

	/**
	 * DefaulRule's Construct. Creates a new default rule.
	 * 
	 * @param test defines the type o default rule being created
	 */
	public DefaultRule(Test test) {
		this.test = test;
	}

	/**
	 * @return default rule's type of test
	 */
	public Test getTest() {
		return test;
	}

	/**
	 * Override of Object's method toString
	 */
	public String toString() {
		return test.getRealName();
	}

}
