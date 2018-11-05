package app.learn.kotlin.feature.team

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class MainModule {

    @ActivityScope
    @Binds
    internal abstract fun provideMainView(mainActivity : MainActivity) : MainView

    @ActivityScope
    @Binds
    internal abstract fun provideMainPresenter(mainPresenterImpl: MainPresenterImpl) : MainPresenter

}