package royreut.apps.friendish.modules.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import royreut.apps.friendish.R
import royreut.apps.friendish.databinding.FragmentLoginBinding
import royreut.apps.friendish.databinding.FragmentShowcaseDishBinding

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var emailTextView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        emailTextView = binding.userEmail

        val button = binding.loginBtn
        button.setOnClickListener {
            val email = emailTextView?.text.toString()

            Toast.makeText(
                view.context,
                email,
                Toast.LENGTH_SHORT,
            ).show()
        }

        return view
    }

    fun onLoginButtonClicked(view: View) {

    }

}