package Enums;

public enum Test {

	PMD("PMD"), IPLASMA("iPlasma"), LONG_METHOD("is_long_method"), IS_FEATURE_ENVY("is_feature_envy");

	private String realName;

	private Test(String realName) {
		this.realName = realName;
	}

	public String getRealName() {
		return realName;
	}

	public String toString() {
		return realName;
	}
}
