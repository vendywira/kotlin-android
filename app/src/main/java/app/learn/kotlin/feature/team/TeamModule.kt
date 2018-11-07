package app.learn.kotlin.feature.team

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class TeamModule {

    @ActivityScope
    @Binds
    internal abstract fun provideMainView(teamFragment : TeamFragment) : TeamContract.View

    @ActivityScope
    @Binds
    internal abstract fun provideMainPresenter(teamPresenterImpl: TeamPresenterImpl) : TeamContract.Presenter

}