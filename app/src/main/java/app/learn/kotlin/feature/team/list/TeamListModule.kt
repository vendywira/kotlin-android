package app.learn.kotlin.feature.team.list

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class TeamListModule {

    @ActivityScope
    @Binds
    internal abstract fun provideTeamListView(listTeamFragment : TeamListFragment) : TeamListContract.View

    @ActivityScope
    @Binds
    internal abstract fun provideTeamListPrenter(listTeamPresenterImpl: TeamListPresenterImpl) : TeamListContract.Presenter

}