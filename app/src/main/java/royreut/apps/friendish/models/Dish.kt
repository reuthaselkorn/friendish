package royreut.apps.friendish.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Dish(
    @PrimaryKey val id:String,
    val name:String,
    val recipe:String,
    var isChecked:Boolean) {

    companion object {
        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val RECIPE_KEY = "recipe"
        const val IS_CHECKED_KEY = "isCheckes"

        fun fromJSON(json:Map<String, Any>):Dish {
            val id = json.get(ID_KEY) as? String ?: ""
            val name = json.get(NAME_KEY) as? String ?: ""
            val recipe = json.get(RECIPE_KEY) as? String?: ""
            val isChecked = json.get(IS_CHECKED_KEY) as? Boolean?: false

            return Dish(id, name, recipe, isChecked)
        }
    }

    val json: Map<String, Any> get() {
        return hashMapOf(
            "id" to id,
            "name" to name,
            "recipe" to recipe,
            "isChecked" to isChecked,
        )

    }
}