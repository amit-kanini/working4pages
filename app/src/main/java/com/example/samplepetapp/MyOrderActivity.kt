package com.example.samplepetapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import kotlinx.coroutines.withContext

class MyOrderActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<MyOrderAdapter.MyViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = MyOrderAdapter()
        recyclerView.adapter = adapter
adapter.notifyDataSetChanged()
        val petsApplication = application as EaterApplication
        val petService = petsApplication.pets

            CoroutineScope(Dispatchers.IO).launch {
                val decodedpetsInterests = petService.getdishes()
                val decodedpets = petService.getMyorders()
                withContext(Dispatchers.Main)
                {println("decodedpets=$decodedpets")
                    println("decodedpetsIntrest=$decodedpetsInterests")
                    adapter.setData(decodedpets.dishOrders,decodedpetsInterests.dishes)
                }
            }
    }
}

