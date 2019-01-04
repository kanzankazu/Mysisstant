package id.co.halloarif.catatanku.view.activity.Support;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import id.co.halloarif.catatanku.ISeasonConfig;
import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.SystemUtil;
import id.co.halloarif.catatanku.view.adapter.PlacesAutoCompleteAdapter;

public class PlacesAutoCompleteActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    protected GoogleApiClient mGoogleApiClient;

    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(-0, 0), new LatLng(0, 0));

    //MapsModel mapsModel = new MapsModel();

    private EditText etPlaceAutoCompleteInputfvbi;
    private ImageView ivPlaceAutoCompleteDeletefvbi;
    private RecyclerView rvPlaceAutoCompletefvbi;
    private ImageView ivPlaceAutoCompleteMarkerfvbi;
    private CardView cvPlaceAutoCompleteDonefvbi;
    private TextView tvPlaceAutoCompleteDoneLocNmfvbi;
    private TextView tvPlaceAutoCompleteDoneLocLatfvbi;
    private TextView tvPlaceAutoCompleteDoneLocLngfvbi;

    private LinearLayoutManager mLinearLayoutManager;
    private PlacesAutoCompleteAdapter PlaceAutoCompleteAdapter;

    private GoogleMap mMap;
    private MarkerOptions marker;
    private LatLngBounds.Builder builder;
    private double mylat;
    private double mylng;
    private LatLng mylatlng;

    private String locationName = "";
    private double locationLat = 0;
    private double locationLng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        setContentView(R.layout.activity_place_autocomplete);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initMap();
        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapPlaceAutoComplete);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                if (ContextCompat.checkSelfPermission(PlacesAutoCompleteActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(PlacesAutoCompleteActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                getMyLocation();
            }
        });
    }

    private void initComponent() {
        etPlaceAutoCompleteInputfvbi = (EditText) findViewById(R.id.etPlaceAutoCompleteInput);
        ivPlaceAutoCompleteDeletefvbi = (ImageView) findViewById(R.id.ivPlaceAutoCompleteDelete);
        rvPlaceAutoCompletefvbi = (RecyclerView) findViewById(R.id.rvPlaceAutoComplete);
        ivPlaceAutoCompleteMarkerfvbi = (ImageView) findViewById(R.id.ivPlaceAutoCompleteMarker);

        cvPlaceAutoCompleteDonefvbi = (CardView) findViewById(R.id.cvPlaceAutoCompleteDone);
        tvPlaceAutoCompleteDoneLocNmfvbi = (TextView) findViewById(R.id.tvPlaceAutoCompleteDoneLocNm);
        tvPlaceAutoCompleteDoneLocLatfvbi = (TextView) findViewById(R.id.tvPlaceAutoCompleteDoneLocLat);
        tvPlaceAutoCompleteDoneLocLngfvbi = (TextView) findViewById(R.id.tvPlaceAutoCompleteDoneLocLng);

    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {
        PlaceAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this, R.layout.list_places_autocomplete, mGoogleApiClient, BOUNDS_INDIA, null);
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvPlaceAutoCompletefvbi.setLayoutManager(mLinearLayoutManager);
        rvPlaceAutoCompletefvbi.setAdapter(PlaceAutoCompleteAdapter);
        rvPlaceAutoCompletefvbi.setVisibility(View.GONE);
    }

    private void initListener() {
        ivPlaceAutoCompleteDeletefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPlaceAutoCompleteInputfvbi.setText("");
            }
        });
        etPlaceAutoCompleteInputfvbi.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mGoogleApiClient.isConnected()) {
                    if (!s.toString().equals("")) {
                        PlaceAutoCompleteAdapter.getFilter().filter(s.toString());
                        rvPlaceAutoCompletefvbi.setVisibility(View.VISIBLE);
                        ivPlaceAutoCompleteDeletefvbi.setVisibility(View.VISIBLE);
                    } else {
                        PlaceAutoCompleteAdapter.getFilter().filter(s.toString());
                        rvPlaceAutoCompletefvbi.setVisibility(View.GONE);
                        ivPlaceAutoCompleteDeletefvbi.setVisibility(View.GONE);
                    }
                } else if (!mGoogleApiClient.isConnected()) {
                    Toast.makeText(getApplicationContext(), PlacesAutoCompleteConstants.API_NOT_CONNECTED, Toast.LENGTH_SHORT).show();
                    Log.e(PlacesAutoCompleteConstants.PlacesTag, PlacesAutoCompleteConstants.API_NOT_CONNECTED);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        etPlaceAutoCompleteInputfvbi.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_GO) {
                    SystemUtil.hideKeyBoard(PlacesAutoCompleteActivity.this);
                }
                return handled;
            }
        });

        rvPlaceAutoCompletefvbi.addOnItemTouchListener(new PlacesAutoCompleteRecyclerItemClickListener(this, new PlacesAutoCompleteRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SystemUtil.hideKeyBoard(PlacesAutoCompleteActivity.this);

                final PlacesAutoCompleteAdapter.PlaceAutocomplete item = PlaceAutoCompleteAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i("TAG", "Autocomplete item selected: " + item.description);
                        /*
                             Issue a request to the Places Geo Data API to retrieve a Place object with additional details about the place.
                         */

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getCount() == 1) {
                            //Do the things here on Click.....
                            Place place = places.get(0);
                            Toast.makeText(getApplicationContext(), String.valueOf(places.get(0).getLatLng()), Toast.LENGTH_SHORT).show();
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getLatLng().latitude);
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getLatLng().longitude);
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getId());
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getAddress());
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getAttributions());
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getLocale());
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getName());
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getPhoneNumber());
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getPlaceTypes());
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getPriceLevel());
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getRating());
                            Log.d("Lihat", "onResult PlacesAutoCompleteActivity : " + places.get(0).getViewport());

                            etPlaceAutoCompleteInputfvbi.setText("");

                            locationName = places.get(0).getName().toString() + " , " + places.get(0).getAddress().toString();
                            locationLat = places.get(0).getLatLng().latitude;
                            locationLng = places.get(0).getLatLng().longitude;

                            cvPlaceAutoCompleteDonefvbi.setVisibility(View.VISIBLE);
                            tvPlaceAutoCompleteDoneLocNmfvbi.setText(locationName);
                            tvPlaceAutoCompleteDoneLocLatfvbi.setText(locationLat+"");
                            tvPlaceAutoCompleteDoneLocLngfvbi.setText(locationLng+"");

                        } else {
                            Toast.makeText(getApplicationContext(), PlacesAutoCompleteConstants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Log.i("TAG", "Clicked: " + item.description);
                Log.i("TAG", "Called getPlaceById to get Place details for " + item.placeId);
            }
        }));
    }

    private void getMyLocation() {
        if (mylat == 0.00 && mylng == 0.00) {
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    mylat = location.getLatitude();
                    mylng = location.getLongitude();
                    mylatlng = new LatLng(mylat, mylng);//onMyLocationChange
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, 15));
                    mMap.setOnMyLocationChangeListener(null);
                    Log.d("Lihat", "onMyLocationChange MainMapsActivity : " + mylatlng);
                }
            });
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            mylat = location.getLatitude();
            mylng = location.getLongitude();
            mylatlng = new LatLng(mylat, mylng);//getMyLocation
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, 15));
            Log.d("Lihat", "getMyLocation MainMapsActivity : " + mylatlng);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("Google API Callback", "Connection Done");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("Google API Callback", "Connection Suspended");
        Log.v("Code", String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v("Google API Callback", "Connection Failed");
        Log.v("Error Code", String.valueOf(connectionResult.getErrorCode()));
        Toast.makeText(this, PlacesAutoCompleteConstants.API_NOT_CONNECTED, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()) {
            Log.v("Google API", "Connecting");
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            Log.v("Google API", "Dis-Connecting");
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuCheckActivity) {
            Intent intent = new Intent();
            intent.putExtra(ISeasonConfig.INTENT_PARAM_MAP_NAME, locationName);
            intent.putExtra(ISeasonConfig.INTENT_PARAM_MAP_LAT, locationLat);
            intent.putExtra(ISeasonConfig.INTENT_PARAM_MAP_LONG, locationLng);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
