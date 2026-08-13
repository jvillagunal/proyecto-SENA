package com.grigori.app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.grigori.app.R
import com.grigori.app.databinding.FragmentRegisterBinding
import com.grigori.app.data.local.AppDatabase
import com.grigori.app.data.repository.UsuarioRepository
import com.grigori.app.utils.ValidationUtils

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels {
        AuthViewModelFactory(UsuarioRepository(AppDatabase.getInstance(requireContext()).usuarioDao()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val nombre = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (!ValidationUtils.isNotEmpty(nombre)) {
                Toast.makeText(requireContext(), R.string.error_name_required, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!ValidationUtils.isValidEmail(email) || !ValidationUtils.isValidPassword(password)) {
                Toast.makeText(requireContext(), R.string.error_invalid_data, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.register(nombre, email, password)
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        viewModel.registerResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), R.string.register_success, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_dashboardFragment)
            } else {
                Toast.makeText(requireContext(), R.string.error_duplicate_account, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
