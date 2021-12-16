package com.example.samplepetapp

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface EaterInterface {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 2e250188-1450-45cb-bb26-a5d73d9ad305"
    )

    @GET("users/me/orders")
//    suspend fun getPets(): Response<PetList>
    suspend fun getMyorders(): OrderList
//    suspend fun getPets(): List<Pets>
//    suspend fun getPets(token: String): List<Pets>

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 2e250188-1450-45cb-bb26-a5d73d9ad305"
    )
    @GET("dishes")
    suspend fun getdishes(): Dishes

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 2e250188-1450-45cb-bb26-a5d73d9ad305"
    )
    @DELETE("users/me/orders/{id}")
    suspend fun deleteOrder(@Path("id") id: Int): Response<ResponseBody>


}

