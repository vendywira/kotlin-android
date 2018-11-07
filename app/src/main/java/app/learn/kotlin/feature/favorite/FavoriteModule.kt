package app.learn.kotlin.feature.favorite

import app.learn.kotlin.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class FavoriteModule {

    @ActivityScope
    @Binds
    abstract fun provideFavoriteView(favoriteFragment: FavoriteFragment): FavoriteContract.View

    @ActivityScope
    @Binds
    abstract fun provideFavoritePresenter(favoritePresenterImpl: FavoritePresenterImpl): FavoriteContract.Presenter
}