package com.example.samplepetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class changemail : AppCompatActivity() {
    var email = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changemail)
        val buttonLogin =findViewById<Button>(R.id.ConfirmChange)
        val inputEmail =findViewById<EditText>(R.id.changemailqw2)
        val ProgressBar=findViewById<ProgressBar>(R.id.progressBar)
        ProgressBar.setVisibility(View.INVISIBLE)
        buttonLogin .setOnClickListener{


            ProgressBar.setVisibility(View.VISIBLE)
////            viewOnProgress.setVisibility(View.VISIBLE)
//            IncButton.setVisibility(View.INVISIBLE)
//            DecButton.setVisibility(View.INVISIBLE)
//            CreateOrderBtn.setVisibility(View.INVISIBLE)


            val retrofit = Retrofit.Builder()
                .baseUrl("https://android-kanini-course.cloud/eaterapp/")
                .build()

            // Create Service
            val service = retrofit.create(API::class.java)

            // Create JSON using JSONObject
            val jsonObject = JSONObject()
            jsonObject.put("email", inputEmail)


            val jsonObjectString = jsonObject.toString()

            // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            println("Request body is : $requestBody" )
            println("Request body is : $jsonObjectString" )
            CoroutineScope(Dispatchers.IO).launch {
                // Do the POST request and get response
                val response = service.userLogin(requestBody)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                        // Convert raw JSON to pretty JSON using GSON library
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(
                            JsonParser.parseString(
                                response.body()
                                    ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                            )
                        )
                        ProgressBar.setVisibility(View.INVISIBLE)
                        Log.d("Pretty Printed JSON :", prettyJson)
                        Toast.makeText(this@changemail, "Created Successfully", Toast.LENGTH_LONG).show()

                        val loginIntent= Intent(this@changemail,MyOrderActivity::class.java)
                        loginIntent.putExtra("message1","This is Home Page")
                        loginIntent.putExtra("message2",prettyJson)
                        this@changemail.startActivity(loginIntent)


                    }
                    else{
                        ProgressBar.setVisibility(View.INVISIBLE)
////            viewOnProgress.setVisibility(View.VISIBLE)
//                        IncButton.setVisibility(View.VISIBLE)
//                        DecButton.setVisibility(View.VISIBLE)
//                        CreateOrderBtn.setVisibility(View.VISIBLE)
                        Log.e("RETROFIT_ERROR", response.code().toString())
                        Toast.makeText(this@changemail, "Invalid Credential", Toast.LENGTH_LONG).show()
                    }
                }
            }


        }


















    }



}