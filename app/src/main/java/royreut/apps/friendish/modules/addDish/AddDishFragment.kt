package royreut.apps.friendish.modules.addDish

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import royreut.apps.friendish.R
import royreut.apps.friendish.ShowcaseUserActivity

class AddDishFragment : Fragment() {

    private var dishNameTextField: EditText? = null
    private var recipeTextField: EditText? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

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

        cancelButton?.setOnClickListener { // todo finish() }
            saveButton?.setOnClickListener {
                // todo: change ShowcaseUserActivity to activity which adds dishes
//            val myIntent = Intent(this, ShowcaseUserActivity::class.java)
//
//            myIntent.putExtra("name", dishNameTextField?.text.toString());
//            myIntent.putExtra("email", recipeTextField?.text.toString());

            }
        }
    }
}