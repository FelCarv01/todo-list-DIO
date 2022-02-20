package com.felcarv.todoslist.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felcarv.todoslist.R
import com.felcarv.todoslist.databinding.ItemListBinding
import com.felcarv.todoslist.model.Task_model

class TaskListAdapter : ListAdapter<Task_model, TaskListAdapter.TaskViewHolder>(DiffCallBack()) {
    var listenerEdit: (Task_model) -> Unit = { }
    var listenerDelete: (Task_model) -> Unit = { }

    inner class TaskViewHolder(private val inflate: ItemListBinding) :
        RecyclerView.ViewHolder(inflate.root) {

        fun bind(item: Task_model) {
            inflate.tvTitle.text = item.title
            inflate.tvDateHora.text = "${item.date}  ${item.hour}"
            inflate.btnMore.setOnClickListener {
                showPopup(item)
            }

        }

        private fun showPopup(item: Task_model) {
            val btnMore = inflate.btnMore
            val popupMenu = PopupMenu(
                btnMore.context,
                btnMore
            )
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflate = ItemListBinding.inflate(inflater, parent, false)
        return TaskViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DiffCallBack : DiffUtil.ItemCallback<Task_model>() {
    override fun areItemsTheSame(oldItem: Task_model, newItem: Task_model) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task_model, newItem: Task_model) =
        oldItem.id == newItem.id

}