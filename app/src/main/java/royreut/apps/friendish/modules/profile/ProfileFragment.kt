package royreut.apps.friendish.modules.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import royreut.apps.friendish.base.MyApplication
import royreut.apps.friendish.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var userEmailTextView: TextView? = null

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        val user = MyApplication.Globals.user

        userEmailTextView = binding.showcaseUserEmail

        userEmailTextView?.text = user?.email ?: "BOOP"

        return view
    }
}