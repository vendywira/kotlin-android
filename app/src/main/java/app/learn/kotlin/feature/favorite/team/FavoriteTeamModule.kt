package app.learn.kotlin.feature.favorite.team

import app.learn.kotlin.di.scope.ActivityScope
import app.learn.kotlin.feature.favorite.event.FavoriteTeamContract
import dagger.Binds
import dagger.Module

@Module
abstract class FavoriteTeamModule {

    @ActivityScope
    @Binds
    abstract fun provideFavoriteTeamFragment(favoriteTeamFragment: FavoriteTeamFragment): FavoriteTeamContract.View

    @ActivityScope
    @Binds
    abstract fun provideFavoriteTeamPresenter(favoriteTeamPresenterImpl: FavoriteTeamPresenterImpl): FavoriteTeamContract.Presenter
}