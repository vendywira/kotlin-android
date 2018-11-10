package app.learn.kotlin.feature.favorite.event

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class FavoriteEventModule {

    @ActivityScope
    @Binds
    abstract fun provideFavoriteTeamFragment(favoriteEventFragment: FavoriteEventFragment): FavoriteEventContract.View

    @ActivityScope
    @Binds
    abstract fun provideFavoriteEventPresenter(favoriteEventPresenterImpl: FavoriteEventPresenterImpl): FavoriteEventContract.Presenter
}