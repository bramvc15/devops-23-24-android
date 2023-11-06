package com.example.templateapplication.data

import android.util.Log
import com.example.templateapplication.models.Doctor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray

class DoctorService {
    private val client = OkHttpClient()

    fun getDoctors(): List<Doctor>{
        Log.d("jdfkgljsdklgjsdkl", "gjskldfjgklsdf")
        val request = Request.Builder()
            .url("http://10.0.2.2:5046/api/Doctor/GetDoctors")
            .build()
        Log.d("jdfkgljsdklgjsdkl", "gjskldfjgklsdf")

        try {

            //client.newCall(request).execute().use { response ->
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                throw Exception("Failed to fetch doctors: ${response.code}")
            }

            val responseBody = response.body
            if (responseBody != null) {
                val json = responseBody.string()
                return parseDoctorListFromJson(json)
            }
        }catch (e:Exception){
            throw e
        }
        throw Exception("Failed to fetch doctors")

    }

    private fun parseDoctorListFromJson(json: String): List<Doctor> {
        val doctors = mutableListOf<Doctor>()
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            val doctorObject = jsonArray.getJSONObject(i)
            val id = doctorObject.getInt("id")
            val name = doctorObject.getString("name")
            val gender = doctorObject.getString("gender")
            val specialization = doctorObject.getString("specialization")
            val infoText = doctorObject.getString("infoText")
            doctors.add(Doctor(id, name, gender, specialization, infoText))
        }
        return doctors
    }
}
