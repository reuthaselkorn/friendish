package royreut.apps.friendish.modules.addDish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import royreut.apps.friendish.R
import royreut.apps.friendish.ShowcaseUserActivity

class AddDishActivity : AppCompatActivity() {
    var dishNameTextField: EditText? = null
    var recipeTextField: EditText? = null
    var saveButton: Button? = null
    var cancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        setupUI()
    }

    private fun setupUI() {
        dishNameTextField = findViewById(R.id.editDishName)
        recipeTextField = findViewById(R.id.editRecipe)
        saveButton = findViewById(R.id.saveRecipeButton)
        cancelButton = findViewById(R.id.cancelRecipeButton)

        cancelButton?.setOnClickListener { finish() }
        saveButton?.setOnClickListener {
            // todo: change ShowcaseUserActivity to activity which adds dishes
            val myIntent = Intent(this@AddDishActivity, ShowcaseUserActivity::class.java)

            myIntent.putExtra("name", dishNameTextField?.text.toString());
            myIntent.putExtra("email", recipeTextField?.text.toString());

            this@AddDishActivity.startActivity(myIntent)
        }
    }

}