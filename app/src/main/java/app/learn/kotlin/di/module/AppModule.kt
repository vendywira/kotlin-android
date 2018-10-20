package app.learn.kotlin.di.module

import android.app.Application
import android.content.Context
import app.learn.kotlin.di.scope.ApplicationContext
import app.learn.kotlin.di.scope.ApplicationScope
import app.learn.kotlin.network.NetworkModule
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(NetworkModule::class))
class AppModule {

    @ApplicationScope
    @Provides
    @ApplicationContext
    internal fun provideApplication(application: Application) : Application {
        return application
    }

    @ApplicationScope
    @Provides
    internal fun provideContext(@ApplicationContext application: Application) : Context {
        return application
    }
}