package app.learn.kotlin.feature.event.match

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class MatchModule {

    @ActivityScope
    @Binds
    abstract fun provideMatchView(matchFragment: MatchFragment): MatchView

    @ActivityScope
    @Binds
    abstract fun provideMatchPresenter(matchPresenterImpl: MatchPresenterImpl): MatchPresenter
}