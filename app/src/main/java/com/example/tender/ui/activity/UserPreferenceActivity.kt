package com.example.tender.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.tender.R
import com.example.tender.databinding.ActivityUserPreferenceBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class UserPreferenceActivity : AppCompatActivity() {

    /**
     * Testing out if Kotlin files can be used
     * along with Java in this project
     */

    private lateinit var binding: ActivityUserPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPreferenceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //get the intent in the target activity
        val intent = intent
        binding.userPrefTv.text = intent.getStringExtra("tender")

        displayMessage()

    }

    private fun displayMessage() {
        binding.userPrefBtn.setOnClickListener {

            MotionToast.createColorToast(
                this,
                "Matching Successful",
                "You can now swipe with your friends",
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this, R.font.helvetica_regular)
            )

        }
    }
}

