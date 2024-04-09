package royreut.apps.friendish.modules.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import royreut.apps.friendish.R
import royreut.apps.friendish.databinding.FragmentSignUpBinding
import royreut.apps.friendish.models.Model

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private var emailTextView: TextView? = null
    private var passwordTextView: TextView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        progressBar = binding.progressBarSignUp

        progressBar?.visibility = View.GONE

        val loginBtn = binding.signUpBtn
        loginBtn.setOnClickListener(::onSignUpWithFirebase)

        return view
    }

    fun onSignUpWithFirebase(view: View) {
        progressBar?.visibility = View.VISIBLE
        val email = emailTextView?.text.toString()
        val password = passwordTextView?.text.toString()
        if(!(email.isNullOrBlank() || password.isNullOrBlank())) {
            Model.instance.signupUser(email, password) { task ->
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
                progressBar?.visibility = View.GONE
            }
        } else {
            Toast.makeText(
                view.context,
                "Please fill all fields",
                Toast.LENGTH_SHORT,
            ).show()

            progressBar?.visibility = View.GONE
        }
    }

}