package navigationcomponentturtorialcom.example.quizapp.model

class QuestionModel(
    private val questionId: String,
    private val question: String,
    private val optionA: String,
    private val optionB: String,
    private val optionC: String,
    private val optionD: String,
    private val answer: String,

    val timer: Long
) {
    // Getter cho questionId
    fun getQuestionId(): String {
        return questionId
    }

    // Getter cho question
    fun getQuestion(): String {
        return question
    }

    // Getter cho optionA
    fun getOptionA(): String {
        return optionA
    }

    // Getter cho optionB
    fun getOptionB(): String {
        return optionB
    }

    // Getter cho optionC
    fun getOptionC(): String {
        return optionC
    }

    // Getter cho optionD
    fun getOptionD(): String {
        return optionD
    }

    // Getter cho answer
    fun getAnswer(): String {
        return answer
    }
}