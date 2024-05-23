package cdac.com.yogaapp.View;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import cdac.com.yogaapp.R;

public class ReadPDFActivity extends AppCompatActivity {


    String url, name;
    private ProgressDialog pDialog;
    String fileName;
    File file;
    WebView webview;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pdf);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        webview = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        name = intent.getStringExtra("fileName");
        url = intent.getStringExtra("pdfUrl");
        Log.d("1234", "onCreate: " + url);
        setTitle(name);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.setWebViewClient(new myWebClient());
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(true);
        webview.loadUrl(url);
        Log.d("1234", "onCreate: " + url);

        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(ReadPDFActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Check your internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                alertDialog.show();
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }
        });
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("1234", "shouldOverrideUrlLoading: " + url);
            view.loadUrl(url);
            progressBar.setVisibility(View.VISIBLE);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    // This method is used to detect back button
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }
}