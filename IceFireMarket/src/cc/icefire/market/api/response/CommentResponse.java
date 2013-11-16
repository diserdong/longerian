package cc.icefire.market.api.response;

import java.util.List;

import cc.icefire.market.model.Comment;

/**
 * 针对某一应用的评论数据
 */
public class CommentResponse extends BaseApiResponse {

	private List<Comment> comments;

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
