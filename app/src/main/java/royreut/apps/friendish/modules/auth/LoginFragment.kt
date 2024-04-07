package royreut.apps.friendish.modules.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import royreut.apps.friendish.R
import royreut.apps.friendish.base.MyApplication
import royreut.apps.friendish.databinding.FragmentLoginBinding
import royreut.apps.friendish.models.User

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginBinding? = null
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        emailTextView = binding.userEmail
        passwordTextView = binding.userPassword

        val loginBtn = binding.loginBtn
        loginBtn.setOnClickListener(::onLoginButtonClicked)

        val signUpBtn = binding.linkToSignUp
        signUpBtn.setOnClickListener(::onLinkToSignUpClicked)
        return view
    }

    fun onLinkToSignUpClicked(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment)
    }

    fun onLoginButtonClicked(view: View) {

        val email = emailTextView?.text.toString()
        val password = passwordTextView?.text.toString()

        val user = User(email, password)
        if(!(user.email.isNullOrBlank() || user.password.isNullOrBlank())) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    MyApplication.Globals.user = user
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_dishesFragment3)
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        view.context,
                        "Authentication failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
//                progressBar?.visibility = View.GONE
            }
        } else {
            Toast.makeText(
                view.context,
                "Authentication failed. please enter username and password",
                Toast.LENGTH_SHORT,
            ).show()
//            progressBar?.visibility = View.GONE
        }
    }

}