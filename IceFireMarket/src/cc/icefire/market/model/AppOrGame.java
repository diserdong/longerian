package cc.icefire.market.model;

/**
 * apk类型：1 应用， 0 游戏， 2 任意
 */
public enum AppOrGame {

	GAME, // == 0
	APP, // == 1
	ANY; // == 2
	
	public static AppOrGame fromInt(int value) {
		switch(value) {
        case 0:
            return GAME;
        case 1:
            return APP;
        case 2:
        	return ANY;
        }
		return null;
	}
	
}
