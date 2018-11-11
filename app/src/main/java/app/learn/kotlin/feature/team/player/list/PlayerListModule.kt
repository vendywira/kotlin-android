package app.learn.kotlin.feature.team.player.list

import app.learn.kotlin.di.scope.ActivityScope
import app.learn.kotlin.feature.team.list.TeamListContract
import app.learn.kotlin.feature.team.list.TeamListFragment
import app.learn.kotlin.feature.team.list.TeamListPresenterImpl
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