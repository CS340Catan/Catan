package test;

public class TestRunner {

	public static void main(String[] args) {

		String[] testClasses = new String[] { "test.SerializerTest",
				"test.AndrewsTesties", "test.ServerProxyTest",
				"test.PollerTest", "test.CanPlayCardTest",
				"test.MiscClientModelTest", "test.DevCardTest", "test.MockServer"};
		org.junit.runner.JUnitCore.main(testClasses);
	}
}
