package royreut.apps.friendish.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dish(
    @PrimaryKey val name:String,
    val recipe:String,
    var isChecked:Boolean) {
}