package navigationcomponentturtorialcom.example.quizapp.repository

import com.google.firebase.firestore.FirebaseFirestore
import navigationcomponentturtorialcom.example.quizapp.model.QuestionModel


class QuestionRepository(private val onQuestionLoad: OnQuestionLoad) {
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var questionId: String

    fun setQuestionId(questionId: String) {
        this.questionId = questionId
    }

    fun getQuestions() {
        firebaseFirestore.collection("Question").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onQuestionLoad.onLoad(task.result.toObjects(QuestionModel::class.java))
                } else {
                    onQuestionLoad.onError(task.exception)
                }
            }
    }
    interface OnQuestionLoad {
        fun onLoad(questionModels: List<QuestionModel>?)
        fun onError(e: Exception?)
    }
}
