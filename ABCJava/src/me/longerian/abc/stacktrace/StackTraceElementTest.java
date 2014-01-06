package me.longerian.abc.stacktrace;

public class StackTraceElementTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("line1: "
				+ new Throwable().getStackTrace()[0].getLineNumber());
		System.out.println("line2: " + getLineInfo());
		System.out.println("line3: " + getTraceInfo());

		StackTraceElement ste1 = null;
		StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
		int steArrayLength = steArray.length;
		String s = null;
		// output all related info of the existing stack traces
		if (steArrayLength == 0) {
			System.err.println("No Stack Trace.");
		} else {
			for (int i = 0; i < steArrayLength; i++) {
				System.out.println("Stack Trace-" + i);
				ste1 = steArray[i];
				s = ste1.getFileName() + ": Line " + ste1.getLineNumber();
				System.out.println(s);
			}
		}
	}

	public static String getTraceInfo() {
		StringBuffer sb = new StringBuffer();

		StackTraceElement[] stacks = new Throwable().getStackTrace();
		int stacksLen = stacks.length;
		sb.append("class: ").append(stacks[1].getClassName())
				.append("; method: ").append(stacks[1].getMethodName())
				.append("; number: ").append(stacks[1].getLineNumber());

		return sb.toString();
	}

	public static String getLineInfo() {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		return ste.getFileName() + ": Line " + ste.getLineNumber();
	}

}
