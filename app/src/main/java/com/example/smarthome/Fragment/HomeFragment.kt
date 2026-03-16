package com.example.smarthome.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.smarthome.Activity.LivingRoomActivity
import com.example.smarthome.Adapter.OnRoomItemClickListener
import com.example.smarthome.Adapter.RoomAdapter
import com.example.smarthome.Model.Device
import com.example.smarthome.Model.Room
import com.example.smarthome.R
import com.example.smarthome.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), OnRoomItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var roomList: MutableList<Room>
    private lateinit var adapter: RoomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentHomeBinding.inflate(inflater,container,false)
        initView()
        return binding.root
    }

    override fun onItemClick(room: Room) {
        if (room.id == 1){
            val intent = Intent(requireContext(), LivingRoomActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initView(){

        roomList = mutableListOf(
            Room(1, "客厅", R.drawable.ic_living_room),
            Room(2, "卧室", R.drawable.ic_bedroom),
            Room(3, "厨房", R.drawable.ic_kitchen),
            Room(4, "浴室", R.drawable.ic_bathroom)
        )
        
        // 初始化RecyclerView
        adapter = RoomAdapter(roomList,this)
        binding.rvRoomList.adapter = adapter

        binding.SidebarButton.setOnClickListener {
            val drawerLayout =
                requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)

            drawerLayout.openDrawer(GravityCompat.START)
        }

    }
    
    private fun showAddDeviceDialog(){
        // 创建一个简单的AlertDialog，让用户选择添加设备的位置
        val roomOptions = roomList.map { it.name }.toTypedArray()
        
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("选择添加设备的位置")
            .setItems(roomOptions) { _, which ->
                val selectedRoom = roomList[which]
                showDeviceTypeDialog(selectedRoom)
            }
            .setNegativeButton("取消", null)
            .show()
    }
    
    private fun showDeviceTypeDialog(selectedRoom: Room){
        val deviceTypes = arrayOf("灯光", "空调", "插座", "窗帘")
        
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("选择设备类型")
            .setItems(deviceTypes) { _, which ->
                val deviceType = deviceTypes[which]
                showDeviceNameInputDialog(selectedRoom, deviceType)
            }
            .setNegativeButton("取消", null)
            .show()
    }
    
    private fun showDeviceNameInputDialog(room: Room, deviceType: String){
        val input = android.widget.EditText(requireContext())
        input.hint = "请输入设备名称"
        input.setText("${deviceType}${room.devices.size + 1}") // 默认名称
        
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("输入设备名称")
            .setView(input)
            .setPositiveButton("确定") { _, _ ->
                val deviceName = input.text.toString().trim()
                if (deviceName.isNotEmpty()) {
                    addDeviceToRoom(room, deviceType, deviceName)
                } else {
                    Toast.makeText(requireContext(), "设备名称不能为空", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }
    
    private fun addDeviceToRoom(room: Room, deviceType: String, deviceName: String){
        // 生成设备ID
        val deviceId = (100..999).random()
        // 创建设备对象
        val newDevice = Device(
            id = deviceId,
            name = deviceName,
            type = deviceType,
            roomId = room.id,
            roomName = room.name,
            status = false
        )
        
        // 添加设备到房间
        room.devices.add(newDevice)
        
        // 更新RecyclerView
        adapter.notifyDataSetChanged()
        
        // 显示添加成功提示
        Toast.makeText(requireContext(), "已在${room.name}添加$deviceName", Toast.LENGTH_SHORT).show()
    }
}