package Utils;

import android.app.Application;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AppConstants extends Application{
    //Location constants
    Location location;
    private static double latitude,longtitude;
    public static double X_VALUE= latitude;
    public static double Y_VALUE= longtitude;
    public static int LOCATION_REFRESH_TIME=4000;
    public static int LOCATION_REFRESH_MINMUM_DISTANCE=10;
    public static int LOCATION_PERMISSION_REQUEST_CODE=1;

    public static int NOT_FOUND_RESPONSE_CODE = 404;

    //Shared Preferences Keys
    public static final String PREF_FILE_GLOBAL = "pref_file_global";
    public static final String KEY_USERNAME="member_username";
    public static final String KEY_COOKIEPRE="cookiepre";
    public static final String KEY_EMAIL="email";
    public static final String KEY_AVATAR="member_avatar";
    public static final String KEY_UID="member_uid";
    public static final String KEY_NOTICES="notice";
    public static final String KEY_READ_AUTH="readaccess";
    public static final String KEY_GROUPID="groupid";
    public static final String KEY_CREDITS="credits";
}
