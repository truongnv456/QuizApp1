package navigationcomponentturtorialcom.example.quizapp.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository() {
    //    val firebaseUserMutableLiveData = MutableLiveData<FirebaseUser>()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(
        email: String,
        password: String,
        onComplete: (FirebaseUser?) -> Unit,
        onError: (String) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    onComplete(currentUser)
                } else {
                    onError("Authentication failed")
                }
            }
    }

    fun signIn(
        email: String,
        password: String,
        onComplete: (FirebaseUser?) -> Unit,
        onError: (String) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    onComplete(currentUser)
                } else {
                    onError("Authentication failed")
                }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}


