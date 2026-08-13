package com.grigori.app.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.grigori.app.R
import com.grigori.app.databinding.FragmentDashboardBinding
import com.grigori.app.data.local.AppDatabase
import com.grigori.app.data.repository.ProblemaRepository
import com.grigori.app.data.repository.UsuarioRepository
import com.grigori.app.ui.history.HistoryAdapter

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerRecentProblems.layoutManager = LinearLayoutManager(requireContext())
        val adapter = HistoryAdapter { problem ->
            val bundle = bundleOf("problemId" to problem.id)
            findNavController().navigate(R.id.action_dashboardFragment_to_solutionFragment, bundle)
        }
        binding.recyclerRecentProblems.adapter = adapter

        binding.btnStartProblem.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_newProblemFragment)
        }

        binding.btnHistory.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_historyFragment)
        }

        viewModel.greeting.observe(viewLifecycleOwner) { binding.tvGreeting.text = it }
        viewModel.recentProblems.observe(viewLifecycleOwner) { adapter.submitList(it) }
        viewModel.loadDashboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
