package app.learn.kotlin.koin

import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BaseIdleResource
import app.learn.kotlin.feature.event.detail.MatchDetailPresenterImpl
import app.learn.kotlin.feature.event.detail.TeamDetailPresenterImpl
import app.learn.kotlin.feature.event.match.MatchPresenterImpl
import app.learn.kotlin.feature.favourite.event.FavouriteEventPresenterImpl
import app.learn.kotlin.feature.favourite.team.FavouriteTeamPresenterImpl
import app.learn.kotlin.feature.search.event.SearchEventPresenterImpl
import app.learn.kotlin.feature.search.team.SearchTeamPresenterImpl
import app.learn.kotlin.feature.team.list.TeamListPresenterImpl
import app.learn.kotlin.feature.team.player.PlayerListPresenterImpl
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers.single
import org.koin.dsl.module.module

val appModule = module {
    single { Gson() }
    single<BaseIdleListener> { BaseIdleResource }
    factory { MatchPresenterImpl(get(), get()) }
    factory { MatchDetailPresenterImpl(get(), get(), get()) }
    factory { TeamListPresenterImpl(get(), get()) }
    factory { TeamDetailPresenterImpl(get(), get()) }
    factory { PlayerListPresenterImpl(get(), get()) }
    factory { SearchTeamPresenterImpl(get(), get()) }
    factory { SearchEventPresenterImpl(get(), get()) }
    factory { FavouriteTeamPresenterImpl(get(), get()) }
    factory { FavouriteEventPresenterImpl(get(), get()) }
}