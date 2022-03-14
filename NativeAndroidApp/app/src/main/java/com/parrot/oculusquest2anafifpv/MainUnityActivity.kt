package com.parrot.oculusquest2anafifpv

import android.widget.Button
import com.PrometheonTechnologies.OculusAnafiFPV.OverrideUnityActivity

class MainUnityActivity : OverrideUnityActivity() {
    // Setup activity layout
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addControlsToUnityFrame()
        val intent: Intent = getIntent()
        handleIntent(intent)
    }

    protected fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
        setIntent(intent)
    }

    fun handleIntent(intent: Intent?) {
        if (intent == null || intent.getExtras() == null) return
        if (intent.getExtras().containsKey("doQuit")) if (mUnityPlayer != null) {
            finish()
        }
    }

    protected fun showMainActivity(setToColor: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra("setColor", setToColor)
        startActivity(intent)
    }

    fun onUnityPlayerUnloaded() {
        showMainActivity("")
    }

    fun addControlsToUnityFrame() {
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

    fun onBackPressed() {
        if (mUnityPlayer != null) {
            mUnityPlayer.UnitySendMessage("Cube", "ChangeColor", "red")
        }
        super.onBackPressed()
    }
}