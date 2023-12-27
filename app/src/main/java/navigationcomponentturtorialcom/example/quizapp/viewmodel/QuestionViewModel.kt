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
    val currentQuestionIndex: LiveData<Int> get() = _currentQuestionIndex

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

    fun getCurrentQuestion(): QuestionModel {
        // Kiểm tra _questionMutableLiveData và _currentQuestionIndex có giá trị không null
//        requireNotNull(_questionMutableLiveData.value) { "Danh sách câu hỏi không được null." }
//        require(_questionMutableLiveData.value!!.isNotEmpty()) { "Danh sách câu hỏi không được trống." }
//        requireNotNull(_currentQuestionIndex.value) { "Chỉ số câu hỏi không được null." }

        // Trả về câu hỏi hiện tại từ danh sách
        return _questionMutableLiveData.value!![_currentQuestionIndex.value!!]
    }

}