package com.grigori.app.ui.auth

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
import com.grigori.app.databinding.FragmentLoginBinding
import com.grigori.app.data.local.AppDatabase
import com.grigori.app.data.repository.UsuarioRepository
import com.grigori.app.utils.ValidationUtils

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels {
        AuthViewModelFactory(UsuarioRepository(AppDatabase.getInstance(requireContext()).usuarioDao()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (!ValidationUtils.isValidEmail(email) || !ValidationUtils.isValidPassword(password)) {
                Toast.makeText(requireContext(), R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(email, password)
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            } else {
                Toast.makeText(requireContext(), R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
