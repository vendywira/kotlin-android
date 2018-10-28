package app.learn.kotlin

import app.learn.kotlin.model.response.Event
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun objectMapperTest() {
        var event = Event()
        event.eventId = "eventId"
        event.teamHomeName = "teamHomeName"
        event.teamHomeScore = 5
        event.teamAwayScore = 3
        event.teamAwayName = "teamAwayName"

//        var converter = Mappers.getMapper(ObjectMapper::class.java)
//        var response = converter.convertEvent(event)

//        var result = converter.convertTo<Event, FavoriteEventEntity>(event)
//        print(response.toString())

    }
}
