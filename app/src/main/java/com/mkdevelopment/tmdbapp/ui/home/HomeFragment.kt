package com.mkdevelopment.tmdbapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkdevelopment.tmdbapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var movieAdapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        movieAdapter = MovieAdapter(object : MovieClickListener {
            override fun onMovieClickListener(movieId: Int?) {
                movieId?.let {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
                    findNavController().navigate(action)
                }
            }
        })

        homeViewModel.getMovieList()

        homeViewModel.movieList.observe(viewLifecycleOwner) {
            movieAdapter.setMovieList(it)
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.errorText.text = it
            binding.errorText.isVisible = true
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = movieAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}