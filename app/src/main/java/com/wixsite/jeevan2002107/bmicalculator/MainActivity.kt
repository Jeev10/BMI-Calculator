package com.wixsite.jeevan2002107.bmicalculator

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etWeight: EditText = findViewById(R.id.et_weight)
        val etHeight: EditText = findViewById(R.id.et_height)
        val btnGo: Button = findViewById(R.id.btn_go)
        val btnClear: Button = findViewById(R.id.btn_clear)
        val bmiNumber: TextView = findViewById(R.id.bmi_number)
        val bmiCategory: TextView = findViewById(R.id.bmi_category)


        MobileAds.initialize(this@MainActivity)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = "ca-app-pub-2224467397706763/3361960089"
        interstitialAd.loadAd(AdRequest.Builder().build())

        btnClear.setOnClickListener(){
            etWeight.setText("0.0")
            etHeight.setText("0.0")
            bmiNumber.setText("0.0")
            bmiCategory.visibility = View.INVISIBLE
            bmiNumber.visibility = View.INVISIBLE
            if(interstitialAd.isLoaded)
                interstitialAd.show()
            interstitialAd.loadAd(AdRequest.Builder().build())
        }

        btnGo.setOnClickListener(){
            var weight = etWeight.text.toString()
            var height = etHeight.text.toString()
            if(TextUtils.isEmpty(weight) ){
                etWeight.setError("Weight cannot be empty")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(height) ){
                etHeight.setError("Height cannot be empty")
                return@setOnClickListener
            }


            val weight1 = weight.toDouble()
            val height1 = height.toDouble()
            bmiCategory.visibility = View.VISIBLE
            bmiNumber.visibility = View.VISIBLE

            if(weight1 == 0.0 || height1 == 0.0) {
                Toast.makeText(
                    this,
                    "This BMI doesn't look right. Please check the weight and height you entered are correct.",
                    Toast.LENGTH_SHORT
                )
                    .show()
                bmiCategory.visibility = View.INVISIBLE
                bmiNumber.visibility = View.INVISIBLE
            }

            val bmi = (weight1*10000)/(height1*height1)
            val solution = Math.round(bmi * 10.0) / 10.0
            bmiNumber.setText("$solution")

            if(solution < 18.5)
                bmiCategory.setText("Underweight")
            else if(solution >= 18.5 && solution < 25)
                bmiCategory.setText("Normal")
            else if(solution >= 25.0 && solution < 30)
                bmiCategory.setText("Overweight")
            else
                bmiCategory.setText("Obese")
        }
    }
}