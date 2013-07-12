package me.longerian.abc;

public class Split {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Event e = new Event();
		e.action = "launchScan";
		e.time = System.currentTimeMillis();
		e.value = 0;
		String s = e.toString();
		System.out.println(s);
		Event e2 = Event.parseFromString(s);
		System.out.println(e2.action);
		System.out.println(e2.time);
		System.out.println(e2.value);
	}

	static class Event {

		private String action;
		private long time;
		private int value;

		public String toString() {
			return action + "\t" + time + "\t" + value;
		}

		public static Event parseFromString(String str) {
			Event event = new Event();
			do {
				if (str == null) {
					break;
				}
				String[] fields = str.split("\\t");
				if (fields == null || fields.length != 3) {
					break;
				}
				event.action = fields[0];
				event.time = Long.valueOf(fields[1]);
				event.value = Integer.valueOf(fields[2]);
			} while (false);
			return event;
		}

	}

}
