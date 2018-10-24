package app.learn.kotlin.mvp.event.detail

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class MatchDetailModule {

    @ActivityScope
    @Binds
    abstract fun provideMatchDetailView(matchDetailFragment: MatchDetailActivity): MatchDetailView

    @ActivityScope
    @Binds
    abstract fun provideMatchPresenter(matchDetailPresenterImpl: MatchDetailPresenterImpl): MatchDetailPresenter
}