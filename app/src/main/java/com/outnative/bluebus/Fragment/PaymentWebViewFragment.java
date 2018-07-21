package com.outnative.bluebus.Fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.outnative.bluebus.R;

/**
 * Created by milan on 9/23/2017.
 */
public class PaymentWebViewFragment extends Fragment{
    String url;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_payment_webview,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //toolbar
        Typeface type= Typeface.createFromAsset(getActivity().getAssets(), "font/elegant.otf");

        FrameLayout backLayout=(FrameLayout)getActivity().findViewById(R.id.back_icon_layout);
        TextView textView=(TextView)getActivity().findViewById(R.id.toolbar_name);

        textView.setTypeface(type);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        url=getArguments().getString("url");

        WebView webView=(WebView)getActivity().findViewById(R.id.payment_webview);
        webView.loadUrl(url.trim());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);

        Toast.makeText(getActivity(),url,Toast.LENGTH_LONG).show();
    }
}
