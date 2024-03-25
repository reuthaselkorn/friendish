package royreut.apps.friendish.models

import android.os.Looper
import androidx.core.os.HandlerCompat
import royreut.apps.friendish.dao.AppLocalDataBase
import java.util.concurrent.Executors

class Model private constructor() {

    private val database = AppLocalDataBase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = FirebaseModel()

    companion object {
        val instance: Model = Model()
    }

    interface getAllDishesListener {
        fun onComplete(dishes:List<Dish>)
    }

    fun getAllDishes(callback: (List<Dish>) -> Unit){
        firebaseModel.getAllDishes(callback)
//        executor.execute {
//
//            Thread.sleep(5000)
//            val dishes = database.dishDao().getAll()
//
//            mainHandler.post{
//                // Main Thread
//                callback(dishes)
//            }
//        }
    }

    fun addDish(dish: Dish, callback: () -> Unit) {
        firebaseModel.addDish(dish, callback)
//        executor.execute {
//            database.dishDao().insert(dish)
//            mainHandler.post(callback)
//        }
    }
}