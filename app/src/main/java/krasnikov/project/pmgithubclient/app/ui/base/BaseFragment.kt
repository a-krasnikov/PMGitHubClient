package krasnikov.project.pmgithubclient.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmgithubclient.R

@AndroidEntryPoint
abstract class BaseFragment<T : ViewBinding, V : BaseViewModel> : Fragment() {

    protected lateinit var binding: T

    protected abstract val viewModel: V

    private val progressBar by lazy { view?.findViewById<ProgressBar>(R.id.pbLoading) }

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

    protected fun showLoading() {
        progressBar?.visibility = View.VISIBLE
    }

    protected fun hideLoading() {
        progressBar?.visibility = View.GONE
    }

    protected fun showToast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), stringRes, duration).show()
    }
}