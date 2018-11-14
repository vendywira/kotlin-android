package app.learn.kotlin.feature.team.player

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class PlayerListModule {


    @ActivityScope
    @Binds
    internal abstract fun provideMainView(playerListFragment: PlayerListFragment) : PlayerListContract.View

    @ActivityScope
    @Binds
    internal abstract fun provideMainPresenter(playerListPresenterImpl: PlayerListPresenterImpl) : PlayerListContract.Presenter
}