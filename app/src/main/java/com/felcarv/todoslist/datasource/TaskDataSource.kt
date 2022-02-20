package com.felcarv.todoslist.datasource

import com.felcarv.todoslist.model.Task_model

object TaskDataSource {
    private val list = arrayListOf<Task_model>()

    fun getList() = list.toList()
    fun insertTask(task: Task_model) {
        if(task.id == 0){
        list.add(task.copy(id = list.size + 1))
        }else{
            list.remove(task)
            list.add(task)
        }
    }

    fun findById(taskId: Int) =
        list.find { it.id == taskId }

    fun deleteTask(task: Task_model) {
    list.remove(task)
    }

}