package otus.homework.flowcats

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
        private val catsService: CatsService,
        private val refreshIntervalMs: Long = 5000,
        private val dispatcher: CoroutineDispatcher
) {

    fun listenForCatFacts() = flow {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
                delay(refreshIntervalMs)
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }.flowOn(dispatcher)
}