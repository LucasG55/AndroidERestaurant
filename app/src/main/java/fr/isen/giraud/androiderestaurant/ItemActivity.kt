package fr.isen.giraud.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import fr.isen.giraud.androiderestaurant.databinding.ActivityItemBinding
import fr.isen.giraud.androiderestaurant.domain.Item
import pl.polak.clicknumberpicker.ClickNumberPickerListener

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    private lateinit var mViewPagerAdapter: PagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        val picker = binding.pickNumber
        picker.setClickNumberPickerListener(ClickNumberPickerListener { previousValue, currentValue, pickerClickType ->
            val prixTotal : Button = binding.buyButton
            prixTotal.text = "TOTAL " + (item.prices[0].price.toFloat()*currentValue).toString() + " €"
        })
        picker.setPickerValue(1f)

        var buyButton = binding.buyButton
        buyButton.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "Ajouté à votre panier", Toast.LENGTH_SHORT).show()
        }

        var cartButton = binding.cart
        cartButton.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "Votre panier", Toast.LENGTH_SHORT).show()
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

}