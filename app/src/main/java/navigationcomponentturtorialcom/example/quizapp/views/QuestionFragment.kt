package navigationcomponentturtorialcom.example.quizapp.views

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import navigationcomponentturtorialcom.example.quizapp.R
import navigationcomponentturtorialcom.example.quizapp.model.QuestionModel
import navigationcomponentturtorialcom.example.quizapp.repository.AuthRepository
import navigationcomponentturtorialcom.example.quizapp.repository.QuestionRepository
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModelFactory
import navigationcomponentturtorialcom.example.quizapp.viewmodel.QuestionViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.QuestionViewModelFactory

class QuestionFragment : Fragment() {
    private val viewModel by viewModels<QuestionViewModel> {
        QuestionViewModelFactory(QuestionRepository())
    }
    private lateinit var navController: NavController
    private lateinit var optionAButton: Button
    private lateinit var optionBButton: Button
    private lateinit var optionCButton: Button
    private lateinit var optionDButton: Button
    private lateinit var finishButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTv: TextView
    private lateinit var answerTv: TextView
    private lateinit var questionNumberTv: TextView
    private lateinit var timerCount: TextView
    private lateinit var timer: CountDownTimer

    private var correctAnswer = 0
    private var wrongAnswer = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        optionAButton = view.findViewById(R.id.btnOptionA)
        optionBButton = view.findViewById(R.id.btnOptionB)
        optionCButton = view.findViewById(R.id.btnOptionC)
        optionDButton = view.findViewById(R.id.btnOptionD)
        finishButton = view.findViewById(R.id.btnFinish)
        nextButton = view.findViewById(R.id.btnNext)
        questionTv = view.findViewById(R.id.tvQuizDetail)
        timerCount = view.findViewById(R.id.tvCountTimer)

        viewModel.questionMutableLiveData.observe(viewLifecycleOwner) { questions ->
            if (!questions.isNullOrEmpty()) {
                // Logic to display the first question when quizzes are loaded
                displayQuestionData(viewModel.getCurrentQuestion())
            }
        }

        // Load the next question on button click
        nextButton.setOnClickListener {
            val currentIndex = viewModel.currentQuizIndex.value ?: 0
            if (currentIndex < (viewModel.questionMutableLiveData.value?.size ?: 0) - 1) {
                viewModel.setCurrentQuestionIndex(currentIndex + 1)
                resetUI()
                displayQuestionData(viewModel.getCurrentQuestion())
            } else {
                // Handle when all questions are finished
                navController.navigate(R.id.action_questionFragment_to_resultFragment)
            }
        }

        // Fetch quizzes
        viewModel.getQuestions()
    }

    private fun displayQuestionData(questionDetail: QuestionModel?) {
        questionDetail?.let { questionModel ->
            // Display the question
            questionTv.text = questionModel.question
            // Display the options
            optionAButton.text = questionModel.optionA
            optionBButton.text = questionModel.optionB
            optionCButton.text = questionModel.optionC
            optionDButton.text = questionModel.optionD

            optionAButton.setOnClickListener {
                val currentQuestion = viewModel.getCurrentQuestion()

                if (currentQuestion != null) {
                    // Update UI based on whether the user answered correctly
                    setButtonBackground(optionAButton, (currentQuestion.answer == questionModel.optionA))
                    disableButton()
                }
            }
            optionBButton.setOnClickListener {
                val currentQuestion = viewModel.getCurrentQuestion()

                if (currentQuestion != null) {
                    // Update UI based on whether the user answered correctly
                    setButtonBackground(optionBButton, (currentQuestion.answer == questionModel.optionB))
                    disableButton()
                }
            }

            optionCButton.setOnClickListener {
                val currentQuestion = viewModel.getCurrentQuestion()

                if (currentQuestion != null) {
                    // Update UI based on whether the user answered correctly
                    setButtonBackground(optionCButton, (currentQuestion.answer == questionModel.optionC))
                    disableButton()
                }
            }

            optionDButton.setOnClickListener {
                val currentQuestion = viewModel.getCurrentQuestion()

                if (currentQuestion != null) {
                    // Update UI based on whether the user answered correctly
                    setButtonBackground(optionDButton, (currentQuestion.answer == questionModel.optionD))
                    disableButton()
                }
            }

            // Set up timer
            timer = object : CountDownTimer(questionModel.timer * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerCount.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    timerCount.text = "Time's up!"
                    disableButton()
                }
            }
            timer.start()

            // Handle user option selection
        }
    }

    private fun setButtonBackground(button: Button, isTrue: Boolean) {
        if (isTrue) {
            button.setBackgroundColor(Color.GREEN)
        } else {
            button.setBackgroundColor(Color.RED)
        }
    }

    private fun disableButton() {
        optionAButton.isEnabled = false
        optionBButton.isEnabled = false
        optionCButton.isEnabled = false
        optionDButton.isEnabled = false
    }

    private fun resetUI() {
        // Reset background color for all buttons to default color
        optionAButton.setBackgroundColor(Color.WHITE)
        optionBButton.setBackgroundColor(Color.WHITE)
        optionCButton.setBackgroundColor(Color.WHITE)
        optionDButton.setBackgroundColor(Color.WHITE)

        // Reset other UI elements or states as needed
        optionAButton.isEnabled = true
        optionBButton.isEnabled = true
        optionCButton.isEnabled = true
        optionDButton.isEnabled = true
        timer?.cancel()
    }
}
