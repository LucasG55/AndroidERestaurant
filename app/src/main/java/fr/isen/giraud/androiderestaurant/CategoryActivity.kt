package fr.isen.giraud.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.giraud.androiderestaurant.domain.APIData
import fr.isen.giraud.androiderestaurant.domain.Item
import org.json.JSONObject
import java.nio.charset.Charset

class CategoryActivity : AppCompatActivity() {

    private val itemsList = ArrayList<Item>()
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        var category = intent.getStringExtra("Category");
        setTitle(category)
        

        val recyclerView: RecyclerView = findViewById(R.id.list_item)
        customAdapter = CustomAdapter(itemsList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter

        post()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("destroy", "onDestroy: ")
    }

    fun post(){

        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"

        val json = JSONObject()
        json.put("id_shop", "1")
        json.toString()
        val requestBody = json.toString()

        val stringReq : StringRequest =
            object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    // response
                    var strResp = response.toString()
                    val apiData = Gson().fromJson(strResp, APIData::class.java)
                    Log.d("API", strResp)
                    fillList(apiData)
                },
                Response.ErrorListener { error ->
                    Log.d("API", "error => $error")
                }
            ){
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
        queue.add(stringReq)
    }

    fun fillList(apiData: APIData){
        if(intent.getStringExtra("Category") == "Entrées"){
            apiData.data[0].items.forEach { item: Item -> itemsList.add(item) }
        }
        if(intent.getStringExtra("Category") == "Plats"){
            apiData.data[1].items.forEach { item: Item -> itemsList.add(item) }
        }
        if(intent.getStringExtra("Category") == "Desserts"){
            apiData.data[2].items.forEach { item: Item -> itemsList.add(item) }
        }

        customAdapter.notifyDataSetChanged()
    }

}