package com.example.templateapplication.data

import android.net.Uri
import android.util.Base64
import android.util.Log
import com.example.templateapplication.models.Doctor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import android.content.ContentResolver
import okhttp3.RequestBody.Companion.toRequestBody

import java.io.InputStream



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

    fun addDoctor(doctor : Doctor) : Doctor? {
        try {
            val jsonObject = JSONObject()
            jsonObject.put("id", doctor.id)
            jsonObject.put("name", doctor.name)
            jsonObject.put("gender", doctor.gender)
            jsonObject.put("specialization", doctor.specialization)
            jsonObject.put("image", doctor.image)
            jsonObject.put("infoOver", doctor.infoOver)
            jsonObject.put("infoOpleiding", doctor.infoOpleiding)
            jsonObject.put("infoPublicaties", doctor.infoPublicaties)
            // Add other doctor details as needed

            val requestBody = jsonObject.toString()
            val body = requestBody.toRequestBody("application/json".toMediaTypeOrNull())

            val request = Request.Builder()
                .url("http://192.168.100.101:5046/api/Doctor/AddDoctor")
                .post(body)
                .build()

            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                throw IOException("Failed to add doctor: ${response.code}")
            }

            val responseBody = response.body
            // Process the response body if needed

        } catch (e: Exception) {
            throw e
        }
        throw Exception("Failed to add doctor")
    }

/*
    fun addDoctor(doctor: Doctor): Doctor? {
        try {
            // Convert Base64 string image back to byte array
            Log.d("imageCheck", "Im here")
            val imageByteArray = Base64.decode(doctor.image, Base64.DEFAULT)
            Log.d("imageCheck2", "${doctor.image}")
            // result of doctor.image =  /picker/0/com.android.providers.media.photopicker/media/1000002235
            val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageByteArray)

            // Here, assuming the API expects the image as a form field 'image'
            val multipartBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image.jpg", requestBody)
                // Add other doctor details as needed
                .addFormDataPart("id", doctor.id.toString())
                .addFormDataPart("name", doctor.name)
                .addFormDataPart("gender", doctor.gender)
                .addFormDataPart("specialization", doctor.specialization)
                .addFormDataPart("infoOver", doctor.infoOver)
                .addFormDataPart("infoOpleiding", doctor.infoOpleiding)
                .addFormDataPart("infoPublicaties", doctor.infoPublicaties)
                .build()

            val request = Request.Builder()
                .url("http://192.168.100.101:5046/api/Doctor/AddDoctor")
                .post(multipartBody)
                .build()

            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                throw IOException("Failed to add doctor: ${response.code}")
            }

            val responseBody = response.body
            // Process the response body if needed
        } catch (e: Exception) {
            throw e
        }
        throw Exception("Failed to add doctor")
    }
*/



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
            val imageString = doctorObject.getString("image")
            Log.d("somebody", imageString)
            val imageByteArray = Base64.decode(imageString, Base64.DEFAULT)
            val imageBase64 = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
            Log.d("somebody3", imageBase64)
            doctors.add(Doctor(id, name, gender, specialization, infoOver, infoOpleiding, infoPublicaties, imageBase64))
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
        val imageString = doctorObject.getString("image")


        Log.d("somebody", imageString)

        val imageByteArray = Base64.decode(imageString, Base64.DEFAULT)
        val imageBase64 = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
        return Doctor(id, name, gender, specialization, infoOver, infoOpleiding, infoPublicaties, imageBase64)
    } catch (e: JSONException) {
        // Handle parsing exception
        e.printStackTrace()
    }
    return null
}