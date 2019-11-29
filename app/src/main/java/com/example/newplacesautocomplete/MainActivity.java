package com.example.newplacesautocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    PlacesClient placesClient;
    AutocompleteSessionToken token;
    private String API_KEY_ID="Your google api key";
    private AutoCompleteTextView autocomplete_place;
    private PlaceAutocompleteAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the SDK
        Places.initialize(getApplicationContext(),API_KEY_ID);
        // Create a new Places client instance
        placesClient = Places.createClient(this);
        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        token = AutocompleteSessionToken.newInstance();

        autocomplete_place=findViewById(R.id.autocomplete_place);

        mCreatRequest(placesClient,token);
    }

    private void mCreatRequest(PlacesClient placesClient, AutocompleteSessionToken token) {
        mAdapter = new PlaceAutocompleteAdapter(this,placesClient,token);
        autocomplete_place.setAdapter(mAdapter);
    }

    /////////////////////////////////////////////////////

    private AdapterView.OnItemClickListener mAutocompleteClickListener;

    {
        mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */

                final AutocompletePrediction item = mAdapter.getItem(position);
                final String placeId = item.getPlaceId();
                final CharSequence primaryText = item.getPrimaryText(null);
                Toast.makeText(MainActivity.this,primaryText.toString(),Toast.LENGTH_SHORT).show();

            }
        };
    }
}
