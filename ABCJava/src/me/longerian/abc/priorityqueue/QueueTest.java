package me.longerian.abc.priorityqueue;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class QueueTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PriorityBlockingQueue<RequestSlice> slices = new PriorityBlockingQueue<RequestSlice>(64, new Comparator<RequestSlice>() {

			@Override
			public int compare(RequestSlice lhs, RequestSlice rhs) {
				return lhs.getSequenceId() - rhs.getSequenceId();
			}
		});
		int seq = 0;
		RequestSlice slice0 = new RequestSlice(seq++, null);
		RequestSlice slice1 = new RequestSlice(seq++, null);
		RequestSlice slice2 = new RequestSlice(seq++, null);
		RequestSlice slice3 = new RequestSlice(seq++, null);
		RequestSlice slice4 = new RequestSlice(seq++, null);
		RequestSlice slice5 = new RequestSlice(seq++, null);
		RequestSlice slice6 = new RequestSlice(seq++, null);
		RequestSlice slice7 = new RequestSlice(seq++, null);
		RequestSlice slice8 = new RequestSlice(seq++, null);
		RequestSlice slice9 = new RequestSlice(seq++, null);
		
		slices.add(slice9);
		for(RequestSlice rs : slices) {
			System.out.print(rs.getSequenceId() + " ");
		}
		System.out.println(" ");
		slices.add(slice8);
		for(RequestSlice rs : slices) {
			System.out.print(rs.getSequenceId() + " ");
		}
		System.out.println(" ");
		slices.add(slice4);
		for(RequestSlice rs : slices) {
			System.out.print(rs.getSequenceId() + " ");
		}
		System.out.println(" ");
		slices.add(slice1);
		for(RequestSlice rs : slices) {
			System.out.print(rs.getSequenceId() + " ");
		}
		System.out.println(" ");
		slices.add(slice3);
		for(RequestSlice rs : slices) {
			System.out.print(rs.getSequenceId() + " ");
		}
		System.out.println(" ");
		slices.add(slice2);
		for(RequestSlice rs : slices) {
			System.out.print(rs.getSequenceId() + " ");
		}
		System.out.println(" ");
		slices.add(slice5);
		for(RequestSlice rs : slices) {
			System.out.print(rs.getSequenceId() + " ");
		}
		System.out.println(" ");
		slices.add(slice7);
		for(RequestSlice rs : slices) {
			System.out.print(rs.getSequenceId() + " ");
		}
		System.out.println(" ");
		slices.add(slice6);
		for(RequestSlice rs : slices) {
			System.out.print(rs.getSequenceId() + " ");
		}
		System.out.println(" ");
		slices.add(slice0);
		for(RequestSlice rs : slices) {
			System.out.print(rs.getSequenceId() + " ");
		}
		System.out.println(" ");
		RequestSlice header = null;
		int index = 0;
		while((header = slices.peek()) != null) {
			if(index == header.getSequenceId()) {
				header = slices.poll();
				System.out.print(header.getSequenceId() + " ");
				index++;
			}
		}
	}
	
	/**
	 * 
	 * 问题：有没有人在使用时，发现将添加在PriorityBlockingQueue的一系列元素打印出来，队列的元素其实并不是全部按优先级排序的，但是队列头的优先级肯定是最高的？
回复：PriorityBlockingQueue队列添加新元素时候不是将全部元素进行顺序排列，而是从某个指定位置开始将新元素与之比较，一直比到队列头，这样既能保证队列头一定是优先级最高的元素，又能减少排序带来的性能消耗（个人判断，仅供参考），可以查看PriorityBlockingQueue源码，添加元素有调用一个方法是PriorityQueue.siftUpUsingComparator（或siftUpComparable），这个方法里有个排序算法：
private void siftUpComparable(int k, E x) {
        Comparable<? super E> key = (Comparable<? super E>) x;
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            if (key.compareTo((E) e) >= 0)
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = key;
    }
不是全部排序。
但是这样会出现一个情况，取完队列头时候，后面的剩余的元素不是排序的，岂不是不符合要求了，继续查看源码，发现每取一个头元素时候，都会对剩余的元素做一次调整，这样就能保证每次队列头的元素都是优先级最高的元素，查看取元素时候调用的一个方法PriorityQueue.：

	 */

}
