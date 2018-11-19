package app.learn.kotlin.di.component

import android.app.Application
import app.learn.kotlin.di.AppController
import app.learn.kotlin.di.module.AppModule
import app.learn.kotlin.di.module.builder.ActivityBuilder
import app.learn.kotlin.di.scope.ApplicationScope
import app.learn.kotlin.network.TheSportDBApiService
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule



@ApplicationScope
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuilder::class])
interface AppComponent : AndroidInjector<AppController> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun theSportDBApiService(): TheSportDBApiService
}