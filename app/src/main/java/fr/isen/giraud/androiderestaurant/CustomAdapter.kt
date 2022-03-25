package fr.isen.giraud.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.giraud.androiderestaurant.databinding.ItemCategoryBinding
import fr.isen.giraud.androiderestaurant.domain.Item

internal class CustomAdapter(private var itemsList: List<Item>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {


    private lateinit var binding: ItemCategoryBinding


    internal inner class MyViewHolder(binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemName: TextView = binding.ItemName
        var prix: TextView = binding.ItemPrix
        var img : ImageView= binding.image
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.itemName.text = item.name_fr
        holder.prix.text = item.prices[0].price + " €"
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }

        if (item.images[0].isEmpty()) {
            holder.img.setImageResource(R.drawable.foodlogo)
        } else{
            if(item.name_fr.equals("Burger maison")){
                Picasso.get().load(item.images[1]).into(holder.img)
            }
            else{
                Picasso.get().load(item.images[0]).into(holder.img)
            }
        }
    }
    override fun getItemCount(): Int {
        return itemsList.size
    }

    class OnClickListener(val clickListener: (item : Item) -> Unit) {
        fun onClick(item: Item) = clickListener(item)
    }
}