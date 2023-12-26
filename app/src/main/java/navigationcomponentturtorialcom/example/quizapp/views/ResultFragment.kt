package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import navigationcomponentturtorialcom.example.quizapp.R
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation

class ResultFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var btnPlayAgain: Button
    private lateinit var btnExit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnPlayAgain = view.findViewById<Button>(R.id.btnPlayAgain)
        btnExit = view.findViewById<Button>(R.id.btnExit)

        btnPlayAgain.setOnClickListener {
            navController.navigate(R.id.action_resultFragment_to_questionFragment)
        }

        btnExit.setOnClickListener {
            navController.navigate(R.id.action_resultFragment_to_homeFragment)
        }
    }
}