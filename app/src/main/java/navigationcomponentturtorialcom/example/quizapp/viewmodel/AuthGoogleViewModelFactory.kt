package navigationcomponentturtorialcom.example.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import navigationcomponentturtorialcom.example.quizapp.repository.AuthGoogleRepository

class AuthGoogleViewModelFactory(private val authGoogleRepository: AuthGoogleRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthGoogleViewModel::class.java)) {
            return AuthGoogleViewModel(authGoogleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}