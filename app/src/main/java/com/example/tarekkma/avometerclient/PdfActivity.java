package com.example.tarekkma.avometerclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;

public class PdfActivity extends AppCompatActivity {

    public static final String KEY_PATH = "PATHHHH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        String path = getIntent().getExtras().getString(KEY_PATH);

        final PDFView pdfView = (PDFView)findViewById(R.id.pdfView);
        pdfView.fromAsset("pdfs/"+path)
        .onRender(new OnRenderListener() {
            @Override
            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                pdfView.fitToWidth();
            }
        }).load();
    }
}
