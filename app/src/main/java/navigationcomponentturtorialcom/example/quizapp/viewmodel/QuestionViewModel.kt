package navigationcomponentturtorialcom.example.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import navigationcomponentturtorialcom.example.quizapp.model.QuestionModel
import navigationcomponentturtorialcom.example.quizapp.repository.QuestionRepository


class QuestionViewModel : ViewModel(), QuestionRepository.OnQuestionLoad {
    private val _questionMutableLiveData = MutableLiveData<List<QuestionModel>>()
    val questionMutableLiveData: MutableLiveData<List<QuestionModel>> get() = _questionMutableLiveData

    private val repository = QuestionRepository(this)

    fun setQuestionId(questionId: String) {
        repository.setQuestionId(questionId)
    }
    override fun onLoad(questionModels: List<QuestionModel>?) {
        _questionMutableLiveData.setValue(questionModels!!)
    }

    override fun onError(e: Exception?) {
        Log.d("QuizError", "on Error:" + e!!.message)
    }
}