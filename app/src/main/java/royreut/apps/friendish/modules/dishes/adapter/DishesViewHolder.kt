package royreut.apps.friendish.modules.dishes.adapter

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import royreut.apps.friendish.R
import royreut.apps.friendish.models.Dish
import royreut.apps.friendish.modules.dishes.DishesRecyclerViewActivity

class DishesViewHolder(val itemView: View, val listener:DishesRecyclerViewActivity.OnItemClickListener?, var dishes:MutableList<Dish>?): RecyclerView.ViewHolder(itemView) {

    fun bind(dish: Dish?) {
        this.dish = dish
        nameTextView?.text = dish?.name
        recipeTextView?.text = dish?.recipe
        dishCheckbox?.apply {
            isChecked = dish?.isChecked ?: false
            tag = adapterPosition
        }
    }

    var nameTextView: TextView? = null
    var recipeTextView: TextView? = null
    var dishCheckbox: CheckBox? = null
    var dish: Dish? = null

    init {
        nameTextView = itemView.findViewById(R.id.lvDishListName)
        recipeTextView = itemView.findViewById(R.id.lvDishListRecipe)
        dishCheckbox = itemView.findViewById(R.id.lvDishListCheckBox)

        dishCheckbox?.setOnClickListener {
            dishes?.get(adapterPosition)?.isChecked = dishCheckbox?.isChecked ?: false
        }

        itemView.setOnClickListener {
            Log.i("TAG", "position: $adapterPosition")
            listener?.onItemClick(adapterPosition)
            listener?.onDishClick(dish)
        }
    }
}
