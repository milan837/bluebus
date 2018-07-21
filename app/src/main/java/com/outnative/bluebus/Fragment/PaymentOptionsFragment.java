package com.outnative.bluebus.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.outnative.bluebus.R;

import org.w3c.dom.Text;

/**
 * Created by milan on 9/20/2017.
 */
public class PaymentOptionsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_payment_options,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

        final TextView creditCardTitle=(TextView)getActivity().findViewById(R.id.credit_card_title);
        final TextView debitCardTitle=(TextView)getActivity().findViewById(R.id.debit_card_title);
        final TextView netBankingTitle=(TextView)getActivity().findViewById(R.id.net_banking_title);
        final TextView walletTitle=(TextView)getActivity().findViewById(R.id.wallet_pay_title);

        creditCardTitle.setTypeface(type);
        debitCardTitle.setTypeface(type);
        netBankingTitle.setTypeface(type);
        walletTitle.setTypeface(type);

        final RelativeLayout creditForm=(RelativeLayout)getActivity().findViewById(R.id.creadit_card_form);
        final RelativeLayout debitForm=(RelativeLayout)getActivity().findViewById(R.id.debit_card_form);
        final RelativeLayout netBankingForm=(RelativeLayout)getActivity().findViewById(R.id.net_banking_form);
        final LinearLayout walletForm=(LinearLayout)getActivity().findViewById(R.id.wallet_pay_form);

        Animation slideUp= AnimationUtils.loadAnimation(getActivity(),R.anim.slide_up);
        Animation slideDown=AnimationUtils.loadAnimation(getActivity(),R.anim.slide_down);
        creditCardTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditCardTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                walletTitle.setTextColor(Color.BLACK);
                netBankingTitle.setTextColor(Color.BLACK);
                debitCardTitle.setTextColor(Color.BLACK);

                creditForm.setVisibility(View.VISIBLE);
                debitForm.setVisibility(View.GONE);
                netBankingForm.setVisibility(View.GONE);
                walletForm.setVisibility(View.GONE);
            }
        });

        debitCardTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debitCardTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                walletTitle.setTextColor(Color.BLACK);
                netBankingTitle.setTextColor(Color.BLACK);
                creditCardTitle.setTextColor(Color.BLACK);

                creditForm.setVisibility(View.GONE);
                debitForm.setVisibility(View.VISIBLE);
                netBankingForm.setVisibility(View.GONE);
                walletForm.setVisibility(View.GONE);
            }
        });

        netBankingTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netBankingTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                walletTitle.setTextColor(Color.BLACK);
                debitCardTitle.setTextColor(Color.BLACK);
                creditCardTitle.setTextColor(Color.BLACK);

                creditForm.setVisibility(View.GONE);
                debitForm.setVisibility(View.GONE);
                netBankingForm.setVisibility(View.VISIBLE);
                walletForm.setVisibility(View.GONE);
            }
        });

        walletTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                netBankingTitle.setTextColor(Color.BLACK);
                debitCardTitle.setTextColor(Color.BLACK);
                creditCardTitle.setTextColor(Color.BLACK);

                creditForm.setVisibility(View.GONE);
                debitForm.setVisibility(View.GONE);
                netBankingForm.setVisibility(View.GONE);
                walletForm.setVisibility(View.VISIBLE);
            }
        });

        //inside items textview
        TextView esewaTitle=(TextView)getActivity().findViewById(R.id.esewa_wallet_text);
        TextView ipayTitle=(TextView)getActivity().findViewById(R.id.ipay_wallet_text);

        //linear layout
        LinearLayout esewaLayout=(LinearLayout)getActivity().findViewById(R.id.esewa_wallet_payment);
        LinearLayout ipayLayout=(LinearLayout)getActivity().findViewById(R.id.ipay_wallet_payment);

        esewaTitle.setTypeface(type);
        ipayTitle.setTypeface(type);

        //fragments

        esewaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PaymentWebViewFragment paymentWebViewFragment=new PaymentWebViewFragment();
                final Bundle bundle=new Bundle();
                bundle.putString("url","https://esewa.com.np/home");
                paymentWebViewFragment.setArguments(bundle);

                FragmentManager manager=getFragmentManager();
                final FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.conform_booking_fragment_display_layout, paymentWebViewFragment, "paymentSelectionFragment");
                transaction.addToBackStack("paymentSelectionFragment");
                transaction.commit();
            }
        });

        ipayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentWebViewFragment paymentWebViewFragment=new PaymentWebViewFragment();
                Bundle bundle=new Bundle();
                bundle.putString("url","https://www.ipay.com.np");
                paymentWebViewFragment.setArguments(bundle);

                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.conform_booking_fragment_display_layout, paymentWebViewFragment, "paymentSelectionFragment");
                transaction.addToBackStack("paymentSelectionFragment");
                transaction.commit();
            }
        });
    }
}
