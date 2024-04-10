package royreut.apps.friendish.models

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.ktx.Firebase

class FirebaseModel {
    private val db = Firebase.firestore

    companion object {
        const val DISHES_COLLECTION_PATH = "dishes"
        const val USERS_COLLECTION_PATH = "users"
    }

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {

            })
            setLocalCacheSettings(persistentCacheSettings {  })
        }
        db.firestoreSettings = settings
    }

    fun getAllDishes(since:Long, callback: (List<Dish>) -> Unit) {
        db.collection(DISHES_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(Dish.LAST_UPDATED, Timestamp(since,0))
            .get()
            .addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val dishes: MutableList<Dish> = mutableListOf()
                    for (json in it.result) {
                        dishes.add(Dish.fromJSON(json.data))
                    }
                    callback(dishes)
                } false -> callback(listOf())
            }
        }
    }

    fun getAllUserDishes(userEmail:String,since:Long, callback: (List<Dish>) -> Unit) {
        db.collection(DISHES_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(Dish.LAST_UPDATED, Timestamp(since,0))
            .whereEqualTo("author", userEmail)
            .get()
            .addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val dishes: MutableList<Dish> = mutableListOf()
                        for (json in it.result) {
                            dishes.add(Dish.fromJSON(json.data))
                        }
                        callback(dishes)
                    } false -> callback(listOf())
                }
            }
    }

    fun addDish(dish: Dish, callback: () -> Unit) {
        db.collection(DISHES_COLLECTION_PATH)
            .document(dish.id)
            .set(dish.json)
            .addOnSuccessListener { callback() }
            .addOnFailureListener{
                Log.e("add dish", "blah", it)
            }
    }

    fun addUser(user: User, callback: () -> Unit) {
        db.collection(USERS_COLLECTION_PATH)
            .document(user.id)
            .set(user.json)
            .addOnSuccessListener { callback() }
            .addOnFailureListener{
                Log.e("add user", "blah", it)
            }
    }

    fun getUserByEmail(email:String, callback: (User) -> Unit) {
        db.collection(USERS_COLLECTION_PATH)
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    callback(User.fromJSON(it.result.first().data))
                }
            }.addOnFailureListener{
                Log.e("getUser", "blah", it)
            }
    }

}