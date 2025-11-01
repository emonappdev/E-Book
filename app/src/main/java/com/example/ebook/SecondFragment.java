package com.example.ebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class SecondFragment extends Fragment {
    ListView listView;

    LottieAnimationView animationView;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_first, container, false);

        animationView = myView.findViewById(R.id.animationView);
        listView = myView.findViewById(R.id.listView);


        loadBook();


        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);


        return myView;
    }

    //------------------------------------------------------------------------------
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView = inflater.inflate(R.layout.item, parent, false);

            TextView bookTitle = myView.findViewById(R.id.bookTitle);
            TextView bookAuthor = myView.findViewById(R.id.bookAuthor);
            ImageView bookImage = myView.findViewById(R.id.bookImage);
            CardView layOut = myView.findViewById(R.id.layOut);


            HashMap<String, String> hashMap = arrayList.get(position);
            String title = hashMap.get("title");
            String author = hashMap.get("author");
            String tam = hashMap.get("tam");
            String pdf_link = hashMap.get("pdf_link");


            bookTitle.setText(title);
            bookAuthor.setText(author);

            Picasso.get()
                    .load(tam)
                    .into(bookImage);


            layOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ViewPdf.assetNAME = pdf_link; // এখন আসল লিংক পাঠাবে
                    Intent intent = new Intent(getContext(), ViewPdf.class);
                    startActivity(intent);


                }
            });


            return myView;
        }
    }
    //------------------------------------------------------------------------------------------------------

    public void loadBook() {

        String url = "https://emondev.xyz/book/sahitto.json";
        animationView.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                animationView.setVisibility(View.GONE);

                for (int x = 0; x < response.length(); x++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(x);

                        String title = jsonObject.getString("title");
                        String author = jsonObject.getString("author");
                        String tam = jsonObject.getString("tam");
                        String pdf_link = jsonObject.getString("pdf_link");

                        hashMap = new HashMap<>();
                        hashMap.put("title", title);
                        hashMap.put("author", author);
                        hashMap.put("tam", tam);
                        hashMap.put("pdf_link", pdf_link);
                        arrayList.add(hashMap);


                        // ২ সেকেন্ডের delay দিয়ে অ্যানিমেশন hide
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                animationView.setVisibility(View.GONE);


                            }
                        }, 1500); // 2000 মিলিসেকেন্ড = ২ সেকেন্ড


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                animationView.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);


    }
    // --------------------------------------------------------------------------------------------------------
}