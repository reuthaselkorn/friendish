package royreut.apps.friendish.modules.api

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import royreut.apps.friendish.R
import royreut.apps.friendish.Repository.RecipeIdeaRepository
import royreut.apps.friendish.databinding.FragmentApiBinding
import royreut.apps.friendish.databinding.FragmentShowcaseDishBinding
import royreut.apps.friendish.models.RecipeIdea

class ApiFragment : Fragment() {
    private lateinit var recipeIdeaRepository: RecipeIdeaRepository
    lateinit var recipeIdeas: List<RecipeIdea>
    private var _binding: FragmentApiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentApiBinding.inflate(inflater, container, false)
        val view = binding.root

        val result = binding.result
        val qText = binding.qText
        val btnSearch = binding.btnSearch

        btnSearch.setOnClickListener {
            recipeIdeaRepository = RecipeIdeaRepository()
            recipeIdeaRepository.getIdeas(qText.text.toString()) { recipeIdeas ->
                result.text = recipeIdeas.get(0).title.toString() ?: "no recipe found"
            }
        }

        return view
    }

    fun onClickSearchRecipe(view : View) {

    }
}