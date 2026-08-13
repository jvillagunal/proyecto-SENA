package com.grigori.app.ui.solution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.grigori.app.databinding.FragmentSolutionBinding

class SolutionFragment : Fragment() {
    private var _binding: FragmentSolutionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SolutionViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.problem.observe(viewLifecycleOwner) { problem ->
            problem?.let {
                binding.tvEquation.text = it.ecuacion
                binding.tvResult.text = it.resultado
                binding.tvSteps.text = it.pasos
            }
        }
        val problemId = arguments?.getInt("problemId") ?: 0
        viewModel.loadProblem(problemId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
