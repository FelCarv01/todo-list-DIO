package com.felcarv.todoslist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.felcarv.todoslist.databinding.ActivityAddTaskBinding
import com.felcarv.todoslist.datasource.TaskDataSource
import com.felcarv.todoslist.extensions.format
import com.felcarv.todoslist.extensions.text
import com.felcarv.todoslist.model.Task_model
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var inflate: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(inflate.root)

        if (intent.hasExtra(TASk_ID)){
            val taskId = intent.getIntExtra(TASk_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                inflate.ipTitle.text = it.title
                inflate.ipDescription.text = it.description
                inflate.ipData.text = it.date
                inflate.ipHoras.text = it.hour
            }
        }

        insertListeners()

    }

    private fun insertListeners() {

        inflate.ipData.editText?.setOnClickListener {
            Log.e("TAG", "insertListeners: ")
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                inflate.ipData.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        inflate.ipHoras.editText?.setOnClickListener {
            Log.e("TAG", "insertListeners: ")
            val timePicker = MaterialTimePicker.Builder().setTimeFormat(
                TimeFormat.CLOCK_24H
            ).build()
            timePicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                inflate.ipHoras.text = "${hour}:${minute}"
            }
            timePicker.show(supportFragmentManager, null)
        }

        inflate.btnCriar.setOnClickListener {
            val task = Task_model(
                title = inflate.ipTitle.text,
                description = inflate.ipDescription.text,
                date = inflate.ipData.text,
                hour = inflate.ipHoras.text,
                id = intent.getIntExtra(TASk_ID, 0)
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()

        }

        inflate.btnCacelar.setOnClickListener {
            finish()

        }
    }

    companion object {
        const val TASk_ID = "task_id"
    }
}