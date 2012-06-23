package so.mp3.http;


public abstract class SougouResponse {

	private boolean networkException;

	public boolean isNetworkException() {
		return networkException;
	}

	public void setNetworkException(boolean networkException) {
		this.networkException = networkException;
	}

}
