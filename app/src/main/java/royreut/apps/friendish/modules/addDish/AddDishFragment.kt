package royreut.apps.friendish.modules.addDish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import royreut.apps.friendish.R

class AddDishFragment : Fragment() {

    private var dishNameTextField: EditText? = null
    private var recipeTextField: EditText? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_dish, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        dishNameTextField = view.findViewById(R.id.editDishName)
        recipeTextField = view.findViewById(R.id.editRecipe)
        saveButton = view.findViewById(R.id.saveRecipeButton)
        cancelButton = view.findViewById(R.id.cancelRecipeButton)

        cancelButton?.setOnClickListener { Navigation.findNavController(it).popBackStack(R.id.dishesFragment, false) }
            saveButton?.setOnClickListener {
                // todo: change ShowcaseUserActivity to activity which adds dishes
//            val myIntent = Intent(this, ShowcaseUserActivity::class.java)
//
//            myIntent.putExtra("name", dishNameTextField?.text.toString());
//            myIntent.putExtra("email", recipeTextField?.text.toString());

            }
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}