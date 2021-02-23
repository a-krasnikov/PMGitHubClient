package krasnikov.project.pmgithubclient.repoinfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import krasnikov.project.pmgithubclient.databinding.FragmentRepoInfoBinding

class RepoInfoFragment : Fragment() {

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
        viewPagerAdapter = RepoInfoPagerAdapter(this)
        binding.vpInfo.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.vpInfo) { tab, position ->
            tab.setText(viewPagerAdapter.getItemTabName(position))
        }.attach()
    }
}