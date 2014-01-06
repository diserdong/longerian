package me.longerian.abc;

public class Puzzle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int result = 1000;
		String normalOrder = "";
		StringBuilder reverseOrder = new StringBuilder();
		for(int i = 1000; i < 10000; i++) {//4 9
			result = i * 9;
			if(result < 10000) {
				normalOrder = String.valueOf(i);
				reverseOrder.append(String.valueOf(result));
				if(normalOrder.equals(reverseOrder.reverse().toString())) {
					System.out.println(normalOrder);
				}
				reverseOrder.delete(0, reverseOrder.length());
			}
		}
	}

}
