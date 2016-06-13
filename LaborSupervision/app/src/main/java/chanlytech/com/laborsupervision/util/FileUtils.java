/**
 * Copyright © All right reserved by IZHUO.NET.
 */
package chanlytech.com.laborsupervision.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * @author Box
 * 
 * @since Jdk1.6 或 Jdk1.7
 * 
 * @version v1.0
 *
 * @date 2015年10月15日 上午11:51:36
 * 
 * @Description TODO
 * 
 * @最后修改日期 2015年10月15日 上午11:51:36
 * 
 * @修改人 Box
 */
public class FileUtils {
	
	public static String getPath(Context context, Uri uri) {
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = context.getContentResolver().query(uri, projection,
						null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
				// Eat it
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}
	
}
