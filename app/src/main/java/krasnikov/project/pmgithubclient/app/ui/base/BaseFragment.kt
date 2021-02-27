package krasnikov.project.pmgithubclient.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding, V : BaseViewModel> : Fragment() {

    protected lateinit var binding: T

    protected abstract val viewModel: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavEvent()
    }

    abstract fun setupBinding()

    private fun observeNavEvent() {
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            it.navigate(parentFragmentManager)
        }
    }

    protected fun showToast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), stringRes, duration).show()
    }
}