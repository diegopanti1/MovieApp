package com.example.movieapplication.presentation.ui.main.movie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.R
import com.example.movieapplication.databinding.MovieFragmentBinding
import com.example.movieapplication.domain.Resource
import com.example.movieapplication.presentation.adapters.MovieRecyclerViewAdapter
import com.example.movieapplication.presentation.ui.dialogs.loader.LoaderFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.movie_fragment) {

    private var _binding: MovieFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel

    private val moviesAdapter = MovieRecyclerViewAdapter()

    private var loaderFragment: LoaderFragment? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MovieFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerMovies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = moviesAdapter
        }

        binding.swipeMovies.setOnRefreshListener { viewModel.getMoviesFromCloud() }
        viewModel.observeMovies()
        viewModel.getMoviesFromCloud()

        observeResponse()
    }

    private fun observeResponse() = lifecycleScope.launchWhenStarted {
        viewModel.localMovies?.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it.map { movie -> movie.toModel() })
        }
        viewModel.movies.observe(viewLifecycleOwner) {
            val response = it ?: return@observe
            when (response) {
                is Resource.Success -> {
                    GlobalScope.launch(Dispatchers.IO) { viewModel.saveLocalMovies(response.value.results.map{ result -> result.toEntity()}) }
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        response.errorBody.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    if (loaderFragment == null) {
                        loaderFragment = LoaderFragment.newInstance(getString(R.string.loading))
                        loaderFragment?.show(parentFragmentManager, "LOADER_SIGNUP")
                    }
                }
            }

            if (!response.isLoading && loaderFragment != null) {
                loaderFragment?.dismiss()
                loaderFragment = null
            }
            if(binding.swipeMovies.isRefreshing) binding.swipeMovies.isRefreshing = false
        }
    }

}