package com.example.templateapplication.shared

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("DoctorAppPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val DOCTOR_ID_KEY = "doctor_id"
        private const val PASSWORD_KEY = "password"
    }

    var doctorId: String?
        get() = preferences.getString(DOCTOR_ID_KEY, null)
        set(value) = preferences.edit().putString(DOCTOR_ID_KEY, value).apply()

    var password: String?
        get() = preferences.getString(PASSWORD_KEY, null)
        set(value) = preferences.edit().putString(PASSWORD_KEY, value).apply()
}
