package Models;

import Enums.Test;

public class DefaultRule {

	protected Test test;

	public DefaultRule(Test test) {
		this.test = test;
	}

	public Test getTest() {
		return test;
	}

	public String toString() {
		return test.getRealName();
	}

}
