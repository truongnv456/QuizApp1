package navigationcomponentturtorialcom.example.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import navigationcomponentturtorialcom.example.quizapp.repository.QuestionRepository

class QuestionViewModelFactory(private val questionRepository: QuestionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            return QuestionViewModel(questionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}