package navigationcomponentturtorialcom.example.quizapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import navigationcomponentturtorialcom.example.quizapp.repository.AuthRepository

class AuthViewModel(application: Application) : AndroidViewModel(application) { // chứa ngữ cảnh của ứng dụng
    private var repository: AuthRepository = AuthRepository(application)

    var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser> = repository.firebaseUserMutableLiveData
    var currentUser: FirebaseUser? = repository.currentUser

    fun signUp(email: String?, password: String?) {
        repository.signUp(email, password)
    }

    fun signIn(email: String?, password: String?) {
        repository.signIn(email, password)
    }

    fun signOut() {
        repository.signOut()
    }
}