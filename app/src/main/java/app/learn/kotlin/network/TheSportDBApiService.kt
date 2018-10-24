package app.learn.kotlin.network

import app.learn.kotlin.model.EventResponse
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

    @GET("api/v1/json/1/eventspastleague.php")
    fun getLastMatchByLeagueId(@Query("id") leagueId: String): Observable<EventResponse>

    @GET("api/v1/json/1/eventsnextleague.php")
    fun getNextMatchByLeagueId(@Query("id") leagueId: String): Observable<EventResponse>

    @GET("api/v1/json/1/lookupevent.php")
    fun getEventByEventId(@Query("id") eventId: String): Observable<EventResponse>

    @GET("api/v1/json/1/lookupteam.php")
    fun getTeamByTeamId(@Query("id") teamId: String): Observable<TeamResponse>
}
