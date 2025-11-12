package fr.esaip.tpopenweather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import fr.esaip.tpopenweather.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView latitude = binding.idLatitude;
        TextView longitude = binding.idLongitude;
        TextView description = binding.idDescription;
        TextView temperature = binding.idTemperature;
        TextView temperature_min = binding.idTemperatureMin;
        TextView temperature_max = binding.idTemperatureMax;
        TextView vitesse_vent = binding.idVitesseVent;
        TextView pressure = binding.idPression;
        TextView humidity = binding.idHumidite;
        TextView direction_vent = binding.idDirectionVent;

        // Activity is a LifecycleOwner, use `this` instead of `viewLifecycleOwner` (that's for Fragments)
        OpenWeather.weatherData().observe(this, weatherData -> {
            try {
                JSONObject coord = weatherData.getJSONObject("coord");
                JSONObject main = weatherData.getJSONObject("main");
                JSONObject wind = weatherData.getJSONObject("wind");
                JSONObject weather = weatherData.getJSONArray("weather").getJSONObject(0);

                latitude.setText(""+coord.getDouble("lat"));
                longitude.setText("" + coord.getDouble("lon"));
                description.setText("" + weather.getString("description"));
                temperature.setText("" + main.getDouble("temp"));
                temperature_min.setText("" + main.getDouble("temp_min"));
                temperature_max.setText("" + main.getDouble("temp_max"));
                vitesse_vent.setText("" + wind.getDouble("speed"));
                pressure.setText("" + main.getInt("pressure"));
                humidity.setText("" + main.getInt("humidity"));
                direction_vent.setText("" + wind.getInt("deg"));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Erreur lors de la récupération des données météo", Toast.LENGTH_LONG).show();
            }
        });

        OpenWeather.updateWeatherData(this, "Angers");
    }
}
