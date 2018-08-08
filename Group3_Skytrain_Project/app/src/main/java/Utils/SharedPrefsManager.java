package Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;

import static Utils.AppConstants.KEY_AVATAR;
import static Utils.AppConstants.KEY_GROUPID;
import static Utils.AppConstants.KEY_NOTICES;
import static Utils.AppConstants.KEY_READ_AUTH;
import static Utils.AppConstants.KEY_UID;
import static Utils.AppConstants.KEY_USERNAME;

public class SharedPrefsManager {
    private static String LOGCAT_TAG=SharedPrefsManager.class.getSimpleName();
    private static SharedPreferences sharedPrefs;
    private static SharedPreferences.Editor editor;
    private Context _context;
    private static SharedPrefsManager instance;
    private static final String SHARED_NAME="com.yamibo.bbs.Group3_Skytrain_Project";
    private static final String IS_KEY_LOGGEDIN="login_succeed";
    private String defAvatarURL="https://bbs.yamibo.com/uc_server/avatar.php?uid=330107&size=small";

    public static final SharedPrefsManager getInstance(){
        return new SharedPrefsManager();
    }
    private SharedPrefsManager(){
        //Empty constructor needed
    }
    // Constructor
    public SharedPrefsManager(Context context){
        this._context=context;

    }
    public static synchronized SharedPrefsManager getInstance(Context context){
        if(instance==null){
            instance=new SharedPrefsManager(context);
        }
        return instance;
    }
    public void saveFavoritedItems(boolean isLoggedIn,String notice,String groupId,
                                   String avatarUrl,String readAuth,String usrName,String uid){
        sharedPrefs =_context.getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);

        editor= sharedPrefs.edit();
        String[] notices={"newmypost","newpm","newprompt","newpush"};
        //Storing login (state) value as TRUE
        editor.putBoolean(IS_KEY_LOGGEDIN,true);
        String[] noticeArr={"newmypost","newpm","newprompt","newpush"};
        for(int i=0;i<noticeArr.length;i++){
            editor.putString(KEY_NOTICES, sharedPrefs.getString(noticeArr[i],notice));
        }
        editor.putString(KEY_AVATAR,avatarUrl);
        editor.putString(KEY_GROUPID,groupId);
        editor.putString(KEY_READ_AUTH,readAuth);
        editor.putString(KEY_USERNAME,usrName);
        editor.putString(KEY_UID,uid);

        editor.commit();
    }

    /**Get stored session data*/
    public HashMap<String,String> getUserDetails(){
        sharedPrefs =_context.getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);

        //Notice Array to hold child objects of notice
        String[] noticeArr={"newmypost","newpm","newprompt","newpush"};
        HashMap<String,String> userData=new HashMap<>();
        for(int i=0;i<noticeArr.length;i++){
            userData.put(KEY_NOTICES, sharedPrefs.getString(noticeArr[i],null));
        }
        userData.put(KEY_AVATAR, sharedPrefs.getString(KEY_AVATAR,defAvatarURL));
        userData.put(KEY_USERNAME, sharedPrefs.getString(KEY_USERNAME,null));
        userData.put(KEY_UID, sharedPrefs.getString(KEY_UID,null));
        userData.put(KEY_GROUPID, sharedPrefs.getString(KEY_GROUPID,null));
        userData.put(KEY_READ_AUTH, sharedPrefs.getString(KEY_READ_AUTH,null));

        return userData;
    }
}
