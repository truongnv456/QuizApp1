package navigationcomponentturtorialcom.example.quizapp.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import navigationcomponentturtorialcom.example.quizapp.model.QuestionModel


class QuestionRepository() {
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val questionList = firebaseFirestore.collection("Question")

    fun getQuestions(callback: (List<QuestionModel>?) -> Unit) {
        questionList
            .get()
            .addOnSuccessListener { documents ->
            var questionList = mutableListOf<QuestionModel>()
            for (document in documents) {
                val questionModel = document.toObject(QuestionModel::class.java)
                questionList.add(questionModel)
                Log.d("GetQuestion", "On Success $questionList")
            }
            callback(questionList)
        }.addOnFailureListener {
            callback(null)
        }
    }
}
