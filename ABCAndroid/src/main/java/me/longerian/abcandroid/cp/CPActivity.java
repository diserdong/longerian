package me.longerian.abcandroid.cp;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class CPActivity extends Activity {

	private TextView content;
	private EditText edit;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_cp);
	        content.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
					clipboard.setText(content.getText().toString());
				}
			});
	 }

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		content = (TextView) findViewById(R.id.content);
		edit = (EditText) findViewById(R.id.edit);
		content.setText("#include <stdio.h>" +
				"	#include <stdlib.h>" +
				"				void getmemory(char *p)" +
				"				{" +
				"				p=(char *) malloc(100);" +
				"				strcpy(p,\"hello world\");" +
						"				}" +
						"				int main( )" +
						"				{" +
						"				char *str=NULL;" +
						"				getmemory(str);" +
						"				printf(\"%/n\",str);" +
								"				free(str);" +
								"				return 0;" +
								"				} 答案：程序崩溃，getmemory中的malloc 不能返回动态内存， free（）对str操作很危险" +
								"			");
	}
	 
	 
}
