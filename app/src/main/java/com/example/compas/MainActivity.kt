package com.example.compas

import android.content.Context
import android.content.DialogInterface
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.compas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    var manager: SensorManager? = null
    var current_degree: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        createSimpleDialog()
    }

    override fun onResume() {
        super.onResume()

        manager?.registerListener(
            this,
            manager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()

        manager?.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val title = findViewById<TextView>(R.id.title)
        val imageDyn = findViewById<ImageView>(R.id.imageDimanic)

        val degree: Int = p0?.values?.get(0)?.toInt()!!
        val rotationAnim = RotateAnimation(
            current_degree.toFloat(),
            (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotationAnim.duration = 180
        rotationAnim.fillAfter = true
        imageDyn.startAnimation(rotationAnim)

        current_degree = -degree


        title.text = degree.toString()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    private fun  createSimpleDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Привет")
        builder.setMessage("Всё хорошо?")
        builder.setNeutralButton("Что?", DialogInterface.OnClickListener { dialogInterface, i ->  })
        builder.setNegativeButton("Нет", DialogInterface.OnClickListener { dialogInterface, i ->  })
        builder.setPositiveButton("Да", DialogInterface.OnClickListener { dialogInterface, i ->  })
        builder.show()
    }
}