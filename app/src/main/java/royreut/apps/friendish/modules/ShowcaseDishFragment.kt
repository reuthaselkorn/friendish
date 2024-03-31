package royreut.apps.friendish.modules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import royreut.apps.friendish.databinding.FragmentShowcaseDishBinding

class ShowcaseDishFragment : Fragment() {
    private var dishNameTextView: TextView? = null
    private var dishRecipeTextView: TextView? = null

    private var _binding: FragmentShowcaseDishBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowcaseDishBinding.inflate(inflater, container, false)
        val view = binding.root

        val dishName = arguments?.let {
            ShowcaseDishFragmentArgs.fromBundle(it).dishname
        }

        val dishRecipe = arguments?.let {
            ShowcaseDishFragmentArgs.fromBundle(it).dishrecipe
        }

        dishNameTextView = binding.showcaseRecipeName
        dishRecipeTextView = binding.showcaseDishRecipe

        dishNameTextView?.text = dishName ?: "BOOP"
        dishRecipeTextView?.text = dishRecipe ?: "BOOP"

        return view
    }
}