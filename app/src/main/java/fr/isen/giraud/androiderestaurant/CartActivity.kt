package fr.isen.giraud.androiderestaurant

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.giraud.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.giraud.androiderestaurant.model.Cart
import fr.isen.giraud.androiderestaurant.model.CartLine
import fr.isen.giraud.androiderestaurant.view.CartAdapter
import java.io.File


class CartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCartBinding
    private val itemsList = ArrayList<CartLine>()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cart: Cart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        cart = CartRead()
        //titre fenetre
        title = "Votre panier"

        //setup du recycler view
        val recyclerCart: RecyclerView = binding.recyclerCart
        cartAdapter = CartAdapter(itemsList, CartAdapter.OnClickListener { item ->
            onListCartClickDelete(item)
        })
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerCart.layoutManager = layoutManager
        recyclerCart.adapter = cartAdapter
        cart.lignes.forEach { ligne: CartLine-> itemsList.add(ligne) }
        cartAdapter.notifyDataSetChanged()

        //prix
        updateTotalPrice()

        val buyButton = binding.buttonTotalPrice

        buyButton.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "You buy it", Toast.LENGTH_SHORT).show()
        }

    }


    private fun updateTotalPrice(){
        var totalPrice = 0.0F
        this.cart.lignes.forEach { ligne:CartLine -> totalPrice += ligne.Item.prices[0].price.toFloat() * ligne.quantite }

        if(totalPrice == 0.0F){
            binding.buttonTotalPrice.text = "Vous n'avez rien dans votre panier"
        }
        else{
            binding.buttonTotalPrice.text = "Payez " + totalPrice.toString() + " €"
        }
    }

    private fun onListCartClickDelete(item: CartLine) {
        Toast.makeText(this@CartActivity, "${item.quantite} ${item.Item.name_fr} enlevé(s) du panier",Toast.LENGTH_SHORT).show()
        this.itemsList.remove(item)
        this.cart.lignes.remove(item)
        Log.d("PANIER",cart.lignes.size.toString())
        this.CartWrite()
        this.updateTotalPrice()
        this.cartAdapter.notifyDataSetChanged()
    }

    private fun CartWrite(){
        //sauvegarde du panier en json dans les fichiers
        val panierJson = Gson().toJson(cart)
        val filename = "panier.json"
        this.binding.root.context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(panierJson.toByteArray())
        }
    }

    private fun CartRead(): Cart {
        //lecture fichier panier
        val filename = "panier.json"
        val file = File(binding.root.context.filesDir, filename)
        if(file.exists()){
            val contents = file.readText()
            return Gson().fromJson(contents, Cart::class.java)
        }else{
            return Cart(ArrayList())
        }
    }
}