package royreut.apps.friendish.modules.addDish

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import royreut.apps.friendish.R
import royreut.apps.friendish.base.MyApplication
import royreut.apps.friendish.databinding.FragmentAddDishBinding
import royreut.apps.friendish.models.Dish
import royreut.apps.friendish.models.Model
import royreut.apps.friendish.modules.DishViewModel
import java.util.UUID


class AddDishFragment : Fragment() {

    private var dishNameTextField: EditText? = null
    private var recipeTextField: EditText? = null
    private var dishImageView: ImageView? = null
    private var dishImageUri: Uri? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null
    private var uploadImageButton: ImageButton? = null

    private var _binding: FragmentAddDishBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DishViewModel

    private var storageRef = Firebase.storage.reference;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddDishBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        dishNameTextField = binding.editDishName
        recipeTextField = binding.editRecipe
        dishImageView = binding.previewDishImageView
        uploadImageButton = binding.uploadImageButton
        saveButton = binding.saveRecipeButton
        cancelButton = binding.cancelRecipeButton

        uploadImageButton?.setOnClickListener {
            // PICK INTENT picks item from data
            // and returned selected item
            val galleryIntent = Intent(Intent.ACTION_PICK)
            // here item is type of image
            galleryIntent.type = "image/*"
            // ActivityResultLauncher callback
            imagePickerActivityResult.launch(galleryIntent)
        };

        cancelButton?.setOnClickListener { Navigation.findNavController(it).popBackStack(R.id.dishesFragment, false) }
        saveButton?.setOnClickListener {
            val name = dishNameTextField?.text.toString()
            val recipe = recipeTextField?.text.toString()
            val author = MyApplication.Globals.user?.email ?: ""
            val id = UUID.randomUUID().toString()

            if (dishImageUri != null) {
                uploadImageToServer { uri ->
                    val dish = Dish(id, name, recipe, false, author, uri)
                    saveDish(dish, it)
                }
            } else {
                val dish = Dish(id, name, recipe, false, author, "https://upload.wikimedia.org/wikipedia/commons/6/6e/Golde33443.jpg")
                saveDish(dish, it)
            }
        }
    }

    private fun saveDish(dish:Dish, view: View) {
        Model.instance.addDish(dish) {
            Navigation.findNavController(view).navigate(R.id.dishesFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }


    private var imagePickerActivityResult: ActivityResultLauncher<Intent> =
    // lambda expression to receive a result back, here we
        // receive single item(photo) on selection
        registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null) {
                // getting URI of selected Image
                dishImageUri = result.data?.data

                Picasso.get()
                    .load(dishImageUri)
                    .into(dishImageView);
            }
        }

    @SuppressLint("Range")
    private fun getFileName(context: Context?, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context?.contentResolver?.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }

    private fun uploadImageToServer(callback: (String) -> Unit) {
                // extract the file name with extension
        val sd = getFileName(MyApplication.Globals.appContext, dishImageUri!!)

        // Upload Task with upload to directory 'file'
        // and name of the file remains same
        val uploadTask = storageRef.child("file/$sd").putFile(dishImageUri!!)

        // On success, download the file URL and display it
        uploadTask.addOnSuccessListener {
            // using glide library to display the image
            storageRef.child("file/$sd").downloadUrl.addOnSuccessListener {
                Log.e("Firebase", "download passed - ${it.path}")
                callback(it.toString())
            }.addOnFailureListener {
                Log.e("Firebase", "Failed in downloading")
            }
        }.addOnFailureListener {
            Log.e("Firebase", "Image Upload fail")
        }
    }
}