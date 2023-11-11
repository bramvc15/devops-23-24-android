package com.example.templateapplication.models

import android.net.Uri
import java.util.Base64

data class Doctor(

    val id: Int?,
    val name: String,
    val gender: String,
    val specialization: String,
    val infoOver: String,
    val infoOpleiding: String,
    val infoPublicaties: String,
    val image: String?

)