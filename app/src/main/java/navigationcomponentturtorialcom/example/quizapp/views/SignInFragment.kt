package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import navigationcomponentturtorialcom.example.quizapp.R
import navigationcomponentturtorialcom.example.quizapp.repository.AuthRepository
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModelFactory


class SignInFragment : Fragment() {
    private val viewModel by viewModels<AuthViewModel>{
        AuthViewModelFactory(AuthRepository())
    }

    private lateinit var btnSignIn: Button
    private lateinit var tvSignUp: TextView
    private var etEmailLogin: TextInputEditText? = null
    private var etPasswordLogin: TextInputEditText? = null
    private var correctAnswer = 0
    private var wrongAnswer = 0

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

        viewModel.userLiveData.observe(viewLifecycleOwner){ firebaseUser -> //tại sao k dùng this được (compile ERROR)
            if(firebaseUser != null){
                Toast.makeText(context, "Welcome to Quiz Game", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            }
            else{
                Toast.makeText(requireContext(), "User data not available", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            // Display error message if login fails
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

    }

}
