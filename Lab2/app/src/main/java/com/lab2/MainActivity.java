package com.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.lab2.fragment.ListFragment;
import com.lab2.fragment.PagerFragment;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {
    public static int currentPosition;
    private static final String KEY_CURRENT_POSITION = "KEY_CURRENT_POSITION";
    private static final String DATA_URL = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            startActivity(new Intent(this, SplashActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0);

            loadData();
            return;
        }

        loadData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_POSITION, currentPosition);
    }

    private void loadData() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                DATA_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                response.remove(0); // get rid of metadata on item 0

                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                ListFragment fragment = new ListFragment();
                fragment.setData(response);

                fragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                        .commitAllowingStateLoss();

                MainActivity.this.sendBroadcast(new Intent("close_splash"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                MainActivity.this.sendBroadcast(new Intent("close_splash"));
                Toast.makeText(MainActivity.this.getApplicationContext(), "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
