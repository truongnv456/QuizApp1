package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import navigationcomponentturtorialcom.example.quizapp.R
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel


class SignInFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var btnSignIn: Button
    private var etEmailLogin: TextInputEditText? = null
    private var etPasswordLogin: TextInputEditText? = null

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

        view.findViewById<TextView>(R.id.tvSignUp).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        btnSignIn.setOnClickListener {
            val emailLogin = etEmailLogin!!.text.toString()
            val passwordLogin: String = etPasswordLogin!!.text.toString()

            if (emailLogin.isNotEmpty() && passwordLogin.isNotEmpty()) {
                viewModel.signIn(emailLogin, passwordLogin)
                viewModel.firebaseUserMutableLiveData.observe(viewLifecycleOwner) {
                    if (it != null) {
                        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                    }
                }
                Toast.makeText(context, "Welcome to Quiz Game", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Email or Password is invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }
}
