package com.neppplus.finalproject_python_userapp_202201.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.neppplus.finalproject_python_userapp_202201.utils.ContextUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ServerAPI {

    companion object {

//        private var BASE_URL = "http://3.35.185.121"
        private var BASE_URL = "http://api.copang.xyz"

        private var retrofit: Retrofit? = null

        fun getRetrofit(context: Context): Retrofit {

            if (retrofit == null) {

                val interceptor = Interceptor {

                    with(it) {

                        val newRequest = request().newBuilder()
                            .addHeader("X-Http-Token", ContextUtil.getToken(context))
                            .build()

                        proceed(newRequest)

                    }

                }

                val myClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter( Date::class.java, DateDeserializer() )  // Date형태로 실제 파싱 진행 클래스 추가
                    .create()


                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(myClient)
                    .build()
            }

            return retrofit!!

        }

    }

}