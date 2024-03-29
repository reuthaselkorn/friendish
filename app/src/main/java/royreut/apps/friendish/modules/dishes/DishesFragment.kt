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
import royreut.apps.friendish.databinding.FragmentDishesBinding
import royreut.apps.friendish.models.Dish
import royreut.apps.friendish.models.Model
import royreut.apps.friendish.modules.dishes.adapter.DishesRecyclerAdapter

class DishesFragment : Fragment() {

    var dishesRecyclerView : RecyclerView? = null
    var dishes:List<Dish>? = null
    var adapter:DishesRecyclerAdapter? = null
    var progressBar:ProgressBar? = null

    private var _binding:FragmentDishesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDishesBinding.inflate(inflater, container, false)
        val view = binding.root

        progressBar = binding.progressBar
        progressBar?.visibility = View.VISIBLE
        adapter = DishesRecyclerAdapter(dishes)
        Model.instance.getAllDishes { dishes ->
            this.dishes = dishes
            adapter?.dishes = dishes
            adapter?.notifyDataSetChanged()
            progressBar?.visibility = View.GONE
        }

        dishesRecyclerView = binding.rvDishFragmentList
        dishesRecyclerView?.setHasFixedSize(true)

        // Set the layout manager
        dishesRecyclerView?.layoutManager = LinearLayoutManager(context)

        adapter?.listener = object : DishesRecyclerViewActivity.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("TAG", "position: $position")
                val dish = adapter?.dishes?.get(position)
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

        val addDishButton:ImageButton = binding.addDishFloatingButton
        val action = Navigation.createNavigateOnClickListener(DishesFragmentDirections.actionGlobalAddDishFragment())

        addDishButton.setOnClickListener(action)

        return view
    }

    override fun onResume() {
        super.onResume()
        progressBar?.visibility = View.VISIBLE

        Model.instance.getAllDishes {
            this.dishes = dishes
            adapter?.dishes = dishes
            adapter?.notifyDataSetChanged()

            progressBar?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}