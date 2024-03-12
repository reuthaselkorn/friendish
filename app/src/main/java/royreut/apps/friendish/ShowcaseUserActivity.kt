package royreut.apps.friendish

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ShowcaseUserActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcase_user)
        val extras = intent.extras

        val nameTextView: TextView? = findViewById(R.id.nameTextView)
        val emailTextView: TextView? = findViewById(R.id.emailTextView)

        nameTextView?.text = extras?.getString("name")
        emailTextView?.text = extras?.getString("email")
    }
}