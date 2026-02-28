package com.example.smarthome.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.Model.Room
import com.example.smarthome.databinding.ItemRoomBinding

class RoomAdapter(private val roomList: List<Room>) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(private val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {
            binding.ivRoomIcon.setImageResource(room.iconResId)
            binding.tvRoomName.text = room.name
            binding.tvDeviceCount.text = "${room.deviceCount}个设备"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = roomList[position]
        holder.bind(room)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }
}