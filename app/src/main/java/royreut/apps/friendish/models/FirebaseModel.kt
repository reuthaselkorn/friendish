package royreut.apps.friendish.models

import android.util.Log
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.ktx.Firebase

class FirebaseModel {
    private val db = Firebase.firestore

    companion object {
        const val DISHES_COLLECTION_PATH = "dishes"
    }

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {

            })
            setLocalCacheSettings(persistentCacheSettings {  })
        }
        db.firestoreSettings = settings
    }

    fun getAllDishes(callback: (List<Dish>) -> Unit) {
        db.collection(DISHES_COLLECTION_PATH).get().addOnCompleteListener {
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
    }

}