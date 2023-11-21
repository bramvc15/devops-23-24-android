package com.example.templateapplication.data

import android.util.Base64
import android.util.Log
import com.example.templateapplication.models.Doctor

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import okhttp3.RequestBody.Companion.toRequestBody


class DoctorService {
    private val client = OkHttpClient()

    val ip = "172.18.187.200"
    val ip2 = "192.168.100.101"
   // val ip3 =  "192.168.20.191"
    val ip3 = "172.18.150.40"

    fun getDoctorById(id : Int) : Doctor? {
        try {
            val request = Request.Builder()
                .url("http://${ip3}:5046/api/Doctor/GetDoctorById?id=${id}")
                .build()

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
            .url("http://${ip3}:5046/api/Doctor/GetDoctors")
            .build()

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
            jsonObject.put("image", (doctor.image))
            jsonObject.put("infoOver", doctor.infoOver)
            jsonObject.put("infoOpleiding", doctor.infoOpleiding)
            jsonObject.put("infoPublicaties", doctor.infoPublicaties)
            jsonObject.put("imageBase64", doctor.imageBase64)

            val requestBody = jsonObject.toString()
            val body = requestBody.toRequestBody("application/json".toMediaTypeOrNull())

            val request = Request.Builder()
                .url("http://${ip3}:5046/api/Doctor/AddDoctor")
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body
            val json = responseBody?.string()

            if (!response.isSuccessful) {
                throw IOException("Failed to add doctor: ${response.code}")
            }


            responseBody?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        throw Exception("Failed to add doctor")
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
            val imageString = doctorObject.getString("image")
            doctors.add(Doctor(id, name, gender, specialization, infoOver, infoOpleiding, infoPublicaties, imageString, null))
        }
        return doctors
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
            val imageByteArray: ByteArray = Base64.decode(imageString, Base64.DEFAULT)

            return Doctor(id, name, gender, specialization, infoOver, infoOpleiding, infoPublicaties, imageString, null)
        } catch (e: JSONException) {
            // Handle parsing exception
            e.printStackTrace()
        }
        return null
    }
}