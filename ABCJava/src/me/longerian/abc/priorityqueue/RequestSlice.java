package me.longerian.abc.priorityqueue;

import java.util.Comparator;

public class RequestSlice implements Comparator<RequestSlice>{

	private final int sequenceId;
	private final byte[] data;
	
	public RequestSlice(int sequenceId, byte[] data) {
		this.sequenceId = sequenceId;
		if(data != null) {
			this.data = new byte[data.length];
			System.arraycopy(data, 0, this.data, 0, data.length);
		} else {
			this.data = null;
		}
	}
	
	public int getSequenceId() {
		return sequenceId;
	}

	public byte[] getData() {
		if(data != null) {
			byte[] dataCopy = new byte[data.length];
			System.arraycopy(data, 0, dataCopy, 0, data.length);
			return dataCopy;
		} else {
			return null;
		}
	}

	@Override
	public int compare(RequestSlice lhs, RequestSlice rhs) {
		return lhs.sequenceId - rhs.sequenceId;
	}

}
