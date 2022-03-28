package fr.isen.giraud.androiderestaurant.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import fr.isen.giraud.androiderestaurant.R

class ViewPagerAdapter (private val mContext: Context, private  val itemList: Array<String>) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater!!.inflate(R.layout.picture, container, false)
        var imageItem: ImageView = view.findViewById(R.id.itemImage)

        if (itemList[position].isEmpty()) {
            imageItem.setImageResource(R.drawable.foodlogo)
        } else{
            Picasso.get().load(itemList[position]).into(imageItem)
        }

        container.addView(view, position)
        return view
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}