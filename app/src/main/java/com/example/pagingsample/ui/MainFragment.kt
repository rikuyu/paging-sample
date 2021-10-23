package com.example.pagingsample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagingsample.MainViewModel
import com.example.pagingsample.Resource
import com.example.pagingsample.databinding.FragmentMainBinding
import com.example.pagingsample.model.data.Res
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MainAdapter(viewLifecycleOwner, requireContext())

        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager(requireContext()).getOrientation()
                )
            )
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        lifecycleScope.launch {
            viewModel.characters.collectLatest {
                adapter.submitData(it)
            }
        }

        // 初回ロード
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { state ->
                when (state.refresh) {
                    is LoadState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.errorMsg.visibility = View.INVISIBLE
                    }
                    is LoadState.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.errorMsg.visibility = View.VISIBLE
                    }
                    is LoadState.NotLoading -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.errorMsg.visibility = View.INVISIBLE
                    }
                }
            }
        }

        binding.recyclerView.adapter =
            adapter.withLoadStateFooter(MainLoadStateAdapter(adapter::retry))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}