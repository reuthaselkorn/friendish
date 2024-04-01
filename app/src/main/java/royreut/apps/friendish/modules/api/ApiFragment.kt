package royreut.apps.friendish.modules.api

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import royreut.apps.friendish.R
import royreut.apps.friendish.databinding.FragmentApiBinding
import royreut.apps.friendish.databinding.FragmentShowcaseDishBinding

class ApiFragment : Fragment() {
    private var _binding: FragmentApiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentApiBinding.inflate(inflater, container, false)
        val view = binding.root

        val randomString = "reutQueen"

        val initialTextView = binding.initialTextAPI
        initialTextView?.text = randomString ?: "BOOP"

        return view
    }
}