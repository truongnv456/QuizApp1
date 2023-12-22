package navigationcomponentturtorialcom.example.quizapp.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository(private var application: Application) {
    val firebaseUserMutableLiveData = MutableLiveData<FirebaseUser>()
    private val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    fun signUp(email: String?, password: String?): Boolean {
        if (email != null && password != null ) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUserMutableLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    Toast.makeText(application, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            return true
        }
        return false
    }

    fun signIn(email: String?, password: String?): Boolean {
        if (email != null && password != null) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUserMutableLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    Toast.makeText(application, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            return true
        }
        return false
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}


