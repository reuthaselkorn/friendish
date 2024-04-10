package royreut.apps.friendish.modules.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import royreut.apps.friendish.base.MyApplication
import royreut.apps.friendish.databinding.FragmentProfileBinding
import royreut.apps.friendish.models.Model

class ProfileFragment : Fragment() {
    private var userEmailTextView: TextView? = null
    private var userProfileImageView: ImageView? = null
    private var nicknameEdit: TextView? = null
    private var progressBar: ProgressBar? = null


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
        userProfileImageView = binding.showcaseUserProfileImage
        nicknameEdit = binding.nicknameEdit
        progressBar = binding.saveUserProgressBar

        progressBar?.visibility = View.GONE

        val loginBtn = binding.saveBtn
        loginBtn.setOnClickListener(::onSave)

        nicknameEdit?.text = user?.nickname ?: ""
        userEmailTextView?.text = user?.email ?: "BOOP"
        Picasso.get().load(user?.imageUrl)
//            .placeholder("https://upload.wikimedia.org/wikipedia/commons/6/6e/Golde33443.jpg")
//            .error(R.drawable.error_gfg)
            .centerCrop()
            .fit()
            .into(userProfileImageView);

        return view
    }

    fun onSave(view: View) {
        progressBar?.visibility = View.VISIBLE
        val id = MyApplication.Globals.user?.id
        val email = MyApplication.Globals.user?.email
        if(id != null && nicknameEdit?.text != null && email != null) {
            Model.instance.updateUser(id, nicknameEdit?.text.toString()) {
                Model.instance.getUserByEmail(email)
                progressBar?.visibility = View.GONE
                Toast.makeText(
                    view.context,
                    "User update successfully!",
                    Toast.LENGTH_LONG,
                ).show()
            }
        } else {
            progressBar?.visibility = View.GONE
            Toast.makeText(
                view.context,
                "Error, try again later",
                Toast.LENGTH_LONG,
            ).show()
        }
    }
}