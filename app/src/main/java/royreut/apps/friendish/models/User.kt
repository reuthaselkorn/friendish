package royreut.apps.friendish.models

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import royreut.apps.friendish.base.MyApplication

@Entity
class User (
    val id:String,
    @PrimaryKey val email:String,
    var imageUrl: String,
    var lastUpdated:Long? = null){

    companion object {
        const val ID_KEY = "id"
        const val EMAIL_KEY = "email"
        const val IMAGE_URL_KEY = "imageUrl"

        var lastUpdated:Long
            get() {
                return MyApplication
                    .Globals
                    .appContext
                    ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                    ?.getLong(GET_LAST_UPDATED,0) ?:0
            }
            set (value) {
                MyApplication
                    .Globals
                    ?.appContext
                    ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                    ?.edit()
                    ?.putLong(GET_LAST_UPDATED, value)?.apply()
            }
        const val LAST_UPDATED:String = "lastUpdated"
        const val GET_LAST_UPDATED:String = "get_last_updated"

        fun fromJSON(json:Map<String, Any>):User {
            val id = json.get(ID_KEY) as? String ?: ""
            val email = json.get(EMAIL_KEY) as? String ?: ""
            val imageUrl = json[IMAGE_URL_KEY] as? String?: "https://upload.wikimedia.org/wikipedia/commons/6/6e/Golde33443.jpg"
            val user = User(id,email, imageUrl)

            val timestamp: Timestamp? = json[LAST_UPDATED] as? Timestamp
            timestamp?.let {
                user.lastUpdated = it.seconds
            }

            return user
        }
    }

    val json: HashMap<String, Any?>
        get() {
        return hashMapOf(
            ID_KEY to id,
            EMAIL_KEY to email,
            IMAGE_URL_KEY to imageUrl,
            LAST_UPDATED to FieldValue.serverTimestamp()
        )
    }
}