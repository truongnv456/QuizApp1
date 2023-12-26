package navigationcomponentturtorialcom.example.quizapp.views

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import navigationcomponentturtorialcom.example.quizapp.R
import navigationcomponentturtorialcom.example.quizapp.model.QuestionModel
import navigationcomponentturtorialcom.example.quizapp.model.ResultModel
import navigationcomponentturtorialcom.example.quizapp.repository.QuestionRepository
import navigationcomponentturtorialcom.example.quizapp.repository.ResultRepository
import navigationcomponentturtorialcom.example.quizapp.viewmodel.QuestionViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.QuestionViewModelFactory
import navigationcomponentturtorialcom.example.quizapp.viewmodel.ResultViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.ResultViewModelFactory

class QuestionFragment : Fragment() {
    // Khởi tạo View Model với custom param truyền vào
    private val viewModel by viewModels<QuestionViewModel> {
        QuestionViewModelFactory(QuestionRepository())
    }

    private val resultViewModel by viewModels<ResultViewModel> {
        ResultViewModelFactory(ResultRepository())
    }

    val db = Firebase.firestore

    private lateinit var navController: NavController
    private lateinit var optionAButton: Button
    private lateinit var optionBButton: Button
    private lateinit var optionCButton: Button
    private lateinit var optionDButton: Button
    private lateinit var finishButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTv: TextView
    private lateinit var correctTv: TextView
    private lateinit var wrongTv: TextView
    private lateinit var timerCountTv: TextView
    private var timer: CountDownTimer? = null

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
        timerCountTv = view.findViewById(R.id.tvCountTimer)
        correctTv = view.findViewById(R.id.tvCorrectNumber)
        wrongTv = view.findViewById(R.id.tvWrongNumber)

        viewModel.questionMutableLiveData.observe(viewLifecycleOwner) { questions ->
            if (!questions.isNullOrEmpty()) {
                // Logic to display the first question when questions are loaded
                displayQuestionData(viewModel.getCurrentQuestion())
            }
        }
        // Load the next question on button click
        nextButton.setOnClickListener {
            val currentIndex = viewModel.currentQuestionIndex.value ?: 0
            if (currentIndex < (viewModel.questionMutableLiveData.value?.size ?: 0) - 1) {
                viewModel.setCurrentQuestionIndex(currentIndex + 1)
                displayQuestionData(viewModel.getCurrentQuestion())
                reset()
            } else {
                val resultModel = ResultModel(correctAnswer, wrongAnswer)
                resultViewModel.updateResults(resultModel)
                // Handle when all questions are finished
                showDialog()
            }
        }
        // Fetch quizzes
        viewModel.getQuestions()
    }

    private fun displayQuestionData(questionModel: QuestionModel?) {
        if (questionModel != null) {
            // Display the question
            questionTv.text = questionModel.question
            // Display the options
            optionAButton.text = questionModel.optionA
            optionBButton.text = questionModel.optionB
            optionCButton.text = questionModel.optionC
            optionDButton.text = questionModel.optionD

            resetTime()

            val answerButtons = listOf(optionAButton, optionBButton, optionCButton, optionDButton)

            for (button in answerButtons) {
                button.setOnClickListener {
                    val currentQuestion = viewModel.getCurrentQuestion()

                    if (currentQuestion != null) {
                        val isTrue = (currentQuestion.answer == getAnswerFromButton(button))
                        updateScore(isTrue)
                        setButtonBackground(button, isTrue)
                        disableButton()
                        // You may want to call resetButtonBackgrounds() here if you're moving to the next question
                    }
                }
            }
        }
    }

    private fun getAnswerFromButton(button: Button): String {
        return when (button) {
            optionAButton -> optionAButton.text.toString()
            optionBButton -> optionBButton.text.toString()
            optionCButton -> optionCButton.text.toString()
            optionDButton -> optionDButton.text.toString()
            else -> ""
        }
    }

    fun resetTime() {
        timer?.cancel() // Hủy đếm ngược trước đó (nếu có)

        val totalTimeMillis = 30000L // 30 giây
        timer = object : CountDownTimer(totalTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                timerCountTv.text = secondsRemaining.toString()
            }

            override fun onFinish() {
                timerCountTv.text = "Hết thời gian!"
                questionTv.text = "Sorry Time is up! Continue with next question."
                wrongAnswer++
                wrongTv.text = wrongAnswer.toString()
                disableButton()
                // Move to the next question when the time is up
                nextButton.performClick()
            }
        }.start()
    }

    private fun setButtonBackground(button: Button, isTrue: Boolean) {
        if (isTrue) {
            button.setBackgroundColor(Color.GREEN)
        } else {
            button.setBackgroundColor(Color.RED)
        }
    }

    fun updateScore(isTrue: Boolean) {
        if (isTrue) {
            correctAnswer += 1
        } else {
            wrongAnswer += 1
        }
        correctTv.text = correctAnswer.toString()
        wrongTv.text = wrongAnswer.toString()
    }

    private fun disableButton() {
        optionAButton.isEnabled = false
        optionBButton.isEnabled = false
        optionCButton.isEnabled = false
        optionDButton.isEnabled = false
    }

    private fun reset() {
        // Reset other UI elements or states as needed
        optionAButton.isEnabled = true
        optionBButton.isEnabled = true
        optionCButton.isEnabled = true
        optionDButton.isEnabled = true
        // Reset background color for all buttons to default color
        optionAButton.setBackgroundColor(Color.WHITE)
        optionBButton.setBackgroundColor(Color.WHITE)
        optionCButton.setBackgroundColor(Color.WHITE)
        optionDButton.setBackgroundColor(Color.WHITE)
    }

    private fun showDialog() {
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Quiz Game")
            .setMessage("Congratulations!!!\nYou have answered all the questions. Do you want to see the result?")
        builder.setPositiveButton("PLAY AGAIN") { _, _ ->
            navController.navigate(R.id.questionFragment)
        }
        builder.setNegativeButton("FINISH") { _, _ ->
            navController.navigate(R.id.action_questionFragment_to_resultFragment)
        }

        builder.show()
    }
}
