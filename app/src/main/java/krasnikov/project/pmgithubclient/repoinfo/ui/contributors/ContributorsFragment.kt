package krasnikov.project.pmgithubclient.repoinfo.ui.contributors

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import krasnikov.project.pmgithubclient.app.ui.base.PaginationScrollListener
import krasnikov.project.pmgithubclient.utils.Result

class ContributorsFragment : Fragment() {
/*
    val adapt = ContributorAdapter()
*/


   /* private fun setupRecycler() {
        with(binding.rvContributors) {
            adapter = adapt
            addOnScrollListener(object :
                PaginationScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        when (val result =
                            Test(requireContext()).getContributors(
                                "freeCodeCamp",
                                "freeCodeCamp",
                                1
                            )) {
                            is Result.Success -> {
                                result.data.forEach(::println)
                                adapt.addItems(result.data)
                            }
                            is Result.Error -> {
                                println("error")
                            }
                        }
                    }
                }
            })
        }
    }*/
}