package navigationcomponentturtorialcom.example.quizapp.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import navigationcomponentturtorialcom.example.quizapp.R
import navigationcomponentturtorialcom.example.quizapp.repository.AuthGoogleRepository
import navigationcomponentturtorialcom.example.quizapp.repository.AuthRepository
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthGoogleViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthGoogleViewModelFactory
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModelFactory


class SignInFragment : Fragment() {
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepository())
    }

    private val googleViewModel by viewModels<AuthGoogleViewModel> {
        AuthGoogleViewModelFactory(AuthGoogleRepository())
    }

    private lateinit var navController: NavController
    private lateinit var btnSignIn: Button
    private lateinit var btnGoogle: SignInButton
    private lateinit var tvSignUp: TextView
    private var etEmailLogin: TextInputEditText? = null
    private var etPasswordLogin: TextInputEditText? = null
    private val RC_SIGN_IN = 9001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etEmailLogin = view.findViewById<TextInputEditText>(R.id.etEmailLogin)
        etPasswordLogin = view.findViewById<TextInputEditText>(R.id.etPasswordLogin)
        btnSignIn = view.findViewById<Button>(R.id.btnSignIn)
        tvSignUp = view.findViewById<TextView>(R.id.tvSignUp)
        btnGoogle = view.findViewById<SignInButton>(R.id.btnGoogle)
        navController = Navigation.findNavController(view)

        tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        btnSignIn.setOnClickListener {
            val emailLogin = etEmailLogin!!.text.toString()
            val passwordLogin: String = etPasswordLogin!!.text.toString()

            if (emailLogin.isNotEmpty() && passwordLogin.isNotEmpty()) {
                viewModel.signIn(emailLogin, passwordLogin)
            } else {
                Toast.makeText(context, "Email or Password is invalid", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoogle.setOnClickListener {
            startSignInWithGoogle()
        }

        googleViewModel.authenticatedUserLiveData.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                navController.navigate(R.id.homeFragment)
            } else {
                Log.e("GoogleTest", "Login fail")
            }
        }

        googleViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {
                Toast.makeText(context, "Welcome to Quiz Game", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), "User data not available", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            // Display error message if login fails
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startSignInWithGoogle() {
        val signInIntent = getGoogleSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun getGoogleSignInIntent(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(
            requireActivity(),
            gso
        )
        return googleSignInClient.signInIntent
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (data != null) {
                val idToken = handleGoogleSignInResult(data)
                idToken?.let {
                    googleViewModel.signInWithGoogle(it)
                }
            } else {
                Log.d("GoogleTest", "Data is null")
            }
        }
    }
    private fun handleGoogleSignInResult(data: Intent?): String? {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            return account?.idToken
        } catch (e: ApiException) {
            // Xử lý lỗi nếu cần thiết
            Log.e("GoogleTest", "ApiException: ${e.message}")
        }
        return null
    }
}
