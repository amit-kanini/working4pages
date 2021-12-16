package com.example.samplepetapp

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EaterApplication: Application() {

    public lateinit var pets: EaterInterface

    override fun onCreate() {
        super.onCreate()
        pets = initPetService()
    }

    private fun initPetService(): EaterInterface
    {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/eaterapp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(EaterInterface::class.java)
    }
}