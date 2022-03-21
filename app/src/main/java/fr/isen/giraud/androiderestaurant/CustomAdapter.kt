package fr.isen.giraud.androiderestaurant

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.giraud.androiderestaurant.domain.Item

internal class CustomAdapter(private var itemsList: List<Item>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemName: TextView = view.findViewById(R.id.ItemName)
        var ingredient1: TextView = view.findViewById(R.id.Ingredient1)
        var image : ImageView = view.findViewById(R.id.image)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item= itemsList[position]
        holder.itemName.text = item.name_fr
        holder.ingredient1.text = item.ingredients[0].name_fr
        if (item.images[0].isEmpty()) {
            holder.image.setImageResource(R.drawable.img);
        } else{
            Picasso.get().load(item.images[0]).into(holder.image);
        }
    }
    override fun getItemCount(): Int {
        return itemsList.size
    }
}