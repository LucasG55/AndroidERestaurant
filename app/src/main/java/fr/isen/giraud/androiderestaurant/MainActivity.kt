package fr.isen.giraud.androiderestaurant

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonEntrees = findViewById(R.id.buttonEntrees) as Button
        var buttonPlats = findViewById(R.id.buttonPlats) as Button
        var buttonDesserts = findViewById(R.id.buttonDesserts) as Button

        buttonEntrees.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page des entrées", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("Category", "Entrées")
            startActivity(intent)
        }
        buttonPlats.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page plats", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("Category", "Plats")
            startActivity(intent)
        }
        buttonDesserts.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page desserts", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("Category", "Desserts")
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("destroy", "onDestroy: ")
    }
}