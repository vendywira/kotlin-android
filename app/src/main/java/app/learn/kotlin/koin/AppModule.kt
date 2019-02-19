package app.learn.kotlin.koin

import app.learn.kotlin.feature.base.BaseIdleListener
import app.learn.kotlin.feature.base.BaseIdleResource
import app.learn.kotlin.feature.event.detail.MatchDetailPresenterImpl
import app.learn.kotlin.feature.event.match.MatchPresenterImpl
import app.learn.kotlin.feature.team.list.TeamListPresenterImpl
import com.google.gson.Gson
import org.koin.dsl.module.module

val appModule = module {
    single { Gson() }
    single<BaseIdleListener> { BaseIdleResource }
    factory { MatchPresenterImpl(get(), get()) }
    factory { MatchDetailPresenterImpl(get(), get(), get()) }
    factory { TeamListPresenterImpl(get(), get()) }
}