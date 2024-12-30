package com.egli.assignment5

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egli.assignment5.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupYearSpinner()

        binding.buttonRegister.setOnClickListener {
            if (validateInputs()) {
                val newUser = createUser()
                if (UserManager.isUsernameTaken(newUser.username)) {
                    Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                UserManager.users.add(newUser)
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.buttonClear.setOnClickListener {
            clearForm()
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun setupYearSpinner() {
        val years = (1900..2024).toList().reversed()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerYear.adapter = adapter
    }

    private fun validateInputs(): Boolean {
        val fullName = binding.editTextFullName.text.toString()
        val username = binding.editTextUsername.text.toString()
        val password = binding.editTextPassword.text.toString()
        val confirmPassword = binding.editTextConfirmPassword.text.toString()

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!binding.radioGroupSex.checkedRadioButtonId.let { it == R.id.radioButtonMale || it == R.id.radioButtonFemale }) {
            Toast.makeText(this, "Please select your sex", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun createUser(): User {
        return User(
            fullName = binding.editTextFullName.text.toString(),
            username = binding.editTextUsername.text.toString(),
            password = binding.editTextPassword.text.toString(),
            birthYear = binding.spinnerYear.selectedItem as Int,
            sex = if (binding.radioGroupSex.checkedRadioButtonId == R.id.radioButtonMale) "Male" else "Female"
        )
    }

    private fun clearForm() {
        binding.editTextFullName.text.clear()
        binding.editTextUsername.text.clear()
        binding.editTextPassword.text.clear()
        binding.editTextConfirmPassword.text.clear()
        binding.radioGroupSex.clearCheck()
        binding.spinnerYear.setSelection(0)
    }
}