package krasnikov.project.pmgithubclient.search.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentSearchBinding
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    private var initQuery by FragmentArgsDelegate<String>(ARG_SEARCH_QUERY)

    override val viewModel by viewModels<SearchViewModel>()

    override fun setupBinding() {
        binding = FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchToolbar()
        observeContentSearch()
        setupInitQuery()
    }

    private fun setupSearchToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        with(binding.toolbar) {
            btnSearch.setOnClickListener {
                searchUser()
            }

            etQuery.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        searchUser()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setupInitQuery() {
        binding.toolbar.etQuery.setText(initQuery)
        searchUser()
    }

    private fun observeContentSearch() {
        viewModel.contentSearch.observe(viewLifecycleOwner) {
            with(binding.rvSearch){
                adapter = UsersAdapter().apply {
                pagedList = it
                onItemClickListener = {
                    viewModel.navigateToUserInfo(it.login)
                }
            }}
        }
    }

    private fun searchUser() {
        with(binding.toolbar.etQuery) {
            clearFocus()
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(this.windowToken, 0)
            viewModel.searchUser(text.toString())
        }
    }

    companion object {
        private const val ARG_SEARCH_QUERY = "search_query"

        @JvmStatic
        fun newInstance(query: String) =
            SearchFragment().apply {
                initQuery = query
            }
    }
}