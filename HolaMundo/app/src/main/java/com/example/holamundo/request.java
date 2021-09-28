package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class request extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        sendGetRequest();
        sendPostRequest();
        sendPostJsonRequets();
    }

    public void sendGetRequest()
    {
        RequestQueue cola = Volley.newRequestQueue(this);
        String url="https://bka70s5pka.execute-api.us-east-1.amazonaws.com/beta";
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT, "GET-RESPONSE", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ASSERT, "ERROR-GET", error.toString());
            }
        });
        cola.add(request);

    }

    public void sendPostRequest()
    {
        RequestQueue cola = Volley.newRequestQueue(this);
        String url ="https://android-service-urhxsjsspa-uc.a.run.app/cars";
        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT, "POST-RESPONSE", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ASSERT, "ERROR-POST", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> data = new HashMap<>();
                data.put("marca","Hola");
                data.put("modelo","Hola");
                data.put("anio","Hola");
                return data;
            }

            @Override
            public Map<String,String> getHeaders()throws AuthFailureError{
                Map<String,String> headers= new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        cola.add(request);
    }

    public void sendPostJsonRequets()
    {
        RequestQueue cola = Volley.newRequestQueue(this);
        String url = "https://bka70s5pka.execute-api.us-east-1.amazonaws.com/beta";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.println(Log.ASSERT, "POST-JSON-RESPONSE", response.getString("response"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ASSERT, "ERROR-POST-JSON", error.toString());
                    }
                }
        );

        single.getInstance(this).addRequestCola(jsonRequest);

    }

}

class single{
    private static single instance;
    private RequestQueue cola;
    private static Context contexto;

    private single(Context context){
        contexto = context;
        cola = getRequestQueue();
    }

    public static synchronized single getInstance(Context contexto){
        if(instance== null){
            instance =new single(contexto);
        }
        return  instance;
    }

    public RequestQueue getRequestQueue(){
        if(cola == null)
        {
            cola = Volley.newRequestQueue(contexto.getApplicationContext());
        }
        return cola;
    }

    public <T> void addRequestCola(Request<T> request){
        getRequestQueue().add(request);
    }
}