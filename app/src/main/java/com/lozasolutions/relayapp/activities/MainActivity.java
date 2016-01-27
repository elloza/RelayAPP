package com.lozasolutions.relayapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.lozasolutions.relayapp.R;
import com.lozasolutions.relayapp.application.BaseApplication;
import com.lozasolutions.relayapp.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButtonRelay1, toggleButtonRelay2;
    TextView txtESP8266Status;
    MaterialDialog errorDialog;
    static int rele1;
    static int rele2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Title and subtitle
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //ToggleButtons

        toggleButtonRelay1 = (ToggleButton) findViewById(R.id.toggleButtonRelay1);
        toggleButtonRelay2 = (ToggleButton) findViewById(R.id.toggleButtonRelay2);

        toggleButtonRelay1.setEnabled(false);
        toggleButtonRelay2.setEnabled(false);

        toggleButtonRelay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (toggleButtonRelay1.isChecked()) {

                    onRelay1();

                } else {

                    offRelay1();

                }

            }
        });

        toggleButtonRelay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (toggleButtonRelay2.isChecked()) {

                    onRelay2();

                } else {

                    offRelay2();

                }

            }
        });

        //ESP8266Status

        txtESP8266Status = (TextView) findViewById(R.id.txtESP8266Status);

        String connectionStatus = getString(R.string.disconnected);
        String esp8266 = getString(R.string.esp8266);

        String esp8266Connection = esp8266 + " " + connectionStatus;
        SpannableString styledString = new SpannableString(esp8266Connection);

        //Seconds txt
        styledString.setSpan(new ForegroundColorSpan(Color.GRAY), esp8266Connection.indexOf(connectionStatus), esp8266Connection.indexOf(connectionStatus) + connectionStatus.length(), 0);

        txtESP8266Status.setText(styledString);

        //Check current status
        checkESP8266();


    }


    @Override
    protected void onStart() {
        super.onStart();

        //Default Bus Event
        EventBus.getDefault().register(this);

        //Register
        BaseApplication.getInstance().getBusWrapper().register(this);

    }

    @Override
    protected void onStop() {

        //Unregister events
        BaseApplication.getInstance().getBusWrapper().unregister(this);
        EventBus.getDefault().unregister(this);

        super.onStop();
    }


    private void checkESP8266() {

        if (BaseApplication.getInstance().getWifiConnection()) {

            String uri = Constants.STATUS_ALL_RELAY;

            StringRequest getRequest = new StringRequest(Request.Method.POST, uri,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                rele1 = jsonResponse.getInt("rele1");
                                rele2 = jsonResponse.getInt("rele2");

                                setCurrentStatus();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.printStackTrace();
                            txtESP8266Status.setText(getString(R.string.disconnected));
                            toggleButtonRelay1.setEnabled(false);
                            toggleButtonRelay2.setEnabled(false);

                            showErrorConnection();

                        }
                    }
            );

            BaseApplication.getInstance().addToRequestQueue(getRequest);

        }

    }


    private void onRelay1() {

        if (BaseApplication.getInstance().getWifiConnection()) {

            String uri = Constants.ON_RELAY_1;

            StringRequest getRequest = new StringRequest(Request.Method.POST, uri,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                rele1 = jsonResponse.getInt("rele1");
                                rele2 = jsonResponse.getInt("rele2");

                                setCurrentStatus();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.printStackTrace();
                            txtESP8266Status = (TextView) findViewById(R.id.txtESP8266Status);

                            String connectionStatus = getString(R.string.disconnected);
                            String esp8266 = getString(R.string.esp8266);

                            String esp8266Connection = esp8266 + " " + connectionStatus;
                            SpannableString styledString = new SpannableString(esp8266Connection);

                            //Seconds txt
                            styledString.setSpan(new ForegroundColorSpan(Color.GRAY), esp8266Connection.indexOf(connectionStatus), esp8266Connection.indexOf(connectionStatus) + connectionStatus.length(), 0);

                            txtESP8266Status.setText(styledString);
                            toggleButtonRelay1.setEnabled(false);
                            toggleButtonRelay2.setEnabled(false);

                            showErrorConnection();

                        }
                    }
            );

            BaseApplication.getInstance().addToRequestQueue(getRequest);

        }

    }


    private void onRelay2() {

        if (BaseApplication.getInstance().getWifiConnection()) {

            String uri = Constants.ON_RELAY_2;

            StringRequest getRequest = new StringRequest(Request.Method.POST, uri,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                rele1 = jsonResponse.getInt("rele1");
                                rele2 = jsonResponse.getInt("rele2");

                                setCurrentStatus();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.printStackTrace();

                            txtESP8266Status = (TextView) findViewById(R.id.txtESP8266Status);

                            String connectionStatus = getString(R.string.disconnected);
                            String esp8266 = getString(R.string.esp8266);

                            String esp8266Connection = esp8266 + " " + connectionStatus;
                            SpannableString styledString = new SpannableString(esp8266Connection);

                            //Seconds txt
                            styledString.setSpan(new ForegroundColorSpan(Color.GRAY), esp8266Connection.indexOf(connectionStatus), esp8266Connection.indexOf(connectionStatus) + connectionStatus.length(), 0);

                            txtESP8266Status.setText(styledString);

                            toggleButtonRelay1.setEnabled(false);
                            toggleButtonRelay2.setEnabled(false);

                            showErrorConnection();

                        }
                    }
            );

            BaseApplication.getInstance().addToRequestQueue(getRequest);

        }

    }

    private void offRelay1() {

        if (BaseApplication.getInstance().getWifiConnection()) {

            String uri = Constants.OFF_RELAY_1;

            StringRequest getRequest = new StringRequest(Request.Method.POST, uri,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                rele1 = jsonResponse.getInt("rele1");
                                rele2 = jsonResponse.getInt("rele2");

                                setCurrentStatus();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.printStackTrace();
                            txtESP8266Status = (TextView) findViewById(R.id.txtESP8266Status);

                            String connectionStatus = getString(R.string.disconnected);
                            String esp8266 = getString(R.string.esp8266);

                            String esp8266Connection = esp8266 + " " + connectionStatus;
                            SpannableString styledString = new SpannableString(esp8266Connection);

                            //Seconds txt
                            styledString.setSpan(new ForegroundColorSpan(Color.GRAY), esp8266Connection.indexOf(connectionStatus), esp8266Connection.indexOf(connectionStatus) + connectionStatus.length(), 0);

                            txtESP8266Status.setText(styledString);
                            toggleButtonRelay1.setEnabled(false);
                            toggleButtonRelay2.setEnabled(false);

                            showErrorConnection();

                        }
                    }
            );

            BaseApplication.getInstance().addToRequestQueue(getRequest);

        }

    }

    private void offRelay2() {

        if (BaseApplication.getInstance().getWifiConnection()) {

            String uri = Constants.OFF_RELAY_2;

            StringRequest getRequest = new StringRequest(Request.Method.POST, uri,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                rele1 = jsonResponse.getInt("rele1");
                                rele2 = jsonResponse.getInt("rele2");

                                setCurrentStatus();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.printStackTrace();
                            txtESP8266Status = (TextView) findViewById(R.id.txtESP8266Status);

                            String connectionStatus = getString(R.string.disconnected);
                            String esp8266 = getString(R.string.esp8266);

                            String esp8266Connection = esp8266 + " " + connectionStatus;
                            SpannableString styledString = new SpannableString(esp8266Connection);

                            //Seconds txt
                            styledString.setSpan(new ForegroundColorSpan(Color.GRAY), esp8266Connection.indexOf(connectionStatus), esp8266Connection.indexOf(connectionStatus) + connectionStatus.length(), 0);

                            txtESP8266Status.setText(styledString);
                            toggleButtonRelay1.setEnabled(false);
                            toggleButtonRelay2.setEnabled(false);

                            showErrorConnection();

                        }
                    }
            );

            BaseApplication.getInstance().addToRequestQueue(getRequest);

        }

    }

    public void showErrorConnection() {

        if (errorDialog == null || !errorDialog.isShowing()) {

            errorDialog = new MaterialDialog.Builder(this)
                    .widgetColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                    .title(R.string.dialog_network_title)
                    .content(R.string.dialog_network_content_error)
                    .positiveText(getString(R.string.dialog_network_accept))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

        }

    }


    public void setCurrentStatus() {


        toggleButtonRelay1.setEnabled(true);

        if (rele1 == 1) {

            toggleButtonRelay1.setChecked(true);

        } else {

            toggleButtonRelay1.setChecked(false);
        }

        toggleButtonRelay2.setEnabled(true);

        if (rele2 == 1) {

            toggleButtonRelay2.setChecked(true);

        } else {

            toggleButtonRelay2.setChecked(false);
        }

        txtESP8266Status = (TextView) findViewById(R.id.txtESP8266Status);

        String connectionStatus = getString(R.string.connected);
        String esp8266 = getString(R.string.esp8266);

        String esp8266Connection = esp8266 + " " + connectionStatus;
        SpannableString styledString = new SpannableString(esp8266Connection);

        //Seconds txt
        styledString.setSpan(new ForegroundColorSpan(Color.GREEN), esp8266Connection.indexOf(connectionStatus), esp8266Connection.indexOf(connectionStatus) + connectionStatus.length(), 0);

        txtESP8266Status.setText(styledString);
    }


    /**
     * This Event is raised when a change in internet connection is produced
     *
     * @param event
     */
    public void onEvent(ConnectivityChanged event) {
        ConnectivityStatus status = event.getConnectivityStatus();

        if (status.equals(ConnectivityStatus.WIFI_CONNECTED) || status.equals(ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET) || status.equals(ConnectivityStatus.WIFI_CONNECTED_HAS_INTERNET)) {

            checkESP8266();

        } else {

            txtESP8266Status.setText(getString(R.string.disconnected));
            toggleButtonRelay1.setEnabled(false);
            toggleButtonRelay2.setEnabled(false);
            showErrorConnection();

        }

    }


}
