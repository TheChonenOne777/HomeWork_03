package otus.homework.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class SampleInteractor(
        private val sampleRepository: SampleRepository
) {

    /**
     * Реализуйте функцию task1 которая последовательно:
     * 1) возводит числа в 5ую степень
     * 2) убирает числа <= 20
     * 3) убирает четные числа
     * 4) добавляет постфикс "won"
     * 5) берет 3 первых числа
     * 6) возвращает результат
     */
    fun task1(): Flow<String> = sampleRepository.produceNumbers()
            .map { it * 5 }
//            .map { it.toDouble().pow(5.0) }
            .filter { it > 20 }
            .filter { it % 2 != 0 }
            .map { "$it won" }
            .take(3)

    /**
     * Классическая задача FizzBuzz с небольшим изменением.
     * Если входное число делится на 3 - эмитим само число и после него эмитим строку Fizz
     * Если входное число делится на 5 - эмитим само число и после него эмитим строку Buzz
     * Если входное число делится на 15 - эмитим само число и после него эмитим строку FizzBuzz
     * Если число не делится на 3,5,15 - эмитим само число
     */
    fun task2(): Flow<String> = sampleRepository.produceNumbers()
            .transform {
                emit("$it")
                when {
                    it % 15 == 0 -> emit("FizzBuzz")
                    it % 3 == 0 -> emit("Fizz")
                    it % 5 == 0 -> emit("Buzz")
                }
            }

    /**
     * Реализуйте функцию task3, которая объединяет эмиты из двух flow и возвращает кортеж Pair<String,String>(f1,f2),
     * где f1 айтем из первого флоу, f2 айтем из второго флоу.
     * Если айтемы в одном из флоу кончились, то результирующий флоу также должен закончиться
     */
    fun task3(): Flow<Pair<String, String>> = sampleRepository.produceColors()
            .zip(sampleRepository.produceForms()) { color, form -> Pair(color, form) }

    /**
     * Реализуйте функцию task4, которая обрабатывает IllegalArgumentException и в качестве фоллбека
     * эмитит число -1.
     * Если тип эксепшена != IllegalArgumentException, пробросьте его дальше
     * При любом исходе, будь то выброс исключения или успешная отработка функции, вызовите метод dotsRepository.completed()
     */
    fun task4(): Flow<Int> = sampleRepository.produceNumbers()
            .catch {
                if (it is IllegalArgumentException) emit(-1)
                else throw it
            }
            .onCompletion { sampleRepository.completed() }
}