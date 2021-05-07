package otus.homework.flowcats

sealed class Result<out R> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val e: Exception) : Result<Nothing>()
}