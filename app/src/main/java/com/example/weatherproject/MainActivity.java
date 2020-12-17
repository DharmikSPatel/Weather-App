package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    String quoteSunny = "\"Do you know, I believe we should all behave quite differently if we lived in a warm, sunny climate all the time. We shouldn't be so withdrawn and shy and difficult.\"\nBrief Encounter";
    String quoteClouds = "\"It says here that the water planet has so much moisture on its surface that it continuously creates cycles of clouds and falling rain.\"\nThe Ice Pirates";
    String quoteRain = "\"It was the rain that defeated every invader. Yes, simple rain.\"\nThe Englishman Who Went up a Hill but Came down a Mountain";
    String quoteSnow = "\"Anything can happen on a snow day.\"\nSnow Day";
    String quoteFog = "\"I can't wait to leave this island. If it's not raining, it's snowing, and if it's not snowing, it's foggy.\"\nKing Arthur";
    String quoteWind = "\"I don't predict it. Nobody does, 'cause i-it's just rain. It's rain. It rains all over the place!\"\nThe Weather Man";
    String quoteThunder = "\"Flight, we are looking at a thunderstorm warning on the edge of the prime recovery zone. Now, this is just a warning.\"\nApollo 13";
    String quoteClear = "\"There's no such thing as good weather, or bad weather. There's just weather and your attitude towards it.\"\nLouise Hay";
    String quoteAnyOtherWeather1 = "\"You're like the weather, you just happen.\"\nThe Avalanche";
    String quoteAnyOtherWeather2 = "\"Good, because I’m looking for a fellow scientist. To understand the weather, Miss Wren, is to understand how to make ships and sailors safer, farms more productive, so we can prepare ourselves and our world for floods, for droughts, famines. We could save thousands of lives.\"\n The Aeronauts";

    JSONObject myResponse;
    EditText editText;
    String editTextwords;
    Button GO;
    String zipcode;
    String temp;

    TextView bigDesc;
    TextView bigTemp;
    TextView bigDate;
    ImageView bigImage;
    TextView quote;

    Time one;
    TextView firstHig;
    TextView firstLow;
    TextView firstTime;
    LinearLayout firstLL;
    ImageView firstImage;

    Time sec;
    TextView secHig;
    TextView secLow;
    TextView secTime;
    LinearLayout secLL;
    ImageView secImage;

    Time thr;
    TextView thirdHig;
    TextView thirdLow;
    TextView thirdTime;
    LinearLayout thirdLL;
    ImageView thirdImage;

    Time four;
    TextView fourHig;
    TextView fourLow;
    TextView fourTime;
    LinearLayout fourLL;
    ImageView fourImage;

    Time five;
    TextView fiveHig;
    TextView fiveLow;
    TextView fiveTime;
    LinearLayout fiveLL;
    ImageView fiveImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zipcode = "08852";
        editText = findViewById(R.id.id_editText);
        GO = findViewById(R.id.button);
        bigDesc = findViewById(R.id.id_bigdescription);
        bigTemp = findViewById(R.id.id_bigTemp);
        bigDate = findViewById(R.id.id_bigDate);
        bigImage = findViewById(R.id.id_bigImage);
        quote = findViewById(R.id.id_textQoute);

        firstHig = findViewById(R.id.id_texthighmon);
        firstLow = findViewById(R.id.id_textlowmon);
        firstTime = findViewById(R.id.id_time1);
        firstLL = findViewById(R.id.id_LLfirst);
        firstImage = findViewById(R.id.id_imageMon);

        secHig = findViewById(R.id.id_texthightue);
        secLow = findViewById(R.id.id_textlowtue);
        secTime = findViewById(R.id.id_time2);
        secLL = findViewById(R.id.id_LLsec);
        secImage = findViewById(R.id.id_imageTue);

        thirdHig = findViewById(R.id.id_texthighwed);
        thirdLow = findViewById(R.id.id_textlowwed);
        thirdTime = findViewById(R.id.id_time3);
        thirdLL = findViewById(R.id.id_LLthir);
        thirdImage = findViewById(R.id.id_imageWed);

        fourHig = findViewById(R.id.id_texthighthu);
        fourLow = findViewById(R.id.id_textlowthu);
        fourTime = findViewById(R.id.id_time4);
        fourLL = findViewById(R.id.id_LLfour);
        fourImage = findViewById(R.id.id_imageThu);

        fiveHig = findViewById(R.id.id_texthighfri);
        fiveLow = findViewById(R.id.id_textlowfri);
        fiveTime = findViewById(R.id.id_time5);
        fiveLL = findViewById(R.id.id_LLfive);
        fiveImage = findViewById(R.id.id_imageFri);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                zipcode = s.toString();
            }
        });
        GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!zipcode.equals(temp)) {
                    Log.d("TAG", "zip: " + zipcode);
                    AsyncThread myt = new AsyncThread();
                    myt.execute(zipcode);
                    temp = zipcode;
                }
            }
        });
        AsyncThread myt = new AsyncThread();
        myt.execute(zipcode);


    }
    public double kelvinToFar(double K){
        double C = K - 273.15;
        return (C * 9.0/5.0) +32.0;

    }
    public class AsyncThread extends AsyncTask<String, Void, Void> {    //<Something that is passed in to be used by Async, Something that shows progress, what is returned>must be the return type of doItInBackgrounf
        @Override
        protected Void doInBackground(String... strings){               //array of Strings
            try {
                String zipcode = strings[0];                            //String used in AsyncTask
                URL mainUrl = new URL("https://api.openweathermap.org/data/2.5/forecast?zip="+zipcode+",us&APPID=2afc94a169d586b0a241bdd14e23b137");
                Log.d("TAG", mainUrl.toString());
                URLConnection urlConnection = mainUrl.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null)
                    response.append(inputLine);
                br.close();
                myResponse = new JSONObject(response.toString());
                editTextwords = ""+myResponse.getJSONObject("city").getString("name");
            }catch (Exception e){
                Log.d("TAG","ERROR");
                editTextwords = "Enter a zipcode";
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            editText.setText(editTextwords);

            one = new Time(0);
            firstHig.setText(""+one.highTemp+"°");
            firstLow.setText(""+one.lowTemp+"°");
            firstTime.setText(""+one.time);
            firstImage.setImageResource(one.image);

            sec = new Time(1);
            secHig.setText(""+sec.highTemp+"°");
            secLow.setText(""+sec.lowTemp+"°");
            secTime.setText(""+sec.time);
            secImage.setImageResource(sec.image);

            thr = new Time(2);
            thirdHig.setText(""+thr.highTemp+"°");
            thirdLow.setText(""+thr.lowTemp+"°");
            thirdTime.setText(""+thr.time);
            thirdImage.setImageResource(thr.image);

            four = new Time(3);
            fourHig.setText(""+four.highTemp+"°");
            fourLow.setText(""+four.lowTemp+"°");
            fourTime.setText(""+four.time);
            fourImage.setImageResource(four.image);

            five = new Time(4);
            fiveHig.setText(""+five.highTemp+"°");
            fiveLow.setText(""+five.lowTemp+"°");
            fiveTime.setText(""+five.time);
            fiveImage.setImageResource(five.image);

            bigDesc.setText(""+one.des);
            bigTemp.setText(""+one.bigTemp+"°");
            bigDate.setText(""+one.date);
            bigImage.setImageResource(one.image);
            quote.setText(one.qoute);
            firstLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bigDesc.setText(""+one.des);
                    bigTemp.setText(""+one.bigTemp+"°");
                    bigDate.setText(""+one.date);
                    bigImage.setImageResource(one.image);
                    quote.setText(one.qoute);
                }
            });
            secLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bigDesc.setText(""+sec.des);
                    bigTemp.setText(""+sec.bigTemp+"°");
                    bigDate.setText(""+sec.date);
                    bigImage.setImageResource(sec.image);
                    quote.setText(sec.qoute);


                }
            });
            thirdLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bigDesc.setText(""+thr.des);
                    bigTemp.setText(""+thr.bigTemp+"°");
                    bigDate.setText(""+thr.date);
                    bigImage.setImageResource(thr.image);
                    quote.setText(thr.qoute);

                }
            });
            fourLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bigDesc.setText(""+four.des);
                    bigTemp.setText(""+four.bigTemp+"°");
                    bigDate.setText(""+four.date);
                    bigImage.setImageResource(four.image);
                    quote.setText(four.qoute);

                }
            });
            fiveLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bigDesc.setText(""+five.des);
                    bigTemp.setText(""+five.bigTemp+"°");
                    bigDate.setText(""+five.date);
                    bigImage.setImageResource(five.image);
                    quote.setText(five.qoute);

                }
            });

        }
    }
    public class Time{
        int bigTemp;
        String highTemp;
        String lowTemp;
        int epoch;
        String date_time;
        String date;
        String time;
        String des;
        int image;
        String qoute;
        @SuppressLint({"DefaultLocale", "SimpleDateFormat"})
        public Time(int index){
            try {
                Log.d("TAG",String.format("%.2f", kelvinToFar(myResponse.getJSONArray("list").getJSONObject(index).getJSONObject("main").getDouble("temp_max"))));
                highTemp = String.format("%.2f", kelvinToFar(myResponse.getJSONArray("list").getJSONObject(index).getJSONObject("main").getDouble("temp_max")));
                lowTemp = String.format("%.2f", kelvinToFar(myResponse.getJSONArray("list").getJSONObject(index).getJSONObject("main").getDouble("temp_min")));
                //highTemp = ""+(int)(Math.round(Double.parseDouble(highTemp)));
                //lowTemp = ""+(int)(Math.round(Double.parseDouble(lowTemp)));
                epoch = myResponse.getJSONArray("list").getJSONObject(index).getInt("dt");
                date_time = new SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(new Date(epoch * 1000L));
                date = date_time.substring(0, 10);
                time = date_time.substring(11);
                des = myResponse.getJSONArray("list").getJSONObject(index).getJSONArray("weather").getJSONObject(0).getString("main");
                bigTemp = (int)(Math.round(Double.parseDouble(highTemp)));
                String icon = myResponse.getJSONArray("list").getJSONObject(index).getJSONArray("weather").getJSONObject(0).getString("icon");
                Log.d("TAG", epoch+" "+icon);
                switch (icon) {
                    case "01d":
                        image = R.drawable.d01;
                        qoute = quoteSunny;
                        break;
                    case "02d":
                        image = R.drawable.d02;
                        qoute = quoteClouds;
                        break;
                    case "03d":
                        image = R.drawable.d03;
                        qoute = quoteClouds;
                        break;
                    case "04d":
                        image = R.drawable.d04;
                        qoute = quoteClouds;
                        break;
                    case "09d":
                        image = R.drawable.d09;
                        qoute = quoteRain;
                        break;
                    case "10d":
                        image = R.drawable.d10;
                        qoute = quoteWind;
                        break;
                    case "11d":
                        image = R.drawable.d11;
                        qoute = quoteThunder;
                        break;
                    case "13d":
                        image = R.drawable.d13;
                        qoute = quoteSnow;
                        break;
                    case "50d":
                        image = R.drawable.d50;
                        qoute = quoteFog;
                        break;
                    case "01n":
                        image = R.drawable.n01;
                        qoute = quoteClear;
                        break;
                    case "02n":
                        image = R.drawable.n02;
                        qoute = quoteClouds;
                        break;
                    case "03n":
                        image = R.drawable.n03;
                        qoute = quoteClouds;
                        break;
                    case "04n":
                        image = R.drawable.n04;
                        qoute = quoteClouds;
                        break;
                    case "09n":
                        image = R.drawable.n09;
                        break;
                    case "10n":
                        image = R.drawable.n10;
                        qoute = quoteRain;
                        break;
                    case "11n":
                        image = R.drawable.n11;
                        qoute = quoteThunder;
                        break;
                    case "13n":
                        image = R.drawable.n13;
                        qoute = quoteSnow;
                        break;
                    case "50n":
                        image = R.drawable.n50;
                        qoute = quoteFog;
                        break;
                    default:
                        if (Math.random() > .5)
                            qoute = quoteAnyOtherWeather1;
                        else
                            qoute = quoteAnyOtherWeather2;
                        break;
                }

            }catch (Exception e){
                Log.d("TAG","Nodata1");
            }
        }
    }
}
