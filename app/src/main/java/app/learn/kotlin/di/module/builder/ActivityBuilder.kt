package app.learn.kotlin.di.module.builder

import app.learn.kotlin.di.scope.ActivityScope
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.feature.event.detail.MatchDetailModule
import app.learn.kotlin.feature.event.match.MatchFragment
import app.learn.kotlin.feature.event.match.MatchModule
import app.learn.kotlin.feature.favorite.event.FavoriteEventFragment
import app.learn.kotlin.feature.favorite.event.FavoriteEventModule
import app.learn.kotlin.feature.favorite.event.FavoriteTeamFragment
import app.learn.kotlin.feature.favorite.team.FavoriteTeamModule
import app.learn.kotlin.feature.search.SearchActivity
import app.learn.kotlin.feature.search.event.SearchEventFragment
import app.learn.kotlin.feature.search.event.SearchEventModule
import app.learn.kotlin.feature.search.SearchModule
import app.learn.kotlin.feature.search.team.SearchTeamFragment
import app.learn.kotlin.feature.search.team.SearchTeamModule
import app.learn.kotlin.feature.team.list.ListTeamFragment
import app.learn.kotlin.feature.team.list.ListTeamModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ListTeamModule::class])
    internal abstract fun provideMainActivity() : ListTeamFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [MatchModule::class])
    abstract fun provideMatchFragment(): MatchFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [MatchDetailModule::class])
    abstract fun provideMatchDetailActvity(): MatchDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun provideSearchActivity(): SearchActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchEventModule::class])
    abstract fun provideSearchEvent(): SearchEventFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchTeamModule::class])
    abstract fun provideSearchTeam(): SearchTeamFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [FavoriteTeamModule::class])
    abstract fun provideFavoriteTeam(): FavoriteTeamFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [FavoriteTeamModule::class, FavoriteEventModule::class])
    abstract fun provideFavoriteEvent(): FavoriteEventFragment
}