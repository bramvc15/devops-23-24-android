package com.example.visionapplication.ui.views

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.visionapplication.R
import com.example.visionapplication.data.GlobalDoctor
import com.example.visionapplication.model.AuthedDoctor


/**
 * Main view model
 *
 * @constructor Create empty Main view model
 */
class MainViewModel: ViewModel() {
    var appJustLaunched by mutableStateOf(true)
    var userIsAuthenticated by mutableStateOf(false)

    var authedDoctor by mutableStateOf(AuthedDoctor())

    private val TAG = "MainViewModel"
    private lateinit var account: Auth0
    private lateinit var context: Context

    /**
     * Set context
     *
     * @param activityContext
     */
    fun setContext(activityContext: Context) {
        context = activityContext
        account = Auth0(
            context.getString(R.string.com_auth0_client_id),
            context.getString(R.string.com_auth0_domain)
        )
    }

    /**
     * Login
     *
     */
    fun login() {
        WebAuthProvider
            .login(account)
            .withScheme(context.getString(R.string.com_auth0_scheme))
            .start(context, object : Callback<Credentials, AuthenticationException> {

                override fun onFailure(error: AuthenticationException) {
                    // The user either pressed the “Cancel” button
                    // on the Universal Login screen or something
                    // unusual happened.
                    Log.e(TAG, "Error occurred in login(): $error")
                }

                override fun onSuccess(result: Credentials) {
                    // The user successfully logged in.
                    val idToken = result.idToken

                    Log.d(TAG, "ID token: $idToken")

                    authedDoctor = AuthedDoctor(idToken)
                    GlobalDoctor.authedDoctor = authedDoctor

                    userIsAuthenticated = true
                    appJustLaunched = false
                }

            })
    }

    /**
     * Logout
     *
     */
    fun logout() {
        WebAuthProvider
            .logout(account)
            .start(context, object : Callback<Void?, AuthenticationException> {

                override fun onFailure(error: AuthenticationException) {
                    Log.e(TAG, "Error occurred in logout(): $error")
                }

                override fun onSuccess(result: Void?) {
                    authedDoctor = AuthedDoctor()
                    GlobalDoctor.authedDoctor = authedDoctor
                    userIsAuthenticated = false
                }

            })
    }
}