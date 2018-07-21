package com.outnative.bluebus.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.outnative.bluebus.R;
import com.outnative.bluebus.SqlLiteDatabase.PassengerDetailsSql;

import java.util.ArrayList;

/**
 * Created by milan on 9/12/2017.
 */
public class ConformBooklingDetails extends Fragment {
    String rideId,selectedDate,selectedSeat,boardingLocationId,droppingLocationId;
    EditText passengerNameEditText[];
    RadioGroup passengerGender[];
    RadioButton passengerMale[];
    RadioButton passengerFemale[];
    Typeface type;
    TextView ntf_text[];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_conform_booking_details,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rideId=getArguments().getString("rideId");
        selectedDate=getArguments().getString("selectedDate");
        selectedSeat=getArguments().getString("selectedSeat");
        boardingLocationId=getArguments().getString("boardingLocationId");
        droppingLocationId=getArguments().getString("droppingLocationId");

        type=Typeface.createFromAsset(getActivity().getAssets(), "font/elegant.otf");

        FrameLayout backLayout=(FrameLayout)getActivity().findViewById(R.id.back_icon_layout);
        TextView textView=(TextView)getActivity().findViewById(R.id.toolbar_name);

        textView.setTypeface(type);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

      //  TextView textView1=(TextView)getActivity().findViewById(R.id.show_details);
        //textView1.setText("ride id: " + rideId + "\n date: " + selectedDate + " seats: " + selectedSeat + " \nboarding: " + boardingLocationId + " dropping: " + droppingLocationId);

        Button conformButton=(Button)getActivity().findViewById(R.id.conform_booking_button);
        conformButton.setTypeface(type);

        //for scrollview items
        TextView contactInformationTextView=(TextView)getActivity().findViewById(R.id.contact_information_textview);
        final EditText passengerEmail=(EditText)getActivity().findViewById(R.id.passenger_email);
        final EditText passengerPhoneNumber=(EditText)getActivity().findViewById(R.id.passenger_phonenumber);
        TextView passengerInformation=(TextView)getActivity().findViewById(R.id.passeneger_information_textview);
        TextView passengerNoteTextView=(TextView)getActivity().findViewById(R.id.contact_information_note_textview);
        TextView termsAndConditionTextView=(TextView)getActivity().findViewById(R.id.terms_and_condition_textview);
        LinearLayout passenderDetailsLayout=(LinearLayout)getActivity().findViewById(R.id.passenger_details_information_layout);

        contactInformationTextView.setTypeface(type);
        passengerEmail.setTypeface(type);
        passengerPhoneNumber.setTypeface(type);
        passengerInformation.setTypeface(type);
        passengerNoteTextView.setTypeface(type);
        termsAndConditionTextView.setTypeface(type);

        final String seats[]=selectedSeat.split(" ");

        final LinearLayout passengerDetailsLinearLayout[]=new LinearLayout[seats.length];
        passengerNameEditText=new EditText[seats.length];
        passengerGender=new RadioGroup[seats.length];
        passengerMale=new RadioButton[seats.length];
        passengerFemale=new RadioButton[seats.length];
        ntf_text=new TextView[seats.length];

        final TextView seatNumber[]=new TextView[seats.length];

        for(int i=0;i<seats.length;i++){
            passengerDetailsLinearLayout[i]=new LinearLayout(getActivity());
            passengerDetailsLinearLayout[i].setOrientation(LinearLayout.VERTICAL);
            passengerMale[i]=new RadioButton(getActivity());
            passengerFemale[i]=new RadioButton(getActivity());
            seatNumber[i]=new TextView(getActivity());
            passengerNameEditText[i]=new EditText(getActivity());
            passengerGender[i]=new RadioGroup(getActivity());

            //for linear layout
            LinearLayout.LayoutParams linearLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayoutParams.setMargins(0, 5, 0, 10);
            passengerDetailsLinearLayout[i].setId(i);
            passengerDetailsLinearLayout[i].setLayoutParams(linearLayoutParams);
            passengerDetailsLinearLayout[i].setBackgroundColor(Color.WHITE);
            passengerDetailsLinearLayout[i].setPadding(10, 10, 10, 10);


            LinearLayout.LayoutParams textViewParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textViewParams.setMargins(3, 3, 3, 3);
            seatNumber[i].setLayoutParams(textViewParams);
            seatNumber[i].setId(i);
            seatNumber[i].setTypeface(type);
            seatNumber[i].setText("Seat Number: " + seats[i]);
            seatNumber[i].setTextSize(18);

            LinearLayout.LayoutParams editTextParms=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,70);
            editTextParms.setMargins(3, 3, 3, 3);
            passengerNameEditText[i].setLayoutParams(editTextParms);
            passengerNameEditText[i].setHint("Passenger name");
            passengerNameEditText[i].setTypeface(type);
            passengerNameEditText[i].setPadding(4, 4, 4, 4);
            passengerNameEditText[i].setId(i);
            passengerNameEditText[i].setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

            LinearLayout.LayoutParams radioButtonParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50);
            radioButtonParams.setMargins(3, 3, 3, 3);
            passengerMale[i].setTypeface(type);
            passengerMale[i].setText("Male");
            passengerMale[i].setTextSize(18);

            passengerFemale[i].setTypeface(type);
            passengerFemale[i].setText("Female");
            passengerFemale[i].setTextSize(18);

            LinearLayout.LayoutParams radioGroupParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50);
            radioGroupParams.setMargins(3, 3, 3, 3);
            passengerGender[i].setLayoutParams(radioGroupParams);
            passengerGender[i].setOrientation(LinearLayout.HORIZONTAL);
            passengerGender[i].addView(passengerMale[i]);
            passengerGender[i].addView(passengerFemale[i]);
            passengerGender[i].setId(i);

            passengerDetailsLinearLayout[i].addView(seatNumber[i]);
            passengerDetailsLinearLayout[i].addView(passengerNameEditText[i]);
            passengerDetailsLinearLayout[i].addView(passengerGender[i]);


            ntf_text[i]=new TextView(getActivity());
            ntf_text[i].setText("Enter all the fields");
            ntf_text[i].setTypeface(type);
            ntf_text[i].setTextColor(Color.RED);
            ntf_text[i].setTextSize(18);
            ntf_text[i].setVisibility(View.GONE);
            passengerDetailsLinearLayout[i].addView(ntf_text[i]);

            passenderDetailsLayout.addView(passengerDetailsLinearLayout[i]);


        }

        conformButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opening sqllite connection
                PassengerDetailsSql  passengerDetailsSql=new PassengerDetailsSql(getActivity());
                SQLiteDatabase db=passengerDetailsSql.getWritableDatabase();
                passengerDetailsSql.onCreate(db);
                passengerDetailsSql.deleteRows(db);
                ContentValues values=new ContentValues();

                String email=passengerEmail.getText().toString().trim();
                String number=passengerPhoneNumber.getText().toString().trim();

                if(email.isEmpty()){
                    Toast.makeText(getActivity(),"please enter your email",Toast.LENGTH_LONG).show();
                }else if(number.isEmpty()){
                    Toast.makeText(getActivity(),"please enter your email",Toast.LENGTH_LONG).show();
                }else{
                    int count=0;
                    for(int i=0;i<seats.length;i++){
                        String name,gender;
                        name=passengerNameEditText[i].getText().toString();
                        int id=passengerGender[i].getCheckedRadioButtonId();
                        if(id == -1){
                            ntf_text[i].setVisibility(View.VISIBLE);
                        }else{
                             gender=((RadioButton)getActivity().findViewById(id)).getText().toString();
                             if(name.isEmpty()) {
                                 ntf_text[i].setVisibility(View.VISIBLE);
                             }else {
                                ntf_text[i].setVisibility(View.GONE);
                                values.put("seat_number", seats[i]);
                                values.put("passenger_name", name);
                                values.put("passenger_gender", gender);
                                long status=db.insert("passenger_details",null,values);
                                 Log.i("tyuiop",seats[i]);
                                if(status != -1){
                                    count++;
                                }
                            }

                        }

                    }
                    if(count == seats.length){
                        PaymentOptionsFragment saveTicketFragment=new PaymentOptionsFragment();
                        FragmentManager manager=getFragmentManager();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.conform_booking_fragment_display_layout, saveTicketFragment, "passengerDetailsFragment");
                        transaction.addToBackStack("passengerDetailsFragment");
                        transaction.commit();
                    }
                    /*
                    for(int i=0;i<seats.length;i++){
                        name[i]=new EditText(getActivity()) ;
                        gender[i]=new RadioGroup(getActivity());

                        name[i].getId();
                    }*/
                }
            }
        });
    }
}
