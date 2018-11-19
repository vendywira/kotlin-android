package app.learn.kotlin.feature.favourite.team

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class FavouriteTeamModule {

    @ActivityScope
    @Binds
    abstract fun provideFavoriteTeamFragment(favouriteTeamFragment: FavouriteTeamFragment): FavouriteTeamContract.View

    @ActivityScope
    @Binds
    abstract fun provideFavoriteTeamPresenter(favouriteTeamPresenterImpl: FavouriteTeamPresenterImpl): FavouriteTeamContract.Presenter
}