package krasnikov.project.pmgithubclient.repo.info.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import krasnikov.project.pmgithubclient.databinding.FragmentRepoInfoBinding
import krasnikov.project.pmgithubclient.utils.FragmentArgsDelegate

class RepoInfoFragment : Fragment() {

    var owner by FragmentArgsDelegate<String>(ARG_OWNER)
    var repo by FragmentArgsDelegate<String>(ARG_REPO)

    private lateinit var binding: FragmentRepoInfoBinding

    private lateinit var viewPagerAdapter: RepoInfoPagerAdapter

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

        setupTabLayout()
    }

    private fun setupBinding() {
        binding = FragmentRepoInfoBinding.inflate(layoutInflater)
    }

    private fun setupTabLayout() {
        viewPagerAdapter = RepoInfoPagerAdapter(this, owner, repo)
        binding.vpInfo.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.vpInfo) { tab, position ->
            tab.setText(viewPagerAdapter.getItemTabName(position))
        }.attach()
    }

    companion object {
        private const val ARG_OWNER = "owner"
        private const val ARG_REPO = "repo"

        @JvmStatic
        fun newInstance(owner: String, repo: String) =
            RepoInfoFragment().apply {
                this.owner = owner
                this.repo = repo
            }
    }
}