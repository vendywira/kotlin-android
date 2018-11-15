package app.learn.kotlin.di.module.builder

import app.learn.kotlin.di.scope.ActivityScope
import app.learn.kotlin.feature.event.detail.MatchDetailActivity
import app.learn.kotlin.feature.event.detail.MatchDetailModule
import app.learn.kotlin.feature.event.detail.TeamDetailModule
import app.learn.kotlin.feature.event.match.MatchFragment
import app.learn.kotlin.feature.event.match.MatchModule
import app.learn.kotlin.feature.favorite.event.FavoriteEventFragment
import app.learn.kotlin.feature.favorite.event.FavoriteEventModule
import app.learn.kotlin.feature.favorite.team.FavoriteTeamFragment
import app.learn.kotlin.feature.favorite.team.FavoriteTeamModule
import app.learn.kotlin.feature.search.SearchActivity
import app.learn.kotlin.feature.search.event.SearchEventFragment
import app.learn.kotlin.feature.search.event.SearchEventModule
import app.learn.kotlin.feature.search.SearchModule
import app.learn.kotlin.feature.search.team.SearchTeamFragment
import app.learn.kotlin.feature.search.team.SearchTeamModule
import app.learn.kotlin.feature.team.detail.TeamDetailActivity
import app.learn.kotlin.feature.team.list.TeamListFragment
import app.learn.kotlin.feature.team.list.TeamListModule
import app.learn.kotlin.feature.team.player.PlayerListFragment
import app.learn.kotlin.feature.team.player.PlayerListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [TeamListModule::class])
    internal abstract fun provideTeamListActivity() : TeamListFragment

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
    @ContributesAndroidInjector(modules = [FavoriteEventModule::class])
    abstract fun provideFavoriteEvent(): FavoriteEventFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [TeamDetailModule::class])
    abstract fun provideTeamDetailActivity(): TeamDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PlayerListModule::class])
    abstract fun providePlayerListFragment(): PlayerListFragment

}