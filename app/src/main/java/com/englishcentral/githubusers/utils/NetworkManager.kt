package com.englishcentral.githubusers.utils

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.englishcentral.githubusers.dataclasses.GithubUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.ArrayList
import java.util.HashMap

interface NetworkCallbacks {
    fun onSuccess(response: String)
    fun onFail(failMessage: String)

}

class NetworkManager() {
    companion object {
        private fun connectToServerWithBodyAndHeaders(
            context: Context,
            action: String,
            contentType: String?,
            parameters: JSONObject?,
            volleyConnectionCallBack: NetworkCallbacks
            ,
            connectionMethod: Int,
            body: String?,
            bearerAuth: Boolean = true

        ) {

            var queue = Volley.newRequestQueue(context)

            val jsonObjectRequest = object :
                StringRequest(connectionMethod, action, Response.Listener<String> { response ->
                    Log.d("detailedResponse", response.toString())
                    volleyConnectionCallBack.onSuccess(response)
                    queue.stop()
                    queue = null

                }, Response.ErrorListener { error ->
                    volleyConnectionCallBack.onFail(
                        error.localizedMessage ?: "حدث خطأ ما, يرجى المحاولة فيما بعد"
                    )

//                val errorString = error.message
                    Log.d("volleyDetailedError", error.toString())
//                val responseBody = String(error.networkResponse.data, Charset.defaultCharset())
//                val data = JSONObject(responseBody)
//                val message = data.optString("msg")
//
//                val ss = data.getJSONObject("error")
//                val code = ss.getInt("code")
//
//
//                if (errorString != null) {
//                    volleyConnectionCallBack.onFail(errorString)
//                }
                    queue.stop()
                    queue = null

//                    if (bearerAuth) {
//                        networkManagerActions.needsAuth()
//                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    if (contentType != null) {
                        headers["Content-Type"] = contentType
                    }
                    return headers
                }

                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    return if (parameters == null) {
                        params
                    } else {
                        val keys = parameters.keys()
                        for (key in keys) {
                            params[key] = parameters.getString(key)
                        }
                        params
                    }
                }

                override fun getBody(): ByteArray {
                    return try {
                        body?.toByteArray(charset("utf-8")) ?: super.getBody()
                    } catch (uee: UnsupportedEncodingException) {
                        super.getBody()
                    }
                }

                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
            queue.add(jsonObjectRequest)
        }


        fun getAllProducts(context: Context, index: Int,callback: (ArrayList<GithubUser>) -> Unit) {
            val url = GlobalKeys.baseURL + "?since=$index"
            val volleyConnectionCallBack = object : NetworkCallbacks {
                override fun onSuccess(response: String) {
                    Log.d("success", response)
                    val arrayType = object : TypeToken<ArrayList<GithubUser>>() {}.type
                    val products: ArrayList<GithubUser> =
                        Gson().fromJson(response, arrayType)
                    callback(products)
                }

                override fun onFail(failMessage: String) {
                    Log.d("error", failMessage)
                }

            }


            connectToServerWithBodyAndHeaders(
                context,
                url,
                "application/json",
                null,
                volleyConnectionCallBack,
                Request.Method.GET,
                null,
                false
            )
        }
    }
}