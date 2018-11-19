package app.learn.kotlin.di.module

import android.app.Application
import android.content.Context
import app.learn.kotlin.di.scope.ApplicationContext
import app.learn.kotlin.di.scope.ApplicationScope
import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BaseIdleResource
import app.learn.kotlin.network.NetworkModule
import app.learn.kotlin.repository.RepositoryModule
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module(includes = [NetworkModule::class, RepositoryModule::class])
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

    @Provides
    @Reusable
    internal fun provideBaseIdleResource(): BaseIdleListener = BaseIdleResource
}