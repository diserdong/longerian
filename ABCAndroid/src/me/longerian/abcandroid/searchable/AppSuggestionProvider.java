package me.longerian.abcandroid.searchable;

import android.content.SearchRecentSuggestionsProvider;

public class AppSuggestionProvider extends SearchRecentSuggestionsProvider {

	public final static String AUTHORITY = "me.longerian.abcandroid.searchable.AppSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public AppSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
    
}
