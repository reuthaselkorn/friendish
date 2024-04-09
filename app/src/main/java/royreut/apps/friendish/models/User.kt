package royreut.apps.friendish.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User (@PrimaryKey val email:String,
    var password:String? = ""){
}