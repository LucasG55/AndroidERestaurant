package fr.isen.giraud.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import fr.isen.giraud.androiderestaurant.databinding.ActivityItemBinding
import fr.isen.giraud.androiderestaurant.domain.Item
import fr.isen.giraud.androiderestaurant.domain.CartLine
import fr.isen.giraud.androiderestaurant.domain.Cart
import pl.polak.clicknumberpicker.ClickNumberPickerListener
import java.io.File

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    private lateinit var mViewPagerAdapter: PagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var cart:Cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)


        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.cart = CartRead()

        val item: Item = Gson().fromJson(intent.getStringExtra("Item"), Item::class.java)

        val itemName: TextView = binding.itemName
        itemName.text = item.name_fr
        binding.toolBar.title = item.name_fr


        val ingredients: TextView = binding.ingredients

        item.ingredients.forEachIndexed{index,ingredient ->
            ingredients.append(ingredient.name_fr)
            if(index!=item.ingredients.size-1){
                ingredients.append(", ")
            }
        }

        var imageList = item.images

        viewPager = findViewById(R.id.viewpager)

        mViewPagerAdapter = ViewPagerAdapter(this, imageList)
        viewPager.adapter = mViewPagerAdapter

        viewPager.pageMargin = 15
        viewPager.setPadding(50, 0, 50, 0);
        viewPager.setClipToPadding(false)
        viewPager.setPageMargin(25)

        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)

        //initialisation badge sur le panier
        binding.cartNumber.text = initNumCart()


        val picker = binding.pickNumber
        var buyButton = binding.buyButton
        picker.setClickNumberPickerListener(ClickNumberPickerListener { previousValue, currentValue, pickerClickType ->
            buyButton.text = "TOTAL " + (item.prices[0].price.toFloat()*currentValue).toString() + " €"
        })
        picker.setPickerValue(1f)

        buyButton.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "Ajouté à votre panier", Toast.LENGTH_SHORT).show()
            //modification texte badge et ecriture dans le fichier panier
            binding.cartNumber.text = CartWrite(binding.pickNumber.value.toInt(),item)
        }

        var cartButton = binding.cart
        cartButton.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "Votre panier", Toast.LENGTH_SHORT).show()
            this.cart = CartRead()

            val intent = Intent(this,CartActivity::class.java)
            startActivity(intent)
        }

    }

    var viewPagerPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                // your logic here
            }
            override fun onPageScrolled(position: Int,positionOffset: Float,
                                        positionOffsetPixels: Int) {
                // your logic here
            }
            override fun onPageSelected(position: Int) {
                // your logic here
            }
        }

    override fun onRestart() {
        super.onRestart()
        this.cart = CartRead()
        binding.cartNumber.text = cart.lignes.size.toString()
    }

    private fun initNumCart():String{
        //sauvegarde du panier en json dans les fichiers
        //lecture du fichier
        val filename1 = "panier.json"
        val file = File(binding.root.context.filesDir, filename1)
        //si le fichier existe on lit le panier directement dedans

        return if(file.exists()){
            val contents = file.readText()
            cart = Gson().fromJson(contents,Cart::class.java)
            cart.lignes.size.toString()
        } else{
            ""
        }
    }

    private fun CartRead(): Cart {
        //lecture fichier panier
        val filename = "panier.json"
        val file = File(binding.root.context.filesDir, filename)
        return if(file.exists()){
            val contents = file.readText()
            Gson().fromJson(contents, Cart::class.java)
        }else{
            Cart(ArrayList())
        }
    }

    private fun CartWrite(value: Int, item:Item): String {
        //sauvegarde du panier en json dans les fichiers
        val lignePanier = CartLine(value,item)

        //ecriture nombre d'article dans panier
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit().putInt("nbItemPanier", value).apply()

        //lecture du fichier
        val filename1 = "panier.json"
        val file = File(binding.root.context.filesDir, filename1)
        //si le fichier existe on lit le panier directement dedans
        cart = if(file.exists()){
            val contents = file.readText()
            Gson().fromJson(contents,Cart::class.java)
        }
        //si le fichier n'existe pas on cree un panier vide
        else{
            Cart(ArrayList())
        }
        //puis on ajoute notre element au panier et on ecrit le fichier
        cart.lignes.add(lignePanier)
        val panierJson = Gson().toJson(cart)
        Log.d("Panier",panierJson)
        val filename = "panier.json"
        this.binding.root.context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(panierJson.toByteArray())
        }
        return cart.lignes.size.toString()
    }

}