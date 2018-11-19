package app.learn.kotlin.feature.base

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource

object BaseIdleResource : BaseIdleListener {

    private const val RESOURCE = "FootballClub"
    private val idlingResource = CountingIdlingResource(RESOURCE)

    override fun getIdlingResource(): IdlingResource {
        return idlingResource
    }

    override fun increment() {
        idlingResource.increment()
    }

    override fun decrement() {
        idlingResource.decrement()
    }
}