package com.grigori.app.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.grigori.app.R
import com.grigori.app.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerHistory.layoutManager = LinearLayoutManager(requireContext())
        val adapter = HistoryAdapter { problem ->
            val bundle = bundleOf("problemId" to problem.id)
            findNavController().navigate(R.id.action_historyFragment_to_solutionFragment, bundle)
        }
        binding.recyclerHistory.adapter = adapter

        viewModel.history.observe(viewLifecycleOwner) { problems ->
            adapter.submitList(problems)
            binding.tvEmpty.visibility = if (problems.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.deleteResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), R.string.deleted_problem, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loadHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
