package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import navigationcomponentturtorialcom.example.quizapp.R
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import navigationcomponentturtorialcom.example.quizapp.repository.AuthRepository
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModelFactory

class HomeFragment : Fragment() {
    private val viewModel by viewModels<AuthViewModel>{
        AuthViewModelFactory(AuthRepository())
    }

    private lateinit var btnSignOut: Button
    private lateinit var btnStartQuiz: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnSignOut = view.findViewById(R.id.btnSignOut)
        btnStartQuiz = view.findViewById(R.id.btnStartQuiz)

        super.onViewCreated(view, savedInstanceState)
        btnSignOut.setOnClickListener {
            viewModel.signOut()
            showSignOutDialog()
            findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
        }

        btnStartQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_questionFragment)
        }
    }

    // Hàm để hiển thị dialog khi sign out thành công
    private fun showSignOutDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        // Thiết lập thông tin cho dialog
        alertDialogBuilder.setTitle("Sign Out")
        alertDialogBuilder.setMessage("Sign Out Successfully")

        // Thiết lập nút đóng dialog
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            // Xử lý sự kiện khi người dùng nhấn nút OK
            // Có thể thêm các thao tác khác ở đây nếu cần
            dialog.dismiss() // Đóng dialog sau khi người dùng nhấn nút OK
        }

        // Tạo và hiển thị dialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}