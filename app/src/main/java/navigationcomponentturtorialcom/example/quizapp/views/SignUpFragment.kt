package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import navigationcomponentturtorialcom.example.quizapp.R
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {
    lateinit var viewModel: AuthViewModel
    lateinit var etEmailRegister: TextInputEditText
    lateinit var etPasswordRegister: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etEmailRegister = view.findViewById(R.id.etEmailRegister)
        etPasswordRegister = view.findViewById(R.id.etPasswordRegister)
        view.findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val email = etEmailRegister.text.toString()
            val password = etPasswordRegister.text.toString()

            if (!!email.isEmpty() && !!password.isEmpty()) {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            } else {
                Toast.makeText(context, "Please Enter Email And Password", Toast.LENGTH_LONG).show()
            }
        }
    }
}