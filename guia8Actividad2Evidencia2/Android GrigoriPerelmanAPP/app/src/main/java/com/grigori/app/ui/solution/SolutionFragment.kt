package com.grigori.app.ui.solution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.grigori.app.databinding.FragmentSolutionBinding

class SolutionFragment : Fragment() {
    private var _binding: FragmentSolutionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SolutionViewModel by viewModels()
    private val args: SolutionFragmentArgs by navArgs()

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
        viewModel.loadProblem(args.problemId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
