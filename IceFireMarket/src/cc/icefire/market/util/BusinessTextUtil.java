package cc.icefire.market.util;

import android.content.Context;
import cc.icefire.market.R;

public class BusinessTextUtil {

	public static String getDownloadCountTxt(Context context, int count) {
		if (count < 10000) {
			return context.getString(R.string.download_count_few);
		} else if (count >= 10000 && count < 100000000) {
			return context.getString(R.string.download_count_many,
					count / 10000);
		} else {
			return context.getString(R.string.download_count_massive,
					count / 100000000);
		}
	}

	public static String getSizeTxt(Context context, double size) {
		double oneByte = 1.0;
		double oneKiloByte = 1024.0;
		double oneMegaByte = 1024.0 * 1024.0;
		double oneGigaByte = 1024.0 * 1024.0 * 1024.0;
		if (Double.compare(size, oneMegaByte) < 0) {
			return context.getString(R.string.app_size_kilo, Math.round(size));
		} else if (Double.compare(size, oneMegaByte) >= 0
				&& Double.compare(size, 100 * oneMegaByte) < 0) {
			return context.getString(R.string.app_size_small_mega,
					size / oneMegaByte);
		} else if (Double.compare(size, 100 * oneMegaByte) >= 0
				&& Double.compare(size, oneGigaByte) < 0) {
			return context.getString(R.string.app_size_big_mega,
					Math.round(size / oneMegaByte));
		} else {
			return context.getString(R.string.app_size_giga,
					size / oneGigaByte);
		}
	}

}
