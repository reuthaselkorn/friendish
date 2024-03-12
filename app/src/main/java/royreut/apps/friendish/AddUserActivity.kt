package royreut.apps.friendish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText

class AddUserActivity : AppCompatActivity() {
    var nameTextField: EditText? = null
    var emailTextField: EditText? = null
    var saveButton: Button? = null
    var cancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        setupUI()
    }

    private fun setupUI() {
        nameTextField = findViewById(R.id.editUserName)
        emailTextField = findViewById(R.id.editUserEmail)
        saveButton = findViewById(R.id.saveButton)
        cancelButton = findViewById(R.id.cancelButton)

        cancelButton?.setOnClickListener { finish() }
        saveButton?.setOnClickListener {
            val myIntent = Intent(this@AddUserActivity, ShowcaseUserActivity::class.java)

            myIntent.putExtra("name", nameTextField?.text.toString());
            myIntent.putExtra("email", emailTextField?.text.toString());

            this@AddUserActivity.startActivity(myIntent)
        }
    }
}
