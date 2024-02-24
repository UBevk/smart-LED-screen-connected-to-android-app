package com.example.myapplication

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.control_layout.*
import java.io.IOException
import java.util.*
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.marginTop
import android.widget.LinearLayout.*


class ControlActivity: AppCompatActivity() {

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog // niki je djau de tu je zdej sam za tutorial drgac je bel zajebanu
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_layout)
        m_address = intent.getStringExtra(MainActivity.EXTRA_ADDRESS).toString() // uan nima .toString()

        ConnectToDevice(this).execute()

      //  control_led_on.setOnClickListener { sendCommand("on") }
     //   control_led_off.setOnClickListener { sendCommand("off") }
       // square1.setOnClickListener { sendCommand("2") }
      //  square2.setOnClickListener { sendCommand("3") }

        fun initTextViews(ids: IntArray): Array<TextView?>? {

            val collection = arrayOfNulls<TextView>(ids.size)
            for (i in ids.indices) {
                val currentTextView = findViewById<View>(ids[i]) as TextView
                collection[i] = currentTextView
            }
            return collection
        }


        var ids = intArrayOf(
            R.id.square1, R.id.square2, R.id.square3, R.id.square4, R.id.square5, R.id.square6, R.id.square7, R.id.square8,
            R.id.square9, R.id.square10, R.id.square11, R.id.square12, R.id.square13, R.id.square14, R.id.square15, R.id.square16,
            R.id.square17, R.id.square18, R.id.square19, R.id.square20, R.id.square21, R.id.square22, R.id.square23, R.id.square24,
            R.id.square25, R.id.square26, R.id.square27, R.id.square28, R.id.square29, R.id.square30, R.id.square31, R.id.square32,
            R.id.square33, R.id.square34, R.id.square35, R.id.square36, R.id.square37, R.id.square38, R.id.square39, R.id.square40,
            R.id.square41, R.id.square42, R.id.square43, R.id.square44, R.id.square45, R.id.square46, R.id.square47, R.id.square48,
            R.id.square49, R.id.square50, R.id.square51, R.id.square52, R.id.square53, R.id.square54, R.id.square55, R.id.square56,
            R.id.square57, R.id.square58, R.id.square59, R.id.square60, R.id.square61, R.id.square62, R.id.square63, R.id.square64,
            R.id.square65, R.id.square66, R.id.square67, R.id.square68, R.id.square69, R.id.square70, R.id.square71, R.id.square72,
            R.id.square73, R.id.square74, R.id.square75, R.id.square76, R.id.square77, R.id.square78, R.id.square79, R.id.square80,
            R.id.square81, R.id.square82, R.id.square83, R.id.square84, R.id.square85, R.id.square86, R.id.square87, R.id.square88,
            R.id.square89, R.id.square90, R.id.square91, R.id.square92, R.id.square93, R.id.square94, R.id.square95, R.id.square96,
            R.id.square97, R.id.square98, R.id.square99, R.id.square100, R.id.square101, R.id.square102, R.id.square103, R.id.square104,
            R.id.square105, R.id.square106, R.id.square107, R.id.square108, R.id.square109, R.id.square110, R.id.square111,
            R.id.square112, R.id.square113, R.id.square114, R.id.square115, R.id.square116, R.id.square117, R.id.square118,
            R.id.square119, R.id.square120, R.id.square121, R.id.square122, R.id.square123, R.id.square124, R.id.square125,
            R.id.square126, R.id.square127, R.id.square128, R.id.square129, R.id.square130, R.id.square131, R.id.square132,
            R.id.square133, R.id.square134, R.id.square135, R.id.square136, R.id.square137, R.id.square138, R.id.square139,
            R.id.square140, R.id.square141, R.id.square142, R.id.square143, R.id.square144, R.id.square145, R.id.square146,
            R.id.square147, R.id.square148, R.id.square149, R.id.square150, R.id.square151, R.id.square152, R.id.square153,
            R.id.square154, R.id.square155, R.id.square156, R.id.square157, R.id.square158, R.id.square159, R.id.square160,
            R.id.square161, R.id.square162, R.id.square163, R.id.square164, R.id.square165, R.id.square166, R.id.square167,
            R.id.square168, R.id.square169, R.id.square170, R.id.square171, R.id.square172, R.id.square173, R.id.square174,
            R.id.square175, R.id.square176, R.id.square177, R.id.square178, R.id.square179, R.id.square180, R.id.square181,
            R.id.square182, R.id.square183, R.id.square184, R.id.square185, R.id.square186, R.id.square187, R.id.square188,
            R.id.square189, R.id.square190, R.id.square191, R.id.square192, R.id.square193, R.id.square194, R.id.square195,
            R.id.square196, R.id.square197, R.id.square198, R.id.square199, R.id.square200, R.id.square201, R.id.square202,
            R.id.square203, R.id.square204, R.id.square205, R.id.square206, R.id.square207, R.id.square208, R.id.square209,
            R.id.square210, R.id.square211, R.id.square212, R.id.square213, R.id.square214, R.id.square215, R.id.square216,
            R.id.square217, R.id.square218, R.id.square219, R.id.square220, R.id.square221, R.id.square222, R.id.square223,
            R.id.square224, R.id.square225, R.id.square226, R.id.square227, R.id.square228, R.id.square229, R.id.square230,
            R.id.square231, R.id.square232, R.id.square233, R.id.square234, R.id.square235, R.id.square236, R.id.square237,
            R.id.square238, R.id.square239, R.id.square240, R.id.square241, R.id.square242, R.id.square243, R.id.square244,
            R.id.square245, R.id.square246, R.id.square247, R.id.square248, R.id.square249, R.id.square250, R.id.square251,
            R.id.square252, R.id.square253, R.id.square254, R.id.square255, R.id.square256)
        val textViews = initTextViews(ids)

        var color = 0

        colorSquare1.setOnClickListener{
            color = 1
            markNotChosen()
            colorSquare1.setBackgroundResource(R.drawable.chosen_red)
        } //rdeča
        colorSquare2.setOnClickListener{
            markNotChosen()
            colorSquare2.setBackgroundResource(R.drawable.chosen_green)
            color = 2} // zelena
        colorSquare3.setOnClickListener{
            color = 3
            markNotChosen()
            colorSquare3.setBackgroundResource(R.drawable.chosen_darkblue)} // modra
        colorSquare4.setOnClickListener{
            color = 4
            markNotChosen()
            colorSquare4.setBackgroundResource(R.drawable.chosen_purple)} // vijola
        colorSquare6.setOnClickListener{
            color = 6
            markNotChosen()
            colorSquare6.setBackgroundResource(R.drawable.chosen_pink)} // roza
        colorSquare8.setOnClickListener{
            color = 8
            markNotChosen()
            colorSquare8.setBackgroundResource(R.drawable.chosen_blue)} // svetlo modra
        colorSquare9.setOnClickListener{
            color = 9
            markNotChosen()
            colorSquare9.setBackgroundResource(R.drawable.chosen_yellow)} // rumena
        colorSquare10.setOnClickListener{
            color = 10
            markNotChosen()
            colorSquare10.setBackgroundResource(R.drawable.chosen_orange)} // oranžna
        colorSquare11.setOnClickListener{
            color = 11
            markNotChosen()
            colorSquare11.setBackgroundResource(R.drawable.chosen_white)} //bela
        colorSquare12.setOnClickListener{
            color = 12
            markNotChosen()
            colorSquare12.setBackgroundResource(R.drawable.chosen_black)} //črna

        clear.setOnTouchListener(object : View.OnTouchListener {
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onTouch(v: View?, event: MotionEvent): Boolean {

                var i = 0

                while (i < 256) {
                    val id = textViews?.get(i)

                    if (id != null) {
                        id.setBackgroundColor(Color.rgb(0, 0, 0))
                    }
                    i++
                }

                sendCommand(13)
                sendCommand(13)


                return true
            }
        })

        polje.setOnTouchListener(object : View.OnTouchListener {
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onTouch(v: View?, event: MotionEvent): Boolean {

                val x = event.getRawX()
                val y = event.getRawY()


                var i = 0

                do{
                    val id = textViews?.get(i)
                    if (id != null) {
                        if(x > id.left && x < (id.left + id.layout.width) &&
                            y > (id.marginTop+230) && y < (id.marginTop+id.height+230)){
                            sendCommand(i)
                            if(color == 1){
                                id.setBackgroundColor(Color.rgb(255, 0, 0))
                                sendCommand(1)
                            }
                            else if(color == 2){
                                id.setBackgroundColor(Color.rgb(0, 255, 0))
                                sendCommand(2)
                            }
                            else if(color == 3){
                                id.setBackgroundColor(Color.rgb(0, 0, 255))
                                sendCommand(3)
                            }
                            else if(color == 4){
                            // vijola
                                id.setBackgroundColor(Color.rgb(80, 0, 255))
                                sendCommand(4)
                            }
                            else if(color == 5){
                                id.setBackgroundColor(Color.rgb(138, 98, 74))
                                sendCommand(5)
                            }

                            else if(color == 6){
                                id.setBackgroundColor(Color.rgb(210, 0, 250))
                                sendCommand(6)
                            }
                            else if(color == 7){
                                id.setBackgroundColor(Color.rgb(150, 150, 150))
                                sendCommand(7)
                            }
                            else if(color == 8){
                                id.setBackgroundColor(Color.rgb(0, 255, 255))
                                sendCommand(8)
                            }
                            else if(color == 9){
                                id.setBackgroundColor(Color.rgb(255, 255, 0))
                                sendCommand(9)
                            }
                            else if(color == 10){
                                id.setBackgroundColor(Color.rgb(255, 145, 0))
                                sendCommand(10)
                            }
                            else if(color == 11){
                                id.setBackgroundColor(Color.rgb(255, 255, 255))
                                sendCommand(11)
                            }
                            else if(color == 12){
                                id.setBackgroundColor(Color.rgb(0, 0, 0))
                                sendCommand(12)
                            }
                        }
                    }

                    i++
                }while(i<256)

                return true
            }
        })

        control_led_disconnect.setOnClickListener { disconnect() }

    }

    private fun markNotChosen(){
        colorSquare1.setBackgroundColor(Color.parseColor("#FF0000"))
        colorSquare2.setBackgroundColor(Color.parseColor("#00FF00"))
        colorSquare3.setBackgroundColor(Color.parseColor("#0000FF"))
        colorSquare4.setBackgroundColor(Color.parseColor("#5000FF"))
        colorSquare6.setBackgroundColor(Color.parseColor("#D200FA"))
        colorSquare8.setBackgroundColor(Color.parseColor("#00FFFF"))
        colorSquare9.setBackgroundColor(Color.parseColor("#FFFF00"))
        colorSquare10.setBackgroundColor(Color.parseColor("#FF9100"))
        colorSquare11.setBackgroundColor(Color.parseColor("#FFFFFF"))
        colorSquare12.setBackgroundColor(Color.parseColor("#000000"))
    }


    private fun sendCommand(input: Int){
        if(m_bluetoothSocket != null){
            try {
                m_bluetoothSocket!!.outputStream.write(input)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // se kliče ko pritisneš gumb disconnect
    private fun disconnect(){
        if(m_bluetoothSocket != null) {
          try {
              m_bluetoothSocket!!.close()
              m_bluetoothSocket = null
              m_isConnected = false
          } catch (e: IOException) {
              e.printStackTrace()
          }
        }
        finish()
    }

    // se inicializira ko se hočemo povezati na BT
    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>(){

        private var connectSuccess: Boolean = true
        private val context: Context

        // kotlin konstruktor
        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if(m_bluetoothSocket == null || !m_isConnected){
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    // vzpostavi povezavo med bt napravo in napravo na katero s hocemo povezati
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if(!connectSuccess) {
                Log.i("data", "couldn't connect")
            } else {
                m_isConnected = true
            }

            m_progress.dismiss()
        }

    }
}

