package royreut.apps.friendish.modules.dishes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import royreut.apps.friendish.R
import royreut.apps.friendish.models.Dish
import royreut.apps.friendish.models.Model
import royreut.apps.friendish.modules.dishes.adapter.DishesRecyclerAdapter

class DishesFragment : Fragment() {

    var dishesRecyclerView : RecyclerView? = null
    var dishes:List<Dish>? = null
    var progressBar:ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dishes, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        progressBar?.visibility = View.VISIBLE
        val adapter = DishesRecyclerAdapter(dishes)
        Model.instance.getAllDishes { dishes ->
            this.dishes = dishes
            adapter.dishes = dishes
            adapter?.notifyDataSetChanged()
            progressBar?.visibility = View.GONE
        }

        dishesRecyclerView = view.findViewById(R.id.rvDishFragmentList)
        dishesRecyclerView?.setHasFixedSize(true)

        // Set the layout manager
        dishesRecyclerView?.layoutManager = LinearLayoutManager(context)

        adapter.listener = object : DishesRecyclerViewActivity.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("TAG", "position: $position")
                val dish = adapter.dishes?.get(position)
                dish?.let {
                    val action = DishesFragmentDirections.actionDishesFragmentToBlueFragment(it.name)
                    Navigation.findNavController(view).navigate(action)
                }
            }

            override fun onDishClick(dish: Dish?) {
                Log.i("TAG", "dish: ${dish}")
            }
        }

        dishesRecyclerView?.adapter = adapter

        val addDishButton:ImageButton = view.findViewById(R.id.addDishFloatingButton)
        val action = Navigation.createNavigateOnClickListener(DishesFragmentDirections.actionGlobalAddDishFragment())

        addDishButton.setOnClickListener(action)

        return view
    }
}