package fr.isen.giraud.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.giraud.androiderestaurant.domain.APIData
import org.json.JSONObject
import java.nio.charset.Charset

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        var category = intent.getStringExtra("Category");
        var categoryName = findViewById(R.id.categoryName) as TextView

        categoryName.text = category

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
                    val apiData = Gson().fromJson(strResp, String::class.java)
                    Log.d("API", strResp)
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

}