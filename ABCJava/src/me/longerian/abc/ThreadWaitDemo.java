package me.longerian.abc;

public class ThreadWaitDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("main: " + Thread.currentThread().getName());
		CycleThread ct = new CycleThread();
		ct.start();
		long start = System.currentTimeMillis();
		ct.waitResponse(40 * 1000);
		long end = System.currentTimeMillis();
		System.out.println("wait response for " + (end - start) / 1000);
		System.out.println("can you reach here?");
	}

	public static class CycleThread extends Thread {

		private int count = 0;
		private int responseCode = -1;

		@Override
		public void run() {
			System.out.println("start run: " + Thread.currentThread().getName());
			long start = System.currentTimeMillis();
			while ((count++) < 30) {
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				responseCode = count;
			}
			System.out.println("finish run: " + Thread.currentThread().getName());
			long end = System.currentTimeMillis();
			System.out.println("run for " + (end - start) / 1000);
			synchronized(this) {
				this.notify();
			}
		}

		public void stopIt() {
			count = 30;
		}

		public int waitResponse(int ms) {
			System.out.println("waitResponse: " + Thread.currentThread().getName());
			if (ms <= 0)
				return -100;
			if (count < 30) {
				synchronized (this) {
					try {
						this.wait(ms);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			return responseCode;
		}

	}

}
