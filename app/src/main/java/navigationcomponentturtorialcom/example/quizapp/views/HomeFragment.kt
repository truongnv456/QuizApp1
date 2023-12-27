package navigationcomponentturtorialcom.example.quizapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import navigationcomponentturtorialcom.example.quizapp.R
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import navigationcomponentturtorialcom.example.quizapp.repository.AuthGoogleRepository
import navigationcomponentturtorialcom.example.quizapp.repository.AuthRepository
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthGoogleViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthGoogleViewModelFactory
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModel
import navigationcomponentturtorialcom.example.quizapp.viewmodel.AuthViewModelFactory

class HomeFragment : Fragment() {
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepository())
    }

    private val googleViewModel by viewModels<AuthGoogleViewModel> {
        AuthGoogleViewModelFactory(AuthGoogleRepository())
    }

    private lateinit var btnSignOut: Button
    private lateinit var btnStartQuiz: Button
    private lateinit var googleSignInClient: GoogleSignInClient

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
            signOutWithGoogle()
            showSignOutDialog()
            findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
        }

        btnStartQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_questionFragment)
        }
        // Đảm bảo bạn đã khởi tạo GoogleSignInClient trong Fragment
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }

    private fun signOutWithGoogle() {
        googleSignInClient.signOut()
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