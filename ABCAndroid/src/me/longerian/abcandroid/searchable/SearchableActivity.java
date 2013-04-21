package me.longerian.abcandroid.searchable;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class SearchableActivity extends Activity implements OnClickListener {

	AppSearchRecentSuggestion suggestions = new AppSearchRecentSuggestion(this,
    		AppSuggestionProvider.AUTHORITY, AppSuggestionProvider.MODE);
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_search_layout);
        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(this);
        Button query = (Button) findViewById(R.id.query);
        query.setOnClickListener(this);
        ListView list = (ListView) findViewById(R.id.suggestion_list);
        
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.search:
	        suggestions.saveRecentQuery("QQSecurity", null);
			break;
		case R.id.query:
			Cursor cursor = suggestions.getQueryHistory();
			if(cursor != null) {
				if(cursor.moveToFirst()) {
					while(!cursor.isAfterLast()) {
						Log.d("longer", cursor.getString(cursor.getColumnIndexOrThrow("query")));
						cursor.moveToNext();
					}
				}
			}
			break;
		}
	}
	
}
