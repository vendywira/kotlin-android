package app.learn.kotlin.model

enum class ConstantEnum(name: String) {
    EXTRA_CONTENT("extra-content"),
    FAILED_GET_DATA("FAILED_GET_DATA")
}

object Constant {
    const val MATCH_PREV_MATCH = "MATCH_PREV_MATCH"
    const val MATCH_NEXT_MATCH = "MATCH_NEXT_MATCH"
}