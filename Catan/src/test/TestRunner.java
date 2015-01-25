package test;

public class TestRunner {

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"test.SerializerTest"
		};
		org.junit.runner.JUnitCore.main(testClasses);
	}
}
