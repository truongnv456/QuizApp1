package navigationcomponentturtorialcom.example.quizapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthGoogleRepository() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    fun signInWithGoogle(
        idToken: String,
        onComplete: (Boolean) -> Unit,
        onError: (String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(task.isSuccessful)
                } else {
                    onError("Authentication failed")
                }
            }
    }
}