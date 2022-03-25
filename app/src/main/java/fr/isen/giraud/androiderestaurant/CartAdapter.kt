package fr.isen.giraud.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import fr.isen.giraud.androiderestaurant.databinding.ItemCartBinding
import fr.isen.giraud.androiderestaurant.domain.CartLine

internal class CartAdapter(private var itemsList: MutableList<CartLine>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    private lateinit var binding: ItemCartBinding

    internal inner class MyViewHolder(binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.nameItem
        val quantity = binding.quantite
        val totalItemPrice = binding.itemTotalPrice
        val btnDelete = binding.delete

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.name.text = item.Item.name_fr
        holder.quantity.text = item.quantite.toString()
        holder.totalItemPrice.text = (item.Item.prices[0].price.toFloat()*item.quantite).toString()+" €"
        holder.btnDelete.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class OnClickListener(val clickListener: (item: CartLine) -> Unit) {
        fun onClick(item: CartLine) = clickListener(item)
    }


}