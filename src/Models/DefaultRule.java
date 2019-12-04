package Models;

import java.io.Serializable;

import Enums.Test;

public class DefaultRule implements Serializable{

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
