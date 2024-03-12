package royreut.apps.friendish.modules.dishes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import royreut.apps.friendish.R
import royreut.apps.friendish.models.Dish
import royreut.apps.friendish.models.Model

class DishesListActivity : AppCompatActivity() {

    var dishesListView:ListView? = null
    var dishes:MutableList<Dish>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dishes_list)

        dishes = Model.instance.dishes

        dishesListView = findViewById(R.id.lvDishesList)
        dishesListView?.adapter = DishListAdapter(dishes)
    }

    class DishListAdapter(private val dishes:MutableList<Dish>?): BaseAdapter() {

        override fun getCount(): Int = dishes?.size ?: 0

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemId(position: Int): Long = 0

        @SuppressLint("CutPasteId")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val dish = dishes?.get(position)
            var view: View? = null

            if (convertView == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.dish_layout_row, parent, false)
                val checkBox : CheckBox? = view?.findViewById(R.id.lvDishListCheckBox)
                checkBox?.setOnClickListener {
                    (checkBox.tag as? Int)?.let {tag ->
                        dishes?.get(tag)?.isChecked = checkBox?.isChecked ?: false
                    }
                }
            }

            view = view ?: convertView;

            val nameTextView:TextView? = view?.findViewById(R.id.lvDishListName)
            val recipeTextView:TextView? = view?.findViewById(R.id.lvDishListRecipe)
            val dishCheckbox:CheckBox? = view?.findViewById(R.id.lvDishListCheckBox)

            nameTextView?.text = dish?.name
            recipeTextView?.text = dish?.recipe
            dishCheckbox?.apply {
                isChecked = dish?.isChecked ?: false
                tag = position
            }

            return view!!
        }
    }
}

