package royreut.apps.friendish.models

import android.os.Looper
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import royreut.apps.friendish.dao.AppLocalDataBase
import java.util.concurrent.Executors

class Model private constructor() {

    enum class LoadingState {
        LOADING,
        LOADED
    }

    private val database = AppLocalDataBase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = FirebaseModel()
    private val dishes:LiveData<MutableList<Dish>>? = null

    val dishListLoadingState:MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)

    companion object {
        val instance: Model = Model()
    }

    interface getAllDishesListener {
        fun onComplete(dishes:List<Dish>)
    }

    fun getAllDishes():LiveData<MutableList<Dish>> {
        refreshAllDishes()
        return dishes ?: database.dishDao().getAll()
    }

    fun refreshAllDishes(){
        dishListLoadingState.value = LoadingState.LOADING

        val lastUpdated:Long =  Dish.lastUpdated
        firebaseModel.getAllDishes(lastUpdated) {list ->
            executor.execute {
                var time = lastUpdated
                for (dish in list) {
                    database.dishDao().insert(dish)

                    dish.lastUpdated?.let {
                        if (time < it) {
                            time = dish.lastUpdated ?: System.currentTimeMillis()
                        }
                    }
                    Dish.lastUpdated = time
                }
                dishListLoadingState.postValue(LoadingState.LOADED)
            }

        }

    }

    fun addDish(dish: Dish, callback: () -> Unit) {
        firebaseModel.addDish(dish) {
            refreshAllDishes()
            callback()
        }
    }
}