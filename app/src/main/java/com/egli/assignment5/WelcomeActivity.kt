package com.egli.assignment5

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egli.assignment5.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username") ?: return
        val user = UserManager.users.find { it.username == username } ?: return

        binding.tvUserInfo.text = """
            Welcome ${user.fullName}!
            Birth Year: ${user.birthYear}
            Sex: ${user.sex}
        """.trimIndent()

        binding.btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}