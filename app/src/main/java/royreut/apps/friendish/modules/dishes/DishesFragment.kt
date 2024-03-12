package royreut.apps.friendish.modules.dishes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import royreut.apps.friendish.R
import royreut.apps.friendish.models.Dish
import royreut.apps.friendish.models.Model
import royreut.apps.friendish.modules.dishes.adapter.DishesRecyclerAdapter

class DishesFragment : Fragment() {

    var dishesRecyclerView : RecyclerView? = null
    var dishes:MutableList<Dish>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dishes, container, false)

        dishes = Model.instance.dishes

        dishesRecyclerView = view.findViewById(R.id.rvDishFragmentList)
        dishesRecyclerView?.setHasFixedSize(true)

        // Set the layout manager
        dishesRecyclerView?.layoutManager = LinearLayoutManager(context)

        val adapter = DishesRecyclerAdapter(dishes)
        adapter.listener = object : DishesRecyclerViewActivity.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("TAG", "position: $position")
            }

            override fun onDishClick(dish: Dish?) {
                Log.i("TAG", "dish: ${dish}")
            }
        }

        dishesRecyclerView?.adapter = adapter

        return view
    }
}