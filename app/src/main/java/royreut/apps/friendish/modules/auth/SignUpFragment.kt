package royreut.apps.friendish.modules.auth

import android.os.Bundle
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
import royreut.apps.friendish.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private var emailTextView: TextView? = null
    private var passwordTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        emailTextView = binding.userNewEmail
        passwordTextView = binding.userNewPassword

        val loginBtn = binding.signUpBtn
        loginBtn.setOnClickListener(::onSignUpWithFirebase)

        return view
    }

    fun onSignUpWithFirebase(view: View) {
        val email = emailTextView?.text.toString()
        val password = passwordTextView?.text.toString()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Navigation.findNavController(view)
                    .navigate(R.id.action_signUpFragment_to_loginFragment)
            } else {
                Toast.makeText(
                    view.context,
                    "Sign up failed. ${task.exception?.message}",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

}