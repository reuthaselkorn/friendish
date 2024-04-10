package royreut.apps.friendish.modules.userDishes.adapter

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import royreut.apps.friendish.R
import royreut.apps.friendish.models.Dish
import royreut.apps.friendish.modules.userDishes.UserDishesRecyclerViewActivity

class UserDishesViewHolder(val itemView: View, val listener: UserDishesRecyclerViewActivity.OnItemClickListener?, var dishes:List<Dish>?): RecyclerView.ViewHolder(itemView) {

    fun bind(dish: Dish?) {
        this.dish = dish
        nameTextView?.text = dish?.name
        recipeTextView?.text = dish?.recipe

        Picasso.get().load(dish?.imageUrl)
//            .placeholder("https://upload.wikimedia.org/wikipedia/commons/6/6e/Golde33443.jpg")
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .fit()
            .into(dishImage);

        dishCheckbox?.apply {
            isChecked = dish?.isChecked ?: false
            tag = adapterPosition
        }
    }

    var nameTextView: TextView? = null
    var recipeTextView: TextView? = null
    var dishCheckbox: CheckBox? = null
    var dishImage: ImageView? = null
    var dish: Dish? = null

    init {
        nameTextView = itemView.findViewById(R.id.lvUserDishListName)
        recipeTextView = itemView.findViewById(R.id.lvUserDishListRecipe)
        dishCheckbox = itemView.findViewById(R.id.lvUserDishListCheckBox)
        dishImage = itemView.findViewById(R.id.imageViewUserDish)

        dishCheckbox?.setOnClickListener {
            dishes?.get(adapterPosition)?.isChecked = dishCheckbox?.isChecked ?: false
        }

        itemView.setOnClickListener {
            Log.i("TAG", "position: $adapterPosition")
            listener?.onDishClick(dish)
        }
    }
}
