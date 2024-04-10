package royreut.apps.friendish.models

import android.os.Looper
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import royreut.apps.friendish.base.MyApplication
import royreut.apps.friendish.dao.AppLocalDataBase
import java.util.concurrent.Executors
import java.util.stream.Collectors

class Model private constructor() {

    enum class LoadingState {
        LOADING,
        LOADED
    }

    private val database = AppLocalDataBase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = FirebaseModel()
    private val auth = Firebase.auth
    private val dishes:LiveData<MutableList<Dish>>? = null
    private val myDishes:LiveData<MutableList<Dish>>? = null

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

    fun refreshAllUserDishes(userEmail: String){
        val lastUpdated:Long =  Dish.userDishesLastUpdated
        firebaseModel.getAllUserDishes(userEmail,lastUpdated) {list ->
            executor.execute {
                var time = lastUpdated
                for (dish in list) {
                    database.dishDao().insert(dish)

                    dish.lastUpdated?.let {
                        if (time < it) {
                            time = dish.lastUpdated ?: System.currentTimeMillis()
                        }
                    }
                    Dish.userDishesLastUpdated = time
                }
            }
        }
    }

    fun getAllDishesOfUser(userEmail:String): LiveData<MutableList<Dish>>? {
        refreshAllUserDishes(userEmail)
        return myDishes ?: database.dishDao().getDishesByUser(userEmail)
    }

    fun addDish(dish: Dish, callback: () -> Unit) {
        firebaseModel.addDish(dish) {
            refreshAllDishes()
            callback()
        }
    }

    fun signupUser(email:String, password:String, callback: (Task<AuthResult>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                val user = it.result.user?.let { userResult -> User(userResult.uid, userResult.email ?: "", "") }
                if (user != null) {
                    firebaseModel.addUser(user) {
                        callback(it)
                    }
                }
            }
    }

    fun getUserByEmail(email: String) {
        firebaseModel.getUserByEmail(email) {
            MyApplication.Globals.user = it
        }
    }
}