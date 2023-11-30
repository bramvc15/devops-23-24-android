package com.example.templateapplication.data

/*
interface AppContainer {
    val doctorsRepository: DoctorsRepository
}


class DefaultAppContainer(): AppContainer{

    private val baseUrl = "http://192.168.100.101:5046/api/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : DoctorApiService by lazy {
        retrofit.create(DoctorApiService::class.java)
    }


    override val doctorsRepository: DoctorsRepository by lazy {
        ApiDoctorsRepository(retrofitService)
    }

}

 */