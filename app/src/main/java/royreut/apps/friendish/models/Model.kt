package royreut.apps.friendish.models

class Model private constructor() {

    val dishes: MutableList<Dish> = ArrayList()

    companion object {
        val instance: Model = Model()
    }

    init {
        for (i in 0..20) {
            val dish = Dish("dish$i","recipe$i", false)
            dishes.add(dish)
        }
    }
}