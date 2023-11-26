package com.example.templateapplication.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



interface ApiServiceNote {
    @GET("notes")
    suspend fun getNotes(): List<ApiNote>
}



class NoteApiService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5046/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiServiceNote::class.java)

    suspend fun getNotes(): List<ApiNote> {
        try {
            return apiService.getNotes()
        } catch (e: Exception) {
            throw Exception("Failed to fetch notes", e)
        }
    }
}


/*class DoctorApiService {
    // TODO beter een lib gebruiken: retrofit -> dit zien we volgende week
    private val client = OkHttpClient()
    fun getDoctors(): List<Doctor>{

        val request = Request.Builder()
            .url("http://10.0.2.2:5046/api/Doctor/GetDoctors")
            .build()


        try {

            client.newCall(request).execute().use { response ->
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
}*/