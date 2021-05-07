package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlow = MutableStateFlow(Fact.STUB)
    val catsStateFlow: StateFlow<Fact> = _catsStateFlow

    private val _errorStateFlow = MutableStateFlow("")
    val errorStateFlow: StateFlow<String> = _errorStateFlow

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().collect {
                when (it) {
                    is Result.Success -> _catsStateFlow.emit(it.value)
                    is Result.Error -> _errorStateFlow.emit(it.e.message.orEmpty())
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}