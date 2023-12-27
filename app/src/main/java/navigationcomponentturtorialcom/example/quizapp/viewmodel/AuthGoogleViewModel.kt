package navigationcomponentturtorialcom.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import navigationcomponentturtorialcom.example.quizapp.repository.AuthGoogleRepository

class AuthGoogleViewModel(private val repository: AuthGoogleRepository) : ViewModel() {
    private val _authenticatedUserLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val authenticatedUserLiveData: LiveData<Boolean> get() = _authenticatedUserLiveData

    private var _errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val errorMessageLiveData: LiveData<String> get() = _errorMessageLiveData

    fun signInWithGoogle(idToken: String) {
        repository.signInWithGoogle(idToken,
            onComplete = { isSuccess ->
                _authenticatedUserLiveData.postValue(isSuccess)
            },
            onError = { errorMessage ->
                // Khi đăng nhập thất bại
                _errorMessageLiveData.postValue(errorMessage)
                // Xử lý lỗi nếu cần
            })
    }
}