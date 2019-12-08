package Models;

import java.io.Serializable;

import Enums.Test;

public class DefaultRule implements Serializable {

	private static final long serialVersionUID = 4807672119585434880L;

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
