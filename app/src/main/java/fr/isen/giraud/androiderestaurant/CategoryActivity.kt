package fr.isen.giraud.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.giraud.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.giraud.androiderestaurant.model.APIData
import fr.isen.giraud.androiderestaurant.model.Item
import fr.isen.giraud.androiderestaurant.view.CustomAdapter
import org.json.JSONObject
import java.nio.charset.Charset

class CategoryActivity : AppCompatActivity() {

    private val itemsList = ArrayList<Item>()
    private lateinit var customAdapter: CustomAdapter
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var category = intent.getStringExtra("Category");
        title = category



        val recyclerView: RecyclerView = binding.listItem
        customAdapter = CustomAdapter(itemsList, CustomAdapter.OnClickListener { item ->
            onClickListItem(item) })
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter

        post()

        //handle refresh
        binding.refresh.setOnRefreshListener(refreshListener)

    }

    private val refreshListener = OnRefreshListener {
        binding.refresh.isRefreshing = false
        itemsList.clear()
        post()
    }


    fun onClickListItem (item: Item) {
        Toast.makeText(applicationContext, "${item.name_fr}", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("Item", Gson().toJson(item))
        startActivity(intent)

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

                    binding.refresh.isRefreshing = false
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