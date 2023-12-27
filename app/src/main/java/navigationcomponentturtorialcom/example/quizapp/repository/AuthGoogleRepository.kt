package navigationcomponentturtorialcom.example.quizapp.repository

import com.google.firebase.auth.FirebaseAuth

class AuthGoogleRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

//    fun signInWithGoogle(): Task<AuthResult> {
//        val signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    // Your server's client ID, not your Android client ID.
//                    .setServerClientId(getString(R.string.your_web_client_id))
//                    // Only show accounts previously used to sign in.
//                    .setFilterByAuthorizedAccounts(true)
//                    .build())
//            .build()
//    }
//
//    fun signOut() {
//        auth.signOut()
//    }
}