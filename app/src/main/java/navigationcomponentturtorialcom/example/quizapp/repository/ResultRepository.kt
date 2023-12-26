package navigationcomponentturtorialcom.example.quizapp.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import navigationcomponentturtorialcom.example.quizapp.model.ResultModel

class ResultRepository {
    private val firebaseDatabase = FirebaseFirestore.getInstance()
    private var resultsRef = firebaseDatabase.collection("Result")

    fun getResult(callback: (ResultModel?) -> Unit) {
        resultsRef.document("LA")
            .get()
            .addOnSuccessListener { document ->
                Log.d("FirestoreCheck", "DocumentSnapshot data: ${document.data}")
                val resultModel = document.toObject(ResultModel::class.java)
                callback(resultModel)
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreCheck", "get failed with ", exception)
                callback(null)
            }
    }

    fun updateResult(resultModel: ResultModel) {
        val correctNumber = resultModel.correctAnswer
        val wrongNumber = resultModel.wrongAnswer

        val data = hashMapOf(
            "correctAnswer" to correctNumber,
            "wrongAnswer" to wrongNumber
        )

        resultsRef.document("LA")
            .set(data)
            .addOnSuccessListener { Log.d("WriteData", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("WriteData", "Error writing document", e) }
    }
}