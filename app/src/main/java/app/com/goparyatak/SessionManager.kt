package app.com.goparyatak

import android.content.Context
import android.content.Intent

import android.content.SharedPreferences




class SessionManager(context: Context?) {
    // Shared Preferences
    var pref: SharedPreferences? = null

    // Editor for Shared preferences
    var editor: SharedPreferences.Editor? = null

    // Context
    var _context: Context? = null

    // Shared pref mode
    var PRIVATE_MODE = 0

    // Sharedpref file name
    private val PREF_NAME = "GoParyatakPref"

    // All Shared Preferences Keys
    private val IS_LOGIN = "IsLoggedIn"

    // User name (make variable public to access from outside)
    val KEY_NAME = "name"

    // Email address (make variable public to access from outside)
    val KEY_EMAIL = "userId"

    // Constructor
    init {
        _context = context
        pref = _context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref?.edit()
    }

    /**
     * Create login session
     */
    fun createLoginSession(name: String?, email: Int?) { // Storing login value as TRUE
        editor?.putBoolean(IS_LOGIN, true)

        // Storing name in pref
        editor?.putString(KEY_NAME, name)

        // Storing email in pref
        editor?.putInt(KEY_EMAIL, email!!)


        // commit changes
        editor?.commit()
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    fun checkLogin() { // Check login status
        if (!isLoggedIn()) { // user is not logged in redirect him to Login Activity
            val i = Intent(_context, MainActivity::class.java) // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            // Add new Flag to start new Activity
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // Staring Login Activity
            _context?.startActivity(i)
        }
        else
        {
            val i = Intent(_context, DashBoardActivity::class.java) // Closing all the Activities
                i.putExtra("customer_id",pref?.getInt(KEY_EMAIL,0))
                i.putExtra("customer_name",pref?.getString(KEY_NAME,null))
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            // Add new Flag to start new Activity
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            // Staring Login Activity
            _context?.startActivity(i)
        }
    }


    /**
     * Get stored session data
     */
    fun getUserDetails(): HashMap<String, String?>? {
        val user = HashMap<String, String?>() // user name
        user[KEY_NAME] = pref?.getString(KEY_NAME, null)

        // user email id
        user[KEY_EMAIL] = pref?.getString(KEY_EMAIL, null)

        // return user
        return user
    }

    /**
     * Clear session details
     */
    fun logoutUser() { // Clearing all data from Shared Preferences
        editor?.clear()
        editor?.commit()

        // After logout redirect user to Loing Activity
        val i = Intent(_context, MainActivity::class.java) // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // Add new Flag to start new Activity
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        // Staring Login Activity
        _context?.startActivity(i)
    }

    /**
     * Quick check for login
     */ // Get Login State
    fun isLoggedIn(): Boolean {
        return pref?.getBoolean(IS_LOGIN, false) == true
    }

}