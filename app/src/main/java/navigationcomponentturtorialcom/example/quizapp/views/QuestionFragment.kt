package navigationcomponentturtorialcom.example.quizapp.views

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

    private lateinit var navController: NavController
    private lateinit var btnOptionA: Button
    private lateinit var btnOptionB: Button
    private lateinit var btnOptionC: Button
    private lateinit var btnOptionD: Button
    private lateinit var btnFinish: Button
    private lateinit var btnNext: Button
    private lateinit var tvQuestion: TextView
    private lateinit var tvCorrect: TextView
    private lateinit var tvWrong: TextView
    private lateinit var tvTimerCount: TextView
    private var timer: CountDownTimer? = null

    private var correctAnswer = 0
    private var wrongAnswer = 0
    private var currentIndex = 0
    private var answerQuestion = 0


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
        btnOptionA = view.findViewById(R.id.btnOptionA)
        btnOptionB = view.findViewById(R.id.btnOptionB)
        btnOptionC = view.findViewById(R.id.btnOptionC)
        btnOptionD = view.findViewById(R.id.btnOptionD)
        btnFinish = view.findViewById(R.id.btnFinish)
        btnNext = view.findViewById(R.id.btnNext)
        tvQuestion = view.findViewById(R.id.tvQuizDetail)
        tvTimerCount = view.findViewById(R.id.tvCountTimer)
        tvCorrect = view.findViewById(R.id.tvCorrectNumber)
        tvWrong = view.findViewById(R.id.tvWrongNumber)

        viewModel.questionMutableLiveData.observe(viewLifecycleOwner) { questions ->
            if (!questions.isNullOrEmpty()) {
                // Logic to display the first question when questions are loaded
                displayQuestionData(viewModel.getCurrentQuestion())
            }
        }
        // Load the next question on button click
        btnNext.setOnClickListener {
            currentIndex = viewModel.currentQuestionIndex.value ?: 0
            Log.d("currentIndex", "{$currentIndex}")
            // Kiểm tra xem người chơi có chọn đáp án hay không
            if (isAnswerSelected()) {
                // Nếu không chọn đáp án, tăng số câu trả lời sai
                wrongAnswer++
                tvWrong.text = wrongAnswer.toString()
            }

            if (currentIndex < (viewModel.questionMutableLiveData.value?.size ?: 0) - 1) {
                answerQuestion = currentIndex + 1
                viewModel.setCurrentQuestionIndex(answerQuestion)
                displayQuestionData(viewModel.getCurrentQuestion())
                reset()
            } else {
                val resultModel = ResultModel(correctAnswer, wrongAnswer)
                resultViewModel.updateResult(resultModel)
                // Handle when all questions are finished
                showDialog()
            }
        }

        btnFinish.setOnClickListener {
            val unAnsweredQuestion = viewModel.questionMutableLiveData.value!!.size - answerQuestion

            if (unAnsweredQuestion > 0) {
                wrongAnswer += unAnsweredQuestion
                tvWrong.text = wrongAnswer.toString()
            }

            val resultModel = ResultModel(correctAnswer, wrongAnswer)
            resultViewModel.updateResult(resultModel)

            showDialog()
        }

        // Fetch quizzes
        viewModel.getQuestions()
    }

    // Hàm kiểm tra xem người chơi đã chọn đáp án hay chưa
    private fun isAnswerSelected(): Boolean {
        // Bạn có thể thực hiện logic kiểm tra xem một trong các nút đáp án đã được chọn hay không
        return btnOptionA.isEnabled && btnOptionB.isEnabled
                && btnOptionC.isEnabled && btnOptionD.isEnabled
    }

    private fun displayQuestionData(questionModel: QuestionModel?) {
        if (questionModel != null) {
            // Display the question
            tvQuestion.text = questionModel.question
            // Display the options
            btnOptionA.text = questionModel.optionA
            btnOptionB.text = questionModel.optionB
            btnOptionC.text = questionModel.optionC
            btnOptionD.text = questionModel.optionD

            resetTime()
            val answerButtons = listOf(btnOptionA, btnOptionB, btnOptionC, btnOptionD)

            for (button in answerButtons) {
                button.setOnClickListener {
                    val currentQuestion = viewModel.getCurrentQuestion()

                    val isTrue = (currentQuestion.answer == getAnswerFromButton(button))
                    updateScore(isTrue)
                    setButtonBackground(button, isTrue)
                    disableButton()
                    // You may want to call resetButtonBackgrounds() here if you're moving to the next question
                }
            }
        }
    }

    private fun getAnswerFromButton(button: Button): String {
        return when (button) {
            btnOptionA -> btnOptionA.text.toString()
            btnOptionB -> btnOptionB.text.toString()
            btnOptionC -> btnOptionC.text.toString()
            btnOptionD -> btnOptionD.text.toString()
            else -> ""
        }
    }

    fun resetTime() {
        timer?.cancel() // Hủy đếm ngược trước đó (nếu có)

        val totalTimeMillis = 30000L // 30 giây
        timer = object : CountDownTimer(totalTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                tvTimerCount.text = secondsRemaining.toString()
            }

            override fun onFinish() {
                tvTimerCount.text = "Hết thời gian!"
                tvQuestion.text = "Sorry Time is up! Continue with next question."
                wrongAnswer++
                tvWrong.text = wrongAnswer.toString()
                disableButton()
                // Move to the next question when the time is up
            }
        }.start()
    }

    private fun setButtonBackground(button: Button, isTrue: Boolean) {
        if (isTrue) {
            button.setBackgroundColor(Color.GREEN)
        } else {
            button.setBackgroundColor(Color.RED)

            // Find and highlight the correct answer in green
            val correctButton = findCorrectAnswerButton()
            correctButton?.setBackgroundColor(Color.GREEN)
        }
    }

    private fun findCorrectAnswerButton(): Button? {
        val currentQuestion = viewModel.getCurrentQuestion()
        val correctAnswerText = currentQuestion.answer

        return when (correctAnswerText) {
            btnOptionA.text.toString() -> btnOptionA
            btnOptionB.text.toString() -> btnOptionB
            btnOptionC.text.toString() -> btnOptionC
            btnOptionD.text.toString() -> btnOptionD
            else -> null
        }
    }

    fun updateScore(isTrue: Boolean) {
        if (isTrue) {
            correctAnswer += 1
        } else {
            wrongAnswer += 1
        }
        tvCorrect.text = correctAnswer.toString()
        tvWrong.text = wrongAnswer.toString()
    }

    private fun disableButton() {
        btnOptionA.isEnabled = false
        btnOptionB.isEnabled = false
        btnOptionC.isEnabled = false
        btnOptionD.isEnabled = false
    }

    private fun reset() {
        // Reset other UI elements or states as needed
        btnOptionA.isEnabled = true
        btnOptionB.isEnabled = true
        btnOptionC.isEnabled = true
        btnOptionD.isEnabled = true
        // Reset background color for all buttons to default color
        btnOptionA.setBackgroundColor(Color.WHITE)
        btnOptionB.setBackgroundColor(Color.WHITE)
        btnOptionC.setBackgroundColor(Color.WHITE)
        btnOptionD.setBackgroundColor(Color.WHITE)
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
        builder.setCancelable(true)
    }
}
