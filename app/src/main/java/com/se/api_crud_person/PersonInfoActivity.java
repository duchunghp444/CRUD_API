package com.se.api_crud_person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class PersonInfoActivity extends AppCompatActivity {

    LinkedList<com.se.api_crud_person.Person> linkedList = new LinkedList<>();
    com.se.api_crud_person.RecycleViewApdapter adapter;
    RecyclerView recyclerView;
    Button btnBack;

    String url = "https://60adf1fe80a61f001733205f.mockapi.io/person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        mappingId();

        getDataFromJsonAPI();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonInfoActivity.this, com.se.api_crud_person.MainActivity.class));
            }
        });
    }

    private void mappingId() {
        recyclerView = findViewById(R.id.recycleView);

        btnBack = findViewById(R.id.btnBack);
    }

    private void getDataFromJsonAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(PersonInfoActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = (JSONObject) response.get(i);
                        int id = object.getInt("id");
                        String name = object.getString("name");
                        String classroom = object.getString("classroom");
                        String status = object.getString("status");
                        String working = object.getString("working");
                        linkedList.add(new com.se.api_crud_person.Person(id, name, classroom, status, working));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new com.se.api_crud_person.RecycleViewApdapter(linkedList, PersonInfoActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(PersonInfoActivity.this, 1));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonInfoActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}