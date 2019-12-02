package Models;

import Enums.Test;

public class EmptyRule implements DefaultRule{

	public Test test;

	public EmptyRule(Test test) {
		this.test = test;
	}

	public Test getTest() {
		return test;
	}

}
