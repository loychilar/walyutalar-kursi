package com.example.walyutalarkursi

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.walyutalarkursi.adapter.RvAdapter
import com.example.walyutalarkursi.databinding.ActivityMainBinding
import com.example.walyutalarkursi.models.Kurs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    lateinit var rvAdapter: RvAdapter
    lateinit var list: ArrayList<Kurs>
    private lateinit var requestQueue: RequestQueue
    lateinit var recyclerView: RecyclerView
    private lateinit var networkHelper: NetworkHelper
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)
                list = ArrayList()
                networkHelper = NetworkHelper(this)
                recyclerView = findViewById(R.id.rv)
                requestQueue = Volley.newRequestQueue(this)

                check()
            }

            private fun check() {
                if (networkHelper.isNetworkConnected()) {
                    loadMyData("https://nbu.uz/uz/exchange-rates/json/")
                } else {
                    Toast.makeText(this, "Internet mavjud emas!", Toast.LENGTH_SHORT).show()
                }
            }

            private fun loadMyData(link: String) {
                val jsonArrayRequest =
                    JsonArrayRequest(Request.Method.GET, link, null, object : Response.Listener<JSONArray> {
                        override fun onResponse(response: JSONArray?) {
                            val str = response.toString()
                            val type = object : TypeToken<ArrayList<Kurs>>() {}.type
                            list = Gson().fromJson(str, type)
                            rvAdapter = RvAdapter(this@MainActivity, list, object : RvAdapter.RvClick {
                                override fun onClick(rs: Kurs) {
                                    val dialog = AlertDialog.Builder(this@MainActivity)
                                    val view =
                                        LayoutInflater.from(this@MainActivity).inflate(R.layout.item2, null)
                                    dialog.setView(view)
                                    dialog.show()
                                    view.findViewById<Button>(R.id.btnHisoblash).setOnClickListener {
                                        view.findViewById<TextView>(R.id.textview).text = (view.findViewById<EditText>(R.id.text).text.toString().toDouble() * rs.cb_price.toDouble()).toString()
                                    }
                                    /*
                                    val bottomSheetDialog = BottomSheetDialog(this)
                            bottomSheetDialog.setContentView(layoutInflater.inflate(R.layout.item, null, false))
                            bottomSheetDialog.show()
                                     */
                                    Toast.makeText(this@MainActivity, "Ishlayapti!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            })
                            recyclerView.adapter = rvAdapter
                        }
                    }, object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError?) {


                        }
                    })
                requestQueue.add(jsonArrayRequest)
            }
        }
/*
 private fun loadData(url:String){
    val jsonArrayRequest= JsonArrayRequest(Request.Method.GET,url,null,object :Response.Listener<JsonArray>{
        override fun onResponse(response: JsonArray?) {
            val str=response.toString()
            val type=object :TypeToken<ArrayList<Kurs>>(){}.type
            list=Gson().fromJson(str,type)
            val rvAdapter=MyAdapter(this@MainActivity,list,object :MyAdapter.RvClick{
                override fun onClick(kurs: Kurs) {
                    Toast.makeText(this, "Ishlayapti!", Toast.LENGTH_SHORT).show()
                }

            })
        }
    },Response.ErrorListener {
        Toast.makeText(this, "Server hatoligi", Toast.LENGTH_SHORT).show()
    })
    requestQueue.add(jsonArrayRequest)
}
 */