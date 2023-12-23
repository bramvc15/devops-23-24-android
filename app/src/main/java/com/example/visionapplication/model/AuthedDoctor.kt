package com.example.visionapplication.model

import android.util.Log
import com.auth0.android.jwt.JWT

/**
 * Authed doctor
 *
 * @property idToken
 * @constructor Create empty Authed doctor
 */
data class AuthedDoctor(val idToken: String? = null) {

    private val TAG = "AuthedDoctor"

    var id = ""
    var name = ""
    var email = ""
    var emailVerified = ""
    var picture = ""
    var updatedAt = ""
    var bearerToken = ""

    init {
        if (idToken != null) {
            try {
                // Attempt to decode the ID token.
                val jwt = JWT(idToken ?: "")

                // The ID token is a valid JWT,
                // so extract information about the AuthedDoctor from it.
                id = jwt.subject ?: ""
                name = jwt.getClaim("name").asString() ?: ""
                email = jwt.getClaim("email").asString() ?: ""
                emailVerified = jwt.getClaim("email_verified").asString() ?: ""
                picture = jwt.getClaim("picture").asString() ?: ""
                updatedAt = jwt.getClaim("updated_at").asString() ?: ""
                bearerToken = idToken
            } catch (error: com.auth0.android.jwt.DecodeException) {
                // The ID token is NOT a valid JWT, so log the error
                // and leave the AuthedDoctor properties as empty strings.
                Log.e(TAG, "Error occurred trying to decode JWT: ${error.toString()} ")
            }
        } else {
            // The AuthedDoctor object was instantiated with a null value,
            // which means the AuthedDoctor is being logged out.
            // The AuthedDoctor properties will be set to empty strings.
            Log.d(TAG, "AuthedDoctor is logged out - instantiating empty AuthedDoctor object.")
        }
    }

}