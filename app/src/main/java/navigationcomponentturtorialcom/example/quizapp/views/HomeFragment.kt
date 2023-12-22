package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import navigationcomponentturtorialcom.example.quizapp.R
import android.widget.Button
import androidx.navigation.fragment.findNavController
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnSignOut).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
        }
    }
}