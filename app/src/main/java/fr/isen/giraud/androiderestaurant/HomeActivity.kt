package fr.isen.giraud.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import fr.isen.giraud.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "AndroidERestaurant"

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var buttonEntrees = binding.buttonEntrees
        var buttonPlats = binding.buttonPlats
        var buttonDesserts = binding.buttonDesserts
        var buttonBLE = binding.buttonBLE

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
        buttonBLE.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page bluetooth", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, BLEScanActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("destroy", "onDestroy: ")
    }
}