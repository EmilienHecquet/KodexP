package com.example.kodexp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.kodexp.databinding.FragmentHomeBinding
import com.example.kodexp.ui.CardAdapter
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels { HomeViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: RecyclerView = binding.listPokemon
        listView.adapter = CardAdapter(viewModel.pokemonList.value ?: emptyList())


        viewModel.pokemonList.observe(viewLifecycleOwner) {
            (listView.adapter as CardAdapter).updateList(it)
        }

        listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // If scroll at the end of the list populate the list with new pokemons
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lifecycleScope.launch {
                        viewModel.populatePokemonList()
                    }
                }
            }
        })

        viewModel.pokemonList.observe(viewLifecycleOwner) {
            (listView.adapter as CardAdapter).updateList(it)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
