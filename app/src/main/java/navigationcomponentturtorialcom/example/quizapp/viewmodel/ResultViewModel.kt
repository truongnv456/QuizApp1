package navigationcomponentturtorialcom.example.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import navigationcomponentturtorialcom.example.quizapp.model.ResultModel
import navigationcomponentturtorialcom.example.quizapp.repository.ResultRepository

class ResultViewModel(private val repository: ResultRepository) : ViewModel() {
    fun updateResults(resultModel: ResultModel) {
        repository.updateResults(resultModel)
    }
}