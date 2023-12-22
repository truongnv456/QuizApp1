package navigationcomponentturtorialcom.example.quizapp.model

data class QuestionModel(
    val questionId: Int,
    val questionContent: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val answer: String,

    var time: String
)