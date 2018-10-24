package app.learn.kotlin.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Event(
        @field:SerializedName("intHomeShots")
        var homeShots: Int? = 0,

        @field:SerializedName("strSport")
        var sport: String? = null,

        @field:SerializedName("strHomeLineupDefense")
        var homeLineupDefense: String? = null,

        @field:SerializedName("strAwayLineupSubstitutes")
        var awayLineupSubstitutes: String? = null,

        @field:SerializedName("idLeague")
        var leagueId: String? = null,

        @field:SerializedName("idSoccerXML")
        var idSoccerXML: String? = null,

        @field:SerializedName("strHomeLineupForward")
        var homeLineupForward: String? = null,

        @field:SerializedName("strTVStation")
        var tVStation: String? = null,

        @field:SerializedName("strHomeGoalDetails")
        var homeGoalDetails: String? = null,

        @field:SerializedName("strAwayLineupGoalkeeper")
        var awayLineupGoalkeeper: String? = null,

        @field:SerializedName("strAwayLineupMidfield")
        var awayLineupMidfield: String? = null,

        @field:SerializedName("idEvent")
        var eventId: String? = null,

        @field:SerializedName("intRound")
        var round: Int? = 0,

        @field:SerializedName("strHomeYellowCards")
        var strHomeYellowCards: String? = null,

        @field:SerializedName("idHomeTeam")
        var idHomeTeam: String? = null,

        @field:SerializedName("intHomeScore")
        var teamHomeScore: Int? = 0,

        @field:SerializedName("dateEvent")
        var dateEvent: Date? = null,

        @field:SerializedName("strCountry")
        var country: String? = null,

        @field:SerializedName("strAwayTeam")
        var teamAwayName: String? = null,

        @field:SerializedName("strHomeLineupMidfield")
        var homeLineupMidfield: String? = null,

        @field:SerializedName("strDate")
        var strDate: String? = null,

        @field:SerializedName("strHomeFormation")
        var homeFormation: String? = null,

        @field:SerializedName("strMap")
        var map: String? = null,

        @field:SerializedName("idAwayTeam")
        var idAwayTeam: String? = null,

        @field:SerializedName("strAwayRedCards")
        var awayRedCards: String? = null,

        @field:SerializedName("strBanner")
        var banner: String? = null,

        @field:SerializedName("strFanart")
        var fanart: String? = null,

        @field:SerializedName("strDescriptionEN")
        var strDescriptionEN: String? = null,

        @field:SerializedName("strResult")
        var result: String? = null,

        @field:SerializedName("strCircuit")
        var circuit: String? = null,

        @field:SerializedName("intAwayShots")
        var awayShots: String? = null,

        @field:SerializedName("strFilename")
        var filename: String? = null,

        @field:SerializedName("strTime")
        var time: String? = null,

        @field:SerializedName("strAwayGoalDetails")
        var awayGoalDetails: String? = null,

        @field:SerializedName("strAwayLineupForward")
        var awayLineupForward: String? = null,

        @field:SerializedName("strLocked")
        var locked: String? = null,

        @field:SerializedName("strSeason")
        var season: String? = null,

        @field:SerializedName("intSpectators")
        var spectators: Int? = 0,

        @field:SerializedName("strHomeRedCards")
        var homeRedCards: String? = null,

        @field:SerializedName("strHomeLineupGoalkeeper")
        var homeLineupGoalkeeper: String? = null,

        @field:SerializedName("strHomeLineupSubstitutes")
        var homeLineupSubstitutes: String? = null,

        @field:SerializedName("strAwayFormation")
        var awayFormation: String? = null,

        @field:SerializedName("strEvent")
        var event: String? = null,

        @field:SerializedName("strAwayYellowCards")
        var awayYellowCards: String? = null,

        @field:SerializedName("strAwayLineupDefense")
        var awayLineupDefense: String? = null,

        @field:SerializedName("strHomeTeam")
        var teamHomeName: String? = null,

        @field:SerializedName("strThumb")
        var thumb: String? = null,

        @field:SerializedName("strLeague")
        var league: String? = null,

        @field:SerializedName("intAwayScore")
        var teamAwayScore: Int? = 0,

        @field:SerializedName("strCity")
        var strCity: String? = null,

        @field:SerializedName("strPoster")
        var strPoster: String? = null

) : Parcelable {
        constructor() : this(null, null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null)
}