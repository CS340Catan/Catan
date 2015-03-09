package test;

public class TestRunner {

	public static void main(String[] args) {

		String[] testClasses = new String[] { "test.SerializerTest",
				"test.ClientModelTest", "test.ServerProxyTest",
				"test.PollerTest", "test.CanPlayCardTest",
				"test.MiscClientModelTest", "test.PlayingCommandTest", };
		org.junit.runner.JUnitCore.main(testClasses);
	}
}
