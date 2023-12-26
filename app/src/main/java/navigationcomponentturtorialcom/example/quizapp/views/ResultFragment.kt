package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import navigationcomponentturtorialcom.example.quizapp.R
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import navigationcomponentturtorialcom.example.quizapp.repository.QuestionRepository
import navigationcomponentturtorialcom.example.quizapp.repository.ResultRepository
import navigationcomponentturtorialcom.example.quizapp.viewmodel.QuestionViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.QuestionViewModelFactory
import navigationcomponentturtorialcom.example.quizapp.viewmodel.ResultViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.ResultViewModelFactory

class ResultFragment : Fragment() {
    private val viewModel by viewModels<ResultViewModel> {
        ResultViewModelFactory(ResultRepository())
    }

    private lateinit var navController: NavController
    private lateinit var btnPlayAgain: Button
    private lateinit var btnExit: Button
    private lateinit var tvCorrectAnswer: TextView
    private lateinit var tvWrongAnswer: TextView

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
        tvCorrectAnswer = view.findViewById<TextView>(R.id.tvCorrectAnswer)
        tvWrongAnswer = view.findViewById<TextView>(R.id.tvWrongAnswer)

        btnPlayAgain.setOnClickListener {
            navController.navigate(R.id.action_resultFragment_to_questionFragment)
        }

        btnExit.setOnClickListener {
            navController.navigate(R.id.action_resultFragment_to_homeFragment)
        }

        viewModel.resultMutableLiveData.observe(viewLifecycleOwner) { resultModel ->
            tvCorrectAnswer.text = "${viewModel.correctAnswer}"
            tvWrongAnswer.text = "${viewModel.wrongAnswer}"
        }

        viewModel.getResult()
    }
}