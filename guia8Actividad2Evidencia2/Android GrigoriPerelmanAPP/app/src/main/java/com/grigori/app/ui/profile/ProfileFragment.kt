package com.grigori.app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.grigori.app.R
import com.grigori.app.databinding.FragmentProfileBinding
import com.grigori.app.utils.SessionManager

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.profileName.observe(viewLifecycleOwner) { binding.etName.setText(it) }
        viewModel.profileEmail.observe(viewLifecycleOwner) { binding.tvEmail.text = it }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            if (name.isBlank()) {
                Toast.makeText(requireContext(), "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.updateName(name)
        }

        viewModel.updateResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                SessionManager.nombre = binding.etName.text.toString()
                Toast.makeText(requireContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "No se pudo actualizar el perfil", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loadProfile()
        binding.btnLogout.setOnClickListener {
            SessionManager.usuarioId = 0
            SessionManager.correo = ""
            SessionManager.nombre = ""
            view.findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
