package cc.icefire.market.api.parser;

import java.lang.reflect.Type;
import java.util.List;

import cc.icefire.market.api.response.CommentResponse;
import cc.icefire.market.model.Comment;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * 解析评论列表接口响应，获得一个Comment对象的列表。服务器返回的数据格式应当为：
 * [
    {
        "time": "2011-11-11 12:12:12",
        "content": "nice",
        "score": 5,
        "user": {
            "uid": "qwertyuiop",
            "name": "hello",
            "avatar_url": "http://www.xxx.com"
        }
    },
    {
        "time": "2011-11-11 12:12:12",
        "content": "nice",
        "score": 5,
        "user": {
            "uid": "qwertyuiop",
            "name": "hello",
            "avatar_url": "http://www.xxx.com"
        }
    },
    {
        "time": "2011-11-11 12:12:12",
        "content": "nice",
        "score": 5,
        "user": {
            "uid": "qwertyuiop",
            "name": "hello",
            "avatar_url": "http://www.xxx.com"
        }
    }
]
 */
public class CommentParser extends JsonParseHandler<CommentResponse> {

	private CommentResponse comment = new CommentResponse();
	
	@Override
	public CommentResponse getModel() {
		return comment;
	}

	@Override
	public void parse(String inputSource) {
		try {
			Type type = new TypeToken<List<Comment>>(){}.getType();
			List<Comment> comments = JsonUtil.fromJsonArray(inputSource, type);
			comment.setComments(comments);
			comment.setSuccess(true);
		} catch (JsonSyntaxException e) {
			comment.setSuccess(false);
			e.printStackTrace();
		} catch (JsonParseException e) {
			comment.setSuccess(false);
			e.printStackTrace();
		}
	}

}
