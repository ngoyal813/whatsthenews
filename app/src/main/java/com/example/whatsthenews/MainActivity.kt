package com.example.whatsthenews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alan.alansdk.AlanCallback
import com.alan.alansdk.button.AlanButton

import com.alan.alansdk.AlanConfig
import com.alan.alansdk.events.EventCommand
import com.example.whatsthenews.adapter.home_rv_adapter
import com.example.whatsthenews.adapter.recycler_view_adapter
import com.example.whatsthenews.models.news_model
import org.json.JSONException
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    private var alanButton: AlanButton? = null
    val adapter1 = recycler_view_adapter()
    val adapter2 = home_rv_adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerview1 = findViewById<RecyclerView>(R.id.recycler_view1)
        val recyclerview2 = findViewById<RecyclerView>(R.id.recycler_view2)
        recyclerview1.layoutManager = LinearLayoutManager(this)
        recyclerview2.layoutManager = LinearLayoutManager(this)
        recyclerview1.adapter = adapter1
        recyclerview2.adapter = adapter2
        val config = AlanConfig.builder().setProjectId("c3322b10b70fc4c04b1796c08756e6ed2e956eca572e1d8b807a3e2338fdd0dc/stage").build()
        alanButton = findViewById(R.id.alan_button)
        alanButton?.initWithConfig(config)

        handlecommand()
    }
    fun handlecommand (){
        val myCallback: AlanCallback = object : AlanCallback() {
            override fun onCommandReceived(eventCommand: EventCommand) {
                super.onCommandReceived(eventCommand)
                try {
                    val command = eventCommand.data
                    val commandName = command.getJSONObject("data").getString("articles")
                    Log.d("AlanButton", "onCommand: commandName: ${commandName}")
                    val gson = Gson()
                    val type: List<news_model> =
                        gson.fromJson(commandName, object : TypeToken<List<news_model>>() {}.type)
                    adapter1.setNewsList(type)
                } catch (e: JSONException) {
                    Log.e("AlanButton", e.message.toString())
                }
            }
        }
        alanButton?.registerCallback(myCallback)
    }

}