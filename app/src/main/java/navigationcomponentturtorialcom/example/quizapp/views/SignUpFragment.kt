package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import navigationcomponentturtorialcom.example.quizapp.R
import navigationcomponentturtorialcom.example.quizapp.repository.AuthRepository
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModelFactory

class SignUpFragment : Fragment() {
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepository())
    }

    private var etEmailRegister: EditText? = null
    private var etPasswordRegister: EditText? = null
    private lateinit var btnRegister: Button
    private lateinit var btnBack: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etEmailRegister = view.findViewById<TextInputEditText>(R.id.etEmailRegister)
        etPasswordRegister = view.findViewById<TextInputEditText>(R.id.etPasswordRegister)
        btnRegister = view.findViewById<Button>(R.id.btnRegister)
        btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        btnRegister.setOnClickListener {
            val email = etEmailRegister!!.text.toString()
            val password: String = etPasswordRegister!!.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signUp(email, password)
            }

            viewModel.userLiveData.observe(viewLifecycleOwner) { firebaseUser -> 
                if (firebaseUser != null) {
                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
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

        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

}
