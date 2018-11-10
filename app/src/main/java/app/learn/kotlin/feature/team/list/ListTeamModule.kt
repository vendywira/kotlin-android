package app.learn.kotlin.feature.team.list

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class ListTeamModule {

    @ActivityScope
    @Binds
    internal abstract fun provideMainView(listTeamFragment : ListTeamFragment) : ListTeamContract.View

    @ActivityScope
    @Binds
    internal abstract fun provideMainPresenter(listTeamPresenterImpl: ListTeamPresenterImpl) : ListTeamContract.Presenter

}