package royreut.apps.friendish.modules.dishes.adapter

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import royreut.apps.friendish.R
import royreut.apps.friendish.models.Dish
import royreut.apps.friendish.modules.dishes.DishesRecyclerViewActivity

class DishesViewHolder(val itemView: View, val listener:DishesRecyclerViewActivity.OnItemClickListener?, var dishes:List<Dish>?): RecyclerView.ViewHolder(itemView) {

    fun bind(dish: Dish?) {
        this.dish = dish
        nameTextView?.text = dish?.name

        Picasso.get().load(dish?.imageUrl)
//            .placeholder("https://upload.wikimedia.org/wikipedia/commons/6/6e/Golde33443.jpg")
//            .error(R.drawable.error_gfg)
            .centerCrop()
            .fit()
            .into(dishImage);
    }

    var nameTextView: TextView? = null
    var dishImage: ImageView? = null
    var dish: Dish? = null

    init {
        nameTextView = itemView.findViewById(R.id.lvDishListName)
        dishImage = itemView.findViewById(R.id.imageView)

        itemView.setOnClickListener {
            Log.i("TAG", "position: $adapterPosition")
            listener?.onDishClick(dish)
        }
    }
}
