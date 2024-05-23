package cdac.com.yogaapp.helper;

import static cdac.com.yogaapp.helper.StringValueHelper.SECRET_KEY;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefUtils {


    private static final String KEY_USER_LOGGED_IN = "isLogin";
    private static final String NOTIFICATION_COUNT = "notiCount";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "yogaPower";

    public PrefUtils(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void saveSecretKey(String value) {
        editor.putString(SECRET_KEY, value);
        editor.commit();
    }

    public String getSecretKey() {
        return pref.getString(SECRET_KEY, null);
    }

    public void setLanguage(String language) {
        editor.putString("language", language);
        editor.commit();

    }

    public String getLanguage() {
        return pref.getString("language", null);
    }


    public int getNotificationCount() {
        return pref.getInt(NOTIFICATION_COUNT, 0);
    }

    public void setNotificationCount(int count) {
        editor.putInt(NOTIFICATION_COUNT, count);
        editor.commit();
    }

    public int getVideoDownloadedCount() {
        return pref.getInt(NOTIFICATION_COUNT, 0);
    }

    public void setVideoDownloadedCount(int count) {
        editor.putInt(NOTIFICATION_COUNT, count);
        editor.commit();
    }


}
