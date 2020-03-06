package com.example.vikingtestproject

import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {


    lateinit var pDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url ="https://mcc.hu/api/app/article_types/5729fc387fdea7e267fa9761"
        AsyncTaskHandler().execute(url)


    }

    inner class AsyncTaskHandler:AsyncTask<String,String,String>()
    {

        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@MainActivity)
            pDialog.setMessage("Please Wait")
            pDialog.setCancelable(false)
            pDialog.show()
        }

        override fun doInBackground(vararg url: String?): String {

            val res:String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                res = connection.inputStream.use { it.reader().use {reader->reader.readText() } }
            }
            finally {
                connection.disconnect()
            }
            return res

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (pDialog.isShowing())
                pDialog.dismiss()
            jsonResult(result)

        }

        private fun jsonResult(jsonString:String?){

            val jsonArray = JSONArray(jsonString)
            val list = ArrayList<MyData>()
            var i = 0
            while (i<jsonArray.length())
            {
                val jsonObject = jsonArray.getJSONObject(i)
                list.add(MyData(
                    jsonObject.getString("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("subtitle"),
                    jsonObject.getString("external_link"),
                    jsonObject.getString("created"),
                    jsonObject.getString("thumbnail")
                )
                )
                i++
            }
            val adapter = ListAdapter(this@MainActivity,list)
            mylist.adapter = adapter

        }

    }

}
