package Enums;

/**
 * Test represents all different types of tests available.
 */

public enum Test {

	PMD("PMD"), IPLASMA("iPlasma"), LONG_METHOD("is_long_method"), IS_FEATURE_ENVY("is_feature_envy");

	/**
	 * The real name of each different test, to be shown in user graphic interface.
	 */
	private String realName;

	private Test(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the real name of a test.
	 */
	public String getRealName() {
		return realName;
	}

	@Override
	public String toString() {
		return realName;
	}
}
