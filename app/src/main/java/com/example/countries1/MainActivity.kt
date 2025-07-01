package com.example.countries1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.countries1.ui.theme.Countries1Theme
import androidx.compose.ui.platform.LocalContext
import com.example.countries1.data.CountryDetails

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Countries1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    displayCountryView()
                }
            }
        }
    }
}


fun getJSONData(countryList: MutableList<CountryDetails>, ctx: Context) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/")

        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

    val call: Call<ArrayList<CountryDetails>> = retrofitAPI.getCountryDetails()
    call!!.enqueue(object : Callback<ArrayList<CountryDetails>?> {
        override fun onResponse(
            call: Call<ArrayList<CountryDetails>?>,
            response: Response<ArrayList<CountryDetails>?>
        ) {

            if (response.isSuccessful) {
                var list: ArrayList<CountryDetails> = ArrayList()

                list = response.body()!!

                for (i in 0 until list.size) {
                    countryList.add(list.get(i))
                }
            }
        }

        override fun onFailure(call: Call<ArrayList<CountryDetails>?>, t: Throwable) {
            // error message
            Toast.makeText(ctx, "Fail to get the data.", Toast.LENGTH_SHORT)
                .show()
        }
    })
}


@Composable
fun ListItem(name : String, region : String, code  : String, capital : String, ){

    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 0.dp, horizontal = 1.dp)){

        //HorizontalDivider(thickness = 2.dp)

        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 2f)
        Canvas(Modifier.fillMaxWidth().height(2.dp)) {

            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(size.width -5f, 0f),
                pathEffect = pathEffect
            )
        }

        Column(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()) {

            Row{

                //VerticalDivider(thickness = 2.dp)

                Column(
                    modifier = Modifier
                        .weight(5f)
                ) {
                    Text(text = "|")
                }

                Column(
                    modifier = Modifier
                        .weight(5f)
                ) {
                    Text(text = "|")
                }

            }



            Row{

                Column(
                    modifier = Modifier
                        .weight(0.1f)
                ) {
                    Text(
                        text = "|",
                        modifier = Modifier
                            .align(Alignment.Start),
                    )


                }

                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth()
                ) {
                    Text(name + ", " + region)
                }

                //VerticalDivider(color = MaterialTheme.colorScheme.primary)

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text = code + "  |", style = MaterialTheme.typography.bodyLarge.copy(
                    ))
                }
            }


            Row{
                //VerticalDivider(thickness = 2.dp)

                Column(
                    modifier = Modifier
                        .weight(5f)
                ) {
                    Text(text = "|")
                }

                Column(
                    modifier = Modifier
                        .weight(5f)
                ) {
                    Text(text = "|")
                }

            }


            Row{

                Column(
                    modifier = Modifier
                        .weight(0.1f)
                ) {
                    Text(
                        text = "|",
                        modifier = Modifier
                            .align(Alignment.Start),
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(3f)
                ) {
                    Text(text = capital)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text =  "  |", style = MaterialTheme.typography.bodyLarge.copy(
                    ))
                }
            }

            Row{

                Column(
                    modifier = Modifier
                        .weight(5f)
                ) {
                    Text(text = "|")
                }

                Column(
                    modifier = Modifier
                        .weight(5f)
                ) {
                    Text(text = "|")
                }

            }


        }

    }
}


@Composable
fun displayCountryView() {
    val context = LocalContext.current

    val countryList = remember { mutableStateListOf<CountryDetails>() }
    getJSONData(countryList, context)

    LazyColumn {
        items(countryList) { country ->
            ListItem(name = country.name, region = country.region, code = country.code, capital = country.capital)
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val context = LocalContext.current


    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Countries1Theme {
        Greeting("Android")
    }
}