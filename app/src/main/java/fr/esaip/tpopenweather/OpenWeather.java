package fr.esaip.tpopenweather;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class OpenWeather {
    static final String API_KEY = "5796abbde9106b7da4febfae8c44c232";


    static private String buildWeatherURL(String city) {
        return "https://api.openweathermap.org/data/2.5/weather?units=metric&lang=FR&APPID="+ API_KEY+ "&q=" + city;
    }

    static public void updateWeatherData(Context context, String city) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = buildWeatherURL(city);
        JsonObjectRequest request = new JsonObjectRequest(url, null, response -> {
            _weatherData.postValue(response);
        }, error -> {
            Log.e("OpenWeather", "Error fetching weather data", error);
        });
        queue.add(request);
    }

    private static MutableLiveData<JSONObject> _weatherData;
    public static LiveData<JSONObject> weatherData() {
        if (_weatherData == null) {
            _weatherData = new MutableLiveData<>();
        }
        return _weatherData;
    }

}
