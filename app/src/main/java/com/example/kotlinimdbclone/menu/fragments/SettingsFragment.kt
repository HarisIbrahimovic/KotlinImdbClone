package com.example.kotlinimdbclone.menu.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.kotlinimdbclone.databinding.FragmentSettingsBinding
import com.example.kotlinimdbclone.main.MainActivity
import com.example.kotlinimdbclone.menu.MenuViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MenuViewModel by activityViewModels()

    private var firebaseStorage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var imageUri: Uri? = null
    private var imageUrl: String? = null

    private val startUpload =registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imageUri = it
        uploadPicture()
        Toast.makeText(requireContext(),"Please wait..",Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userData.observe(viewLifecycleOwner, { userData ->
            if (userData.id == "null") {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                binding.settingPassword.setText(userData.password)
                binding.settingsName.setText(userData.username)
                binding.settingEmail.setText(userData.email)
            }
        })
        binding.settingPassword.setOnEditorActionListener(editorActionListener)
        binding.settingEmail.setOnEditorActionListener(editorActionListener)
        binding.settingsName.setOnEditorActionListener(editorActionListener)

        binding.uploadButton.setOnClickListener{
            chosePicture()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private val editorActionListener = TextView.OnEditorActionListener { _, _, _ ->
        if( viewModel.updateUser(
                binding.settingsName.text.toString(),
                binding.settingEmail.text.toString(),
                binding.settingPassword.text.toString()
            ))
            Toast.makeText(requireContext(), "Profile updated.", Toast.LENGTH_SHORT).show()
        else  Toast.makeText(requireContext(), "Problems...", Toast.LENGTH_SHORT).show()
        true
    }

    private fun chosePicture() {
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = firebaseStorage!!.reference
        startUpload.launch("image/*")
    }

    private fun uploadPicture() {
        if(imageUri==null)return
        val randomKey: String = UUID.randomUUID().toString()
        val ref: StorageReference = storageReference!!.child("images/$randomKey")
        ref.putFile(imageUri!!).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { uri ->
                imageUrl = uri.toString()
                viewModel.updatePicutre(imageUrl!!)
            }
        }
    }
}