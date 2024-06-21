package at.ac.fhcampuswien.codegarden.utils

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface SharedPrefManager {
    fun saveUserDetails(id: Int, token: String, expiresAt: String)
    fun fetchToken(): String?
    fun fetchUserId(): Int?
    fun saveCredentials(username: String, password: String)
    fun fetchUsername(): String?
    fun fetchTokenExpiration(): Date?
}

class SharedPrefManagerImpl(private val context: Context) : SharedPrefManager {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("CodeGardenPrefs", Context.MODE_PRIVATE)

    override fun saveUserDetails(id: Int, token: String, expiresAt: String) {
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.putInt("id", id)
        editor.putString("expiresAt", expiresAt)
        editor.apply()
    }

    override fun fetchToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    override fun fetchUserId(): Int {
        return sharedPreferences.getInt("id", -1)
    }

    override fun saveCredentials(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    override fun fetchUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    override fun fetchTokenExpiration(): Date? {

        val dateString =
            sharedPreferences.getString("expiresAt", null) // replace with your date string
        val format = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault())
        return dateString?.let { format.parse(it) }
    }


}