package navigationcomponentturtorialcom.example.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import navigationcomponentturtorialcom.example.quizapp.model.ResultModel
import navigationcomponentturtorialcom.example.quizapp.repository.ResultRepository

class ResultViewModel(private val repository: ResultRepository) : ViewModel() {
    private var _resultMutableLiveData = MutableLiveData<ResultModel>()
    val resultMutableLiveData get() = _resultMutableLiveData

    val wrongAnswer: Int
        get() = _resultMutableLiveData.value?.wrongAnswer ?: 0

    val correctAnswer: Int
        get() = _resultMutableLiveData.value?.correctAnswer ?: 0

    fun getResult() {
        repository.getResult { resultModel ->
            _resultMutableLiveData.value = resultModel
            Log.d("ResultViewModel","Correct Answer: ${correctAnswer}, Wrong Answer:" +
                    " ${wrongAnswer}")
        }
    }

    fun updateResult(resultModel: ResultModel) {
        repository.updateResult(resultModel)
    }
}