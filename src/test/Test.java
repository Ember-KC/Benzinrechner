package test;

public class Test {

	private static Test instance = null;

	private Test() {

	}

	public static Test getInstance() {
		if (instance == null) {
			instance = new Test();
		}
		return instance;
	}
}
