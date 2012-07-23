package so.mp3.app.downloader;


public interface DownloadTaskListener {
    public void updateProcess(int taskId, int progress);			// 更新进度
    public void finishDownload(int taskId);			// 完成下载
    public void preDownload(int taskId);					// 准备下载
    public void errorDownload(int error);				// 下载错误
}
