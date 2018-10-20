package app.learn.kotlin.network

import app.learn.kotlin.model.LeagueResponse
import app.learn.kotlin.model.TeamResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportDBApiService {

    @GET("api/v1/json/1/all_leagues.php")
    fun getAllLeagues(): Observable<LeagueResponse>

    @GET("api/v1/json/1/search_all_teams.php")
    fun getAllTeams(@Query("l") league: String) : Observable<TeamResponse>
}
