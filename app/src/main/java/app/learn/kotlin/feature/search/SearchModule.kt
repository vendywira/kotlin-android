package app.learn.kotlin.feature.search

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class SearchModule {

    @ActivityScope
    @Binds
    abstract fun provideSearchView(searchActivity: SearchActivity): SearchContract.View

//    @ActivityScope
//    @Binds
//    abstract fun provideSearchEvent(searchEventFragment: SearchEventFragment): SearchEventContract
//
//    @ActivityScope
//    @Binds
//    abstract fun provideSearchTeam(searchTeamFragment: SearchTeamFragment): SearchTeamContract

    @ActivityScope
    @Binds
    abstract fun provideSearchPresenter(searchPresenterImpl: SearchPresenterImpl): SearchContract.Presenter
}