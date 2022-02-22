package com.felcarv.todoslist.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.felcarv.todoslist.databinding.ActivityMainBinding
import com.felcarv.todoslist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {
    private lateinit var inflate: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate = ActivityMainBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        inflate.rvTasks.adapter = adapter
        updateList()

        insertListeners()

    }

    private fun insertListeners() {
        inflate.btnAddHome.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }
        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASk_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }
    private fun updateList(){
        val list = TaskDataSource.getList()
        inflate.emptyStateInclude.emptyState.visibility =  if (list.isEmpty()) View.VISIBLE
        else View.GONE
        adapter.submitList(list)
    }
    companion object{
        private const val CREATE_NEW_TASK = 1000
    }

}
