package cc.icefire.market.model;

public class BasicDownloadInfo {

	private long mId;
	private String mUri;
	private String mDestination;
	private int mStatus;

	public BasicDownloadInfo(BasicDownloadInfo item) {
		super();
		this.mId = item.mId;
		this.mUri = item.mUri;
		this.mDestination = item.mDestination;
		this.mStatus = item.mStatus;
	}

	public BasicDownloadInfo() {
		super();
	}

	public BasicDownloadInfo(long mId, String mUri,
			String mDestination, int mStatus) {
		super();
		this.mId = mId;
		this.mUri = mUri;
		this.mDestination = mDestination;
		this.mStatus = mStatus;
	}

	public long getId() {
		return mId;
	}

	public void setId(long mId) {
		this.mId = mId;
	}

	public String getUri() {
		return mUri;
	}

	public void setUri(String mUri) {
		this.mUri = mUri;
	}

	public String getDestination() {
		return mDestination;
	}

	public void setDestination(String mDestination) {
		this.mDestination = mDestination;
	}

	public int getStatus() {
		return mStatus;
	}

	public void setStatus(int mStatus) {
		this.mStatus = mStatus;
	}

	@Override
	public String toString() {
		return "BasicDownloadInfo [mId=" + mId + ", mUri=" + mUri
				+ ", mDestination=" + mDestination + ", mStatus=" + mStatus
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mDestination == null) ? 0 : mDestination.hashCode());
		result = prime * result + (int) (mId ^ (mId >>> 32));
		result = prime * result + mStatus;
		result = prime * result + ((mUri == null) ? 0 : mUri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicDownloadInfo other = (BasicDownloadInfo) obj;
		if (mDestination == null) {
			if (other.mDestination != null)
				return false;
		} else if (!mDestination.equals(other.mDestination))
			return false;
		if (mId != other.mId)
			return false;
		if (mStatus != other.mStatus)
			return false;
		if (mUri == null) {
			if (other.mUri != null)
				return false;
		} else if (!mUri.equals(other.mUri))
			return false;
		return true;
	}

}
