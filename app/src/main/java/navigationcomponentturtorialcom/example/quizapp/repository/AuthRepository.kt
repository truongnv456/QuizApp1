package navigationcomponentturtorialcom.example.quizapp.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth


class AuthRepository {
    private lateinit var currentUser: FirebaseUser
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String) {

    }

    fun signIn() {

    }

    fun signOut() {

    }
}


