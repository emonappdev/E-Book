package com.example.ebook;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class ViewPdf extends AppCompatActivity {

    PDFView pdfView;
    LottieAnimationView animationView;
    public static String assetNAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_pdf);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pdfView = findViewById(R.id.pdfView);
        animationView = findViewById(R.id.animationView);

        pdfView.setVisibility(View.INVISIBLE);
        animationView.setVisibility(View.VISIBLE);

        loadPdfFromUrl(assetNAME);
    }

    // ---------------- Load PDF ------------------
    private void loadPdfFromUrl(String pdfUrl) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(getMainLooper());

        executor.execute(() -> {
            InputStream inputStream = null;
            try {
                URL url = new URL(pdfUrl);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    inputStream = new BufferedInputStream(connection.getInputStream());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            InputStream finalInputStream = inputStream;
            handler.post(() -> {
                if (finalInputStream != null) {
                    pdfView.fromStream(finalInputStream)
                            .scrollHandle(new DefaultScrollHandle(ViewPdf.this))
                            .spacing(15)
                            .onLoad(nbPages -> {
                                new Handler().postDelayed(() -> {
                                    pdfView.setVisibility(View.VISIBLE);
                                    animationView.setVisibility(View.INVISIBLE);
                                }, 1000);
                            })
                            .load();
                } else {
                    animationView.setVisibility(View.GONE);
                    Toast.makeText(ViewPdf.this, "Failed to load PDF", Toast.LENGTH_SHORT).show();
                }
            });
        });


    }//  --------------------------  LoadPdfFromUrl End Here-------------------------


} // ------------------------------public class PDF End Here----------------------------------------------------------