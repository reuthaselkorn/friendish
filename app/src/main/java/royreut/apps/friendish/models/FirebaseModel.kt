package royreut.apps.friendish.models

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseModel {
    private val db = Firebase.firestore

    fun getAllDishes(callback: (List<Dish>) -> Unit) {

    }

    fun addDish(dish: Dish, callback: () -> Unit) {
    }

}

//// Create a new user with a first and last name
//val user = hashMapOf(
//    "first" to "Ada",
//    "last" to "Lovelace",
//    "born" to 1815,
//)
//
//// Add a new document with a generated ID
//db.collection("users")
//.add(user)
//.addOnSuccessListener { documentReference ->
//    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
//}
//.addOnFailureListener { e ->
//    Log.w("TAG", "Error adding document", e)
//}
//}