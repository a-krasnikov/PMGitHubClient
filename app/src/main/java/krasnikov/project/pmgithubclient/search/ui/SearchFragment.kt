package krasnikov.project.pmgithubclient.search.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import krasnikov.project.pmgithubclient.app.di.AppComponent
import krasnikov.project.pmgithubclient.app.ui.base.BaseFragment
import krasnikov.project.pmgithubclient.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override val viewModel by viewModels<SearchViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchViewModel(AppComponent.searchService) as T
            }
        }
    }

    override fun setupBinding() {
        binding = FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        setupSearchToolbar()
        observeContentSearch()
    }

    private fun setupSearchToolbar() {
        with(binding.toolbar) {
            searchToolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }

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

    private fun observeContentSearch() {
        viewModel.contentSearch.observe(viewLifecycleOwner) {
            binding.rvSearch.adapter = UsersAdapter(it)
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
}