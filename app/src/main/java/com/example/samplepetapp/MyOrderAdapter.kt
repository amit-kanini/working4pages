package com.example.samplepetapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MyOrderAdapter: RecyclerView.Adapter<MyOrderAdapter.MyViewHolder>() {
    var newpetInterests : MutableList<Dish> = mutableListOf<Dish>()
    var newpets :MutableList<Orders> = mutableListOf<Orders>()

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newpets[position]

//        val imageview= holder.itemView.findViewById<ImageView>(R.id.pet_image)
//        Picasso.get().load(currentItem.url).into(imageview)

        for(item in newpetInterests) {

            if(item.id==currentItem.dishId) {
                holder.itemView.findViewById<TextView>(R.id.pet_name).text= item.name
                holder.itemView.findViewById<TextView>(R.id.getPrice).text=item.price.toString()
                holder.itemView.findViewById<TextView>(R.id.pet_age).text= item.type
                val imageview= holder.itemView.findViewById<ImageView>(R.id.pet_image)
                   Picasso.get().load(item.url).into(imageview)

                val button=holder.itemView.findViewById<Button>(R.id.cancelOrder)
                button.setOnClickListener{

                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://android-kanini-course.cloud/eaterapp/")
                        .build()

                    // Create Service
                    val service = retrofit.create(EaterInterface::class.java)
                    CoroutineScope(Dispatchers.IO).launch {
                        // Do the POST request and get response
                        val response = service.deleteOrder(currentItem.orderId)

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                newpets.removeAt(position)
                                notifyDataSetChanged()
                                // Convert raw JSON to pretty JSON using GSON library
//                                val gson = GsonBuilder().setPrettyPrinting().create()
//                                val prettyJson = gson.toJson(
//                                    JsonParser.parseString(
//                                        response.body()
//                                            ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
//                                    )
//                                )
                                //   ProgressBar.setVisibility(View.INVISIBLE)
                               // Log.d("Pretty Printed JSON :", prettyJson)
                               // Toast.makeText(this@RecyclerAdapter, "Created Successfully", Toast.LENGTH_LONG).show()
                               // val loginIntent= Intent(this@RecyclerAdapter,MainActivity::class.java)
                               //loginIntent.putExtra("message1","This is Home Page")
                               // loginIntent.putExtra("message2",prettyJson)
                               // this@RecyclerAdapter.startActivity(loginIntent)
//                                val newScreenIntent= Intent(this@RecyclerAdapter,MainActivity::class.java)
//                                startActivity(newScreenIntent)
//

                            }
                            else{
                                //ProgressBar.setVisibility(View.INVISIBLE)
////            viewOnProgress.setVisibility(View.VISIBLE)
//                        IncButton.setVisibility(View.VISIBLE)
//                        DecButton.setVisibility(View.VISIBLE)
//                        CreateOrderBtn.setVisibility(View.VISIBLE)
                                Log.e("RETROFIT_ERROR", response.code().toString())
                                //Toast.makeText(this@RecyclerAdapter, "Invalid Credential", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                }
            }

        }
    }

    override fun getItemCount(): Int {
        return newpets.size
    }

    fun setData(pets: MutableList<Orders>, _newpets: MutableList<Dish>)
    {
        this.newpets=pets
        this.newpetInterests=_newpets
        notifyDataSetChanged()
    }

}