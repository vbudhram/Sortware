package utils;

public class Logging {
	public static final int DEBUG = 0;
	public static final int INFO = 1;
	public static final int ERROR = 2;
	public static int theMode = DEBUG;

	public static void debugException(Throwable t) {
		if (theMode <= DEBUG)
			t.printStackTrace();
	}

	public static void errorException(Throwable t) {
		if (theMode <= ERROR)
			t.printStackTrace();
	}

	public static void debug(String msg) {
		if (theMode <= DEBUG)
			System.out.println(msg);
	}

	public static void info(String msg) {
		if (theMode <= INFO)
			System.out.println(msg);
	}

	public static void error(String msg) {
		if (theMode <= ERROR)
			System.out.println(msg);
	}
}
