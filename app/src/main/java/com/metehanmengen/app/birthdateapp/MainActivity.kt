package com.metehanmengen.app.birthdateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.metehanmengen.app.birthdateapp.databinding.ActivityMainBinding
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private fun getBirthDayMessage(birthDay: LocalDate, today: LocalDate) : String
    {
        var message = "Doğum gününüz kutlu olsun"

        if (birthDay.isAfter(today))
            message = "Doğum gününüz şimdiden kutlu olsun"
        else if (birthDay.isBefore(today))
            message = "Geçmiş doğum gününüz kutlu olsun"

        return message


    }

    private fun dateFormatArray(str : String) : LocalDate?
    {
        val formatters = arrayOf(DateTimeFormatter.ofPattern("dd/MM/yyyy"), DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        for (format in formatters)
                try {
                    return LocalDate.parse(str, format)
                }
                catch (ex: DateTimeException)
                {

                }
        return null
    }

    private fun okButtonClickedCallBack()
    {
        try {
            mBinding.mainActivityTextViewMessage.text = ""
            val today = LocalDate.now()
            val birthDate = dateFormatArray(mBinding.mainActivityEditTextBirthDate.text.toString())
            val birthDay = birthDate!!.withYear(today.year)

            val message = getBirthDayMessage(birthDay, today)

            val age = ChronoUnit.DAYS.between(birthDate, today) / 365.25

            "%s -> yaşınız :%.3f".format(message, age).apply {
              mBinding.mainActivityTextViewMessage.text = this
            }
        }

        catch (ex: Exception)
        {
            Toast.makeText(this, "Invalid Argument Exception", Toast.LENGTH_SHORT).show()
        }
    }

    private fun exitButtonClickedCallBack() = finish()



    private fun initViews()
    {
        mBinding.mainActivityButtonOK.setOnClickListener{okButtonClickedCallBack()}
        mBinding.mainActivityButtonEXIT.setOnClickListener{exitButtonClickedCallBack()}

    }

    private fun initBinding()
    {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

    }

    private fun initialize()
    {
        initBinding()
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }
}