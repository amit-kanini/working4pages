package com.example.samplepetapp

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface API {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 6b298527-19fa-4837-a8cd-ea56e3958b22"
    )

    @POST("users/me/email")
    suspend fun userLogin(@Body requestBody: RequestBody) : Response<ResponseBody>


    @Headers(

        "Authorization: Bearer 6b298527-19fa-4837-a8cd-ea56e3958b22"
    )
    @DELETE("users/me")
    suspend fun userDelete() : Response<ResponseBody>

}