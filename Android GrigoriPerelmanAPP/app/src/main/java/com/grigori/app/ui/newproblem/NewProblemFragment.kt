package com.grigori.app.ui.newproblem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.grigori.app.R
import com.grigori.app.databinding.FragmentNewProblemBinding

class NewProblemFragment : Fragment() {
    private var _binding: FragmentNewProblemBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewProblemViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewProblemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnResolve.setOnClickListener {
            val equation = binding.etEquation.text.toString()
            if (!com.grigori.app.utils.ValidationUtils.isValidEquation(equation)) {
                Toast.makeText(requireContext(), R.string.error_invalid_equation, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.saveProblem(equation)
        }

        viewModel.saveResult.observe(viewLifecycleOwner) { id ->
            if (id > 0) {
                val bundle = bundleOf("problemId" to id.toInt())
                findNavController().navigate(R.id.action_newProblemFragment_to_solutionFragment, bundle)
            } else {
                Toast.makeText(requireContext(), R.string.error_saving_problem, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
