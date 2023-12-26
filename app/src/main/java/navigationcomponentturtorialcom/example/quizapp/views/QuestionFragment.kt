package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import navigationcomponentturtorialcom.example.quizapp.R
import navigationcomponentturtorialcom.example.quizapp.model.QuestionModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.QuestionViewModel

class QuestionFragment : Fragment() {
    private lateinit var viewModel: QuestionViewModel
    private var currentQueNo = 0

    private lateinit var progressBar: ProgressBar
    private lateinit var option1Btn: Button
    private lateinit var option2Btn: Button
    private lateinit var option3Btn: Button
    private lateinit var option4Btn: Button
    private lateinit var nextQueBtn: Button
    private lateinit var questionTv: TextView
    private lateinit var ansFeedBackTv: TextView
    private lateinit var questionNumberTv: TextView
    private lateinit var timerCountTv: TextView
    private lateinit var closeQuizBtn: ImageView
    private lateinit var questionId: String
    private var timer: Long = 0
    private var answer: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        option1Btn = view.findViewById(R.id.btnOptionA)
        option2Btn = view.findViewById(R.id.btnOptionB)
        option3Btn = view.findViewById(R.id.btnOptionC)
        option4Btn = view.findViewById(R.id.btnOptionD)
        nextQueBtn = view.findViewById(R.id.btnNext);
        ansFeedBackTv = view.findViewById(R.id.tvAnswer);
        questionTv = view.findViewById(R.id.tvQuizDetail);
        timerCountTv = view.findViewById(R.id.tvCountTimer);
        questionNumberTv = view.findViewById(R.id.tvQuestionNumber)
        progressBar = view.findViewById(R.id.progressBar2)

        closeQuizBtn.setOnClickListener {
            findNavController().navigate(R.id.action_questionFragment_to_homeFragment)
        }
    }

    private fun loadQuestions(i : Int){
        viewModel.questionMutableLiveData.observe(viewLifecycleOwner){
            //Observer override onChanged
                listQuestion->
            questionTv.setText(listQuestion[i-1].getQuestion())
            option1Btn.setText(listQuestion[i-1].getOptionA())
            option2Btn.setText(listQuestion[i-1].getOptionB())
            option3Btn.setText(listQuestion[i-1].getOptionC())
            option4Btn.setText(listQuestion[i-1].getOptionD())
        }
    }
}