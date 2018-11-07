package app.learn.kotlin.di.module.builder

import app.learn.kotlin.di.scope.ActivityScope
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.feature.event.detail.MatchDetailModule
import app.learn.kotlin.feature.event.match.MatchFragment
import app.learn.kotlin.feature.event.match.MatchModule
import app.learn.kotlin.feature.favorite.FavoriteFragment
import app.learn.kotlin.feature.favorite.FavoriteModule
import app.learn.kotlin.feature.search.SearchActivity
import app.learn.kotlin.feature.search.SearchModule
import app.learn.kotlin.feature.team.TeamFragment
import app.learn.kotlin.feature.team.TeamModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [TeamModule::class])
    internal abstract fun provideMainActivity() : TeamFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [MatchModule::class])
    abstract fun provideMatchFragment(): MatchFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [MatchDetailModule::class])
    abstract fun provideMatchDetailActvity(): MatchDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [FavoriteModule::class])
    abstract fun provideFavoriteFragment(): FavoriteFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun provideSearchActivity(): SearchActivity
}