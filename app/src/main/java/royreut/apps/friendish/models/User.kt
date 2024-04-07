package royreut.apps.friendish.models

import androidx.room.PrimaryKey

class User (@PrimaryKey val email:String,
    val password:String){
}