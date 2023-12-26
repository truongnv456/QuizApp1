package navigationcomponentturtorialcom.example.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import navigationcomponentturtorialcom.example.quizapp.model.QuestionModel
import navigationcomponentturtorialcom.example.quizapp.repository.QuestionRepository


class QuestionViewModel(private val repository: QuestionRepository) : ViewModel() {
    private var _questionMutableLiveData = MutableLiveData<List<QuestionModel>>()
    val questionMutableLiveData: LiveData<List<QuestionModel>> get() = _questionMutableLiveData

    private val _currentQuestionIndex = MutableLiveData<Int>()
    val currentQuizIndex: LiveData<Int> get() = _currentQuestionIndex
    init {
        _currentQuestionIndex.value = 0 // Set the initial quiz index to start from the first quiz
        getQuestions()
    }

    fun getQuestions() {
        repository.getQuestions { questionList ->
            _questionMutableLiveData.postValue(questionList)
        }
    }

    fun setCurrentQuestionIndex(index: Int) {
        _currentQuestionIndex.value = index
    }

    fun getCurrentQuestion(): QuestionModel? {
        return if (_questionMutableLiveData.value != null && _questionMutableLiveData.value!!.isNotEmpty() && _currentQuestionIndex.value != null) {
            _questionMutableLiveData.value!![_currentQuestionIndex.value!!]
        } else {
            null
        }
    }
}