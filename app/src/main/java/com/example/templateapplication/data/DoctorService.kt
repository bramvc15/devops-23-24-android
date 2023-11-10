package com.example.templateapplication.data

import android.util.Log
import com.example.templateapplication.models.Doctor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DoctorService {
    private val client = OkHttpClient()

    fun getDoctorById(id : Int) : Doctor? {
        try {
            val request = Request.Builder()
                .url("http://192.168.100.101:5046/api/Doctor/GetDoctorById?id=${id}")
                .build()


            //client.newCall(request).execute().use { response ->
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                throw Exception("Failed to fetch doctors: ${response.code}")
            }

            val responseBody = response.body
            if (responseBody != null) {
                val json = responseBody.string()
                return parseDoctorFromJson(json)
            }
        } catch (e: Exception) {
            throw e
        }
        throw Exception("Failed to fetch doctors")
    }


    fun getDoctors(): List<Doctor>{
        try {

        val request = Request.Builder()
            //.url("http://10.0.2.2:5046/api/Doctor/GetDoctors")
            .url("http://192.168.100.101:5046/api/Doctor/GetDoctors")
            .build()



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
            val infoOver = doctorObject.getString("infoOver")
            val infoOpleiding = doctorObject.getString("infoOpleiding")
            val infoPublicaties = doctorObject.getString("infoPublicaties")
            val image = doctorObject.getString("image")
            doctors.add(Doctor(id, name, gender, specialization, infoOver, infoOpleiding, infoPublicaties, image))
        }
        return doctors
    }
}

private fun parseDoctorFromJson(json: String): Doctor? {
    try {
        val doctorObject = JSONObject(json)
        val id = doctorObject.getInt("id")
        val name = doctorObject.getString("name")
        val gender = doctorObject.getString("gender")
        val specialization = doctorObject.getString("specialization")
        val infoOver = doctorObject.getString("infoOver")
        val infoOpleiding = doctorObject.getString("infoOpleiding")
        val infoPublicaties = doctorObject.getString("infoPublicaties")
        val image = doctorObject.getString("image")
        return Doctor(id, name, gender, specialization, infoOver, infoOpleiding, infoPublicaties, image)
    } catch (e: JSONException) {
        // Handle parsing exception
        e.printStackTrace()
    }
    return null
}