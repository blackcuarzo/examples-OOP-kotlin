package com.danielcano.imdbapp.uilayer.uielements.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielcano.imdbapp.uilayer.adapters.MovieSearchListAdapter
import com.danielcano.imdbapp.databinding.FragmentSearchBinding
import com.danielcano.imdbapp.domainlayer.models.MovieModel
import com.danielcano.imdbapp.uilayer.viewmodels.MoviesViewModel

class SearchFragment : Fragment() {
    private val viewModel by viewModels<MoviesViewModel>()
    private var _binding:FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        val view = binding.root
        val movieList: RecyclerView = binding.movieList
        movieList.layoutManager = LinearLayoutManager(requireContext())
        val movieListAdapter = MovieSearchListAdapter(::showMovieDetails)
        movieList.adapter = movieListAdapter
        viewModel.movieList.observe(viewLifecycleOwner) { movieItemsList ->
            movieListAdapter.submitList(movieItemsList)
        }
        viewModel.status.observe(viewLifecycleOwner) {
            binding.statusText.text = it
        }
        return view
    }

    private fun showMovieDetails(movie: MovieModel) {
        val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(
            name = movie.name,
            nameEs = movie.nameEs,
            synopsis = movie.synopsis,
            preview = movie.preview,
            thumbnail = movie.thumbnail,
            shortDescription = movie.synopsis,
            numberEpisodes = movie.numberEpisodes
        )
        findNavController().navigate(action)
    }
}

