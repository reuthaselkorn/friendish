package royreut.apps.friendish.models

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import royreut.apps.friendish.base.MyApplication

@Entity
data class Dish(
    @PrimaryKey val id:String,
    val name:String,
    val recipe:String,
    var isChecked:Boolean,
    var imageUrl: String,
    var lastUpdated:Long? = null) {

    companion object {
        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val RECIPE_KEY = "recipe"
        const val IS_CHECKED_KEY = "isChecked"
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

        fun fromJSON(json:Map<String, Any>):Dish {
            val id = json[ID_KEY] as? String ?: ""
            val name = json[NAME_KEY] as? String ?: ""
            val recipe = json[RECIPE_KEY] as? String?: ""
            val isChecked = json[IS_CHECKED_KEY] as? Boolean?: false
            val imageUrl = json[IMAGE_URL_KEY] as? String?: "https://upload.wikimedia.org/wikipedia/commons/6/6e/Golde33443.jpg"
            val dish = Dish(id, name, recipe, isChecked, imageUrl)

            val timestamp:Timestamp? = json[LAST_UPDATED] as? Timestamp
            timestamp?.let {
                dish.lastUpdated = it.seconds
            }

            return dish
        }
    }

    val json: HashMap<String, Any?>
        get() {
        return hashMapOf(
            ID_KEY to id,
            NAME_KEY to name,
            RECIPE_KEY to recipe,
            IS_CHECKED_KEY to isChecked,
            IMAGE_URL_KEY to imageUrl,
            LAST_UPDATED to FieldValue.serverTimestamp()
        )
    }
}