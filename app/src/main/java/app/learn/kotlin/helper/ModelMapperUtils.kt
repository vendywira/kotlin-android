package app.learn.kotlin.helper

import org.modelmapper.ModelMapper

object ModelMapperUtils {
    private var instance: ModelMapper? = null

    fun getInstance(): ModelMapper {
        if (ModelMapperUtils.instance == null) {
            ModelMapperUtils.instance = ModelMapper()
            instance?.configuration?.isSkipNullEnabled = true
            instance?.configuration?.isAmbiguityIgnored = true
        }
        return ModelMapperUtils.instance as ModelMapper
    }
}