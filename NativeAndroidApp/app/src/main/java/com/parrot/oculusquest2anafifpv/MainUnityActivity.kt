package com.parrot.oculusquest2anafifpv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import com.company.product.OverrideUnityActivity

open class MainUnityActivity : OverrideUnityActivity() {
    // Setup activity layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addControlsToUnityFrame()
        val intent: Intent = intent
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
        setIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.extras == null) return
        if (intent.extras!!.containsKey("doQuit")) if (mUnityPlayer != null) {
            finish()
        }
    }

    override fun showMainActivity(setToColor: String?) {
        val intent = Intent(this, MainDroneActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("setColor", setToColor)
        startActivity(intent)
    }

    override fun onUnityPlayerUnloaded() {
        showMainActivity("")
    }

    private fun addControlsToUnityFrame() {
        val layout: FrameLayout = mUnityPlayer
        run {
            val myButton = Button(this)
            myButton.text = "Show Main"
            myButton.x = 10f
            myButton.y = 500f
            myButton.setOnClickListener { showMainActivity("") }
            layout.addView(myButton, 300, 200)
        }
        run {
            val myButton = Button(this)
            myButton.text = "Send Msg"
            myButton.x = 320f
            myButton.y = 500f
            myButton.setOnClickListener { mUnityPlayer.UnitySendMessage("Cube", "ChangeColor", "yellow") }
            layout.addView(myButton, 300, 200)
        }
        run {
            val myButton = Button(this)
            myButton.text = "Unload"
            myButton.x = 630f
            myButton.y = 500f
            myButton.setOnClickListener { mUnityPlayer.unload() }
            layout.addView(myButton, 300, 200)
        }
        run {
            val myButton = Button(this)
            myButton.text = "Finish"
            myButton.x = 630f
            myButton.y = 800f
            myButton.setOnClickListener { finish() }
            layout.addView(myButton, 300, 200)
        }
    }
}