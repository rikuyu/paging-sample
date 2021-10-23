package com.example.pagingsample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingsample.databinding.LoadstateItemBinding

class MainLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MainLoadStateAdapter.MainLoadStateViewHolder>() {

    class MainLoadStateViewHolder(private val binding: LoadstateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retry: () -> Unit) {
            when (loadState) {
                is LoadState.Loading -> {
                    binding.apply {
                        visible(progressBar)
                        invisible(errorMsg)
                        invisible(retryBtn)
                    }
                }
                is LoadState.Error -> {
                    binding.apply {
                        invisible(progressBar)
                        visible(errorMsg)
                        visible(retryBtn)
                    }
                }
                is LoadState.NotLoading -> {
                    binding.apply {
                        visible(progressBar)
                        invisible(errorMsg)
                        invisible(retryBtn)
                    }
                }
            }

            binding.retryBtn.setOnClickListener {
                retry()
            }
        }

        private fun visible(view: View){
            view.visibility = View.VISIBLE
        }

        private fun invisible(view: View){
            view.visibility = View.INVISIBLE
        }
    }

    override fun onBindViewHolder(holder: MainLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MainLoadStateViewHolder {
        val binding =
            LoadstateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainLoadStateViewHolder(binding)
    }
}