package app.learn.kotlin.model.mapper

import app.learn.kotlin.model.entity.FavoriteEvent
import app.learn.kotlin.model.response.Event
import org.mapstruct.Mapper

@Mapper
interface ObjectMapper {

    fun convertTo(source: Event) : FavoriteEvent

//    fun <S,T> convertTo(source: S) : T

//    fun <S,T> convertListTo(sources: List<S>) : List<T>

}