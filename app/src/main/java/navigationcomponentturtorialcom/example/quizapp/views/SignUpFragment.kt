package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import navigationcomponentturtorialcom.example.quizapp.R
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel
    private var etEmailRegister: EditText? = null
    private var etPasswordRegister: EditText? = null
    private lateinit var btnRegister: Button

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

        btnRegister.setOnClickListener {
            val email = etEmailRegister!!.text.toString()
            val password: String = etPasswordRegister!!.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signUp(email, password)
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
                viewModel.firebaseUserMutableLiveData.observe(viewLifecycleOwner) {
                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                }
            } else {
                Toast.makeText(context, "Please enter Email and Password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }
}
