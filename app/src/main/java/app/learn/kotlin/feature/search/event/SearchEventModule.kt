package app.learn.kotlin.feature.search.event

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class SearchEventModule {

    @ActivityScope
    @Binds
    abstract fun provideSearchEvent(searchEventFragment: SearchEventFragment): SearchEventContract.View

    @ActivityScope
    @Binds
    abstract fun provideSearchPresenter(searchEventPresenterImpl: SearchEventPresenterImpl): SearchEventContract.Presenter
}