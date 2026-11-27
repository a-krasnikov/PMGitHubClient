package krasnikov.project.pmgithubclient.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmgithubclient.R
import krasnikov.project.pmgithubclient.utils.State

@AndroidEntryPoint
class LoginFragment : Fragment() {

    val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent { LoginScreen(viewModel = viewModel) }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleOauth(requireActivity().intent)
    }

    private fun startGitHubLogin() {
        val authIntent = Intent(Intent.ACTION_VIEW, viewModel.authGitHubUrl)
        startActivity(authIntent)
    }

    @Composable
    fun LoginScreen(
        viewModel: LoginViewModel,
    ) {
        val content = viewModel.content.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { startGitHubLogin() }) {
                    Text("Login with GitHub")
                }
                when (val state = content.value) {
                    is State.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                    }

                    is State.Content -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(requireContext(), R.string.toast_login_successful, Toast.LENGTH_SHORT).show()
                        }
                    }

                    is State.Error -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(requireContext(), state.error.stringRes, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
