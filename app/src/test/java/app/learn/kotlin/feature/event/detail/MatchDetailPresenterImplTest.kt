package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team
import app.learn.kotlin.network.TheSportDBApiService
import app.learn.kotlin.repository.FavoriteMatchRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MatchDetailPresenterImplTest {

    @InjectMocks
    private lateinit var impl: MatchDetailPresenterImpl

    @Spy
    private lateinit var view: MatchDetailView

    @Spy
    private lateinit var apiService: TheSportDBApiService

    @Spy
    private lateinit var favoriteRepository: FavoriteMatchRepository

    private lateinit var favoriteEventEntity: FavoriteEventEntity
    private lateinit var event: Event
    private lateinit var listEventResponse: ListResponse<Event>
    private lateinit var responseEvents: Observable<ListResponse<Event>>

    private lateinit var teamHome: Team
    private lateinit var teamAway: Team

    private val EVENT_ID = "EVENT_ID"
    private val ID_HOME_TEAM = "ID_HOME_TEAM"
    private val ID_AWAY_TEAM = "ID_AWAY_TEAM"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        favoriteEventEntity = FavoriteEventEntity()
        event = Event(eventId = EVENT_ID, idHomeTeam = ID_HOME_TEAM, idAwayTeam = ID_AWAY_TEAM)
        listEventResponse = ListResponse(mutableListOf(event))
        responseEvents = Observable.just(listEventResponse)
        teamHome = Team(id = ID_HOME_TEAM)
        teamAway = Team(id = ID_AWAY_TEAM)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(view, apiService, favoriteRepository)
    }

    @Test
    fun insertMatchToFavoriteTest_valid_true() {
        Mockito.`when`(favoriteRepository.insertEvent(favoriteEventEntity)).thenReturn(Single.just(true))

        impl.insertMatchToFavorite(favoriteEventEntity)

        Mockito.verify(favoriteRepository).insertEvent(favoriteEventEntity)
        Mockito.verify(view).showLoading()
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).showMessage("Added to favorite")
    }

    @Test
    fun insertMatchToFavoriteTest_valid_exception() {
        Mockito.`when`(favoriteRepository.insertEvent(favoriteEventEntity))
                .thenReturn(Single.error(Exception()))

        impl.insertMatchToFavorite(favoriteEventEntity)

        Mockito.verify(favoriteRepository).insertEvent(favoriteEventEntity)
        Mockito.verify(view).showLoading()
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).showMessage("Failed add to favorite")
    }

    @Test
    fun deleteMatchFromFavoriteTest_valid_success() {
        `when`(favoriteRepository.deleteEvent(EVENT_ID)).thenReturn(Single.just(true))

        impl.deleteMatchFromFavorite(EVENT_ID)

        verify(favoriteRepository).deleteEvent(EVENT_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMessage("Removed from favorite")
    }

    @Test
    fun deleteMatchFromFavoriteTest_valid_failed() {
        `when`(favoriteRepository.deleteEvent(EVENT_ID))
                .thenReturn(Single.error(Exception()))

        impl.deleteMatchFromFavorite(EVENT_ID)

        verify(favoriteRepository).deleteEvent(EVENT_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMessage("Failed removed from favorite")
    }

    @Test
    fun isExistFavoriteEventTest_valid_true() {
        `when`(favoriteRepository.isExistEvent(EVENT_ID)).thenReturn(Single.just(true))

        impl.isExistFavoriteEvent(EVENT_ID)

        verify(favoriteRepository).isExistEvent(EVENT_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).isExistFavoriteEvent(true)
    }

    @Test
    fun isExistFavoriteEventTest_valid_false() {
        `when`(favoriteRepository.isExistEvent(EVENT_ID)).thenReturn(Single.just(false))

        impl.isExistFavoriteEvent(EVENT_ID)

        verify(favoriteRepository).isExistEvent(EVENT_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).isExistFavoriteEvent(false)
    }

    @Test
    fun isExistFavoriteEventTest_valid_exception() {
        `when`(favoriteRepository.isExistEvent(EVENT_ID)).thenReturn(Single.error(Exception()))

        impl.isExistFavoriteEvent(EVENT_ID)

        verify(favoriteRepository).isExistEvent(EVENT_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMessage("failed to get data from db")
    }

    @Test
    fun getDetailEventTest_valid_success() {
        `when`(view.getEventId()).thenReturn(EVENT_ID)
        `when`(apiService.getEventByEventId(EVENT_ID)).thenReturn(responseEvents)
        `when`(apiService.getTeamByTeamId(ID_HOME_TEAM))
                .thenReturn(Observable.just(ListResponse(mutableListOf(teamHome))))
        `when`(apiService.getTeamByTeamId(ID_AWAY_TEAM))
                .thenReturn(Observable.just(ListResponse(mutableListOf(teamAway))))

        impl.getDetailEvent()

        verify(view).getEventId()
        verify(apiService).getEventByEventId(EVENT_ID)
        verify(apiService).getTeamByTeamId(ID_HOME_TEAM)
        verify(apiService).getTeamByTeamId(ID_AWAY_TEAM)
        verify(view).setTeamDetailModel(teamHome)
        verify(view).setTeamDetailModel(teamAway)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).setEventDetailModel(event)
    }
}