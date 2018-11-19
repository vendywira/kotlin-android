package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.di.scope.ActivityScope
import app.learn.kotlin.feature.team.detail.TeamDetailActivity
import dagger.Binds
import dagger.Module

@Module
abstract class TeamDetailModule {

    @ActivityScope
    @Binds
    abstract fun provideTeamDetailView(teamDetailActivity: TeamDetailActivity): TeamDetailContract.View

    @ActivityScope
    @Binds
    abstract fun provideTeamPresenter(teamDetailPresenterImpl: TeamDetailPresenterImpl): TeamDetailContract.Presenter
}