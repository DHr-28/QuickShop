package androidrock.quickshoapapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Vigo Telecome on 26-12-2016.
 */
public class UserSessionManager {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String PREFER_NAME = "ShoapApp";

    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ID = "userid";




    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(int retailuserid,String email, String Password){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        editor.putInt(USER_ID, retailuserid);

        // Storing name in pref
        editor.putString(USER_EMAIL, email);

        // Storing email in pref
        editor.putString(USER_PASSWORD, Password);

        // commit changes
        editor.commit();
    }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getShoapCartSession(int cartindex){

        //Use hashmap to store user credentials
        HashMap<String, String> getshoapcartinfo = new HashMap<String, String>();


        // return user
        return getshoapcartinfo;
    }

    public boolean checkLogin(){

        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public int getloginretailerid(){

        return pref.getInt(USER_ID, 0);
    }

    /**
     * Clear session details
     * */
    public void clearAllSession(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

    }
}
