package fr.isen.giraud.androiderestaurant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.giraud.androiderestaurant.databinding.ActivityPanierBinding
import fr.isen.giraud.androiderestaurant.domain.CartLine
import fr.isen.giraud.androiderestaurant.domain.Cart
import java.io.File

class CartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPanierBinding
    private val itemsList = ArrayList<CartLine>()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cart: Cart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanierBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        cart = CartRead()
        //titre fenetre
        title = "Votre panier"

        //setup du recycler view
        val recyclerPanier: RecyclerView = binding.recyclerPanier
        cartAdapter = CartAdapter(itemsList,CartAdapter.OnClickListener { item ->
            onListCartClickDelete(item)
        })
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerPanier.layoutManager = layoutManager
        recyclerPanier.adapter = cartAdapter
        cart.lignes.forEach { ligne: CartLine-> itemsList.add(ligne) }
        cartAdapter.notifyDataSetChanged()

        //prix
        updateTotalPrice()
    }


    private fun updateTotalPrice(){
        var totalPrice = 0.0F
        this.cart.lignes.forEach { ligne:CartLine -> totalPrice += ligne.Item.prices[0].price.toFloat() * ligne.quantite }
        binding.buttonTotalPrice.text = "Payez " + totalPrice.toString() + " €"
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