package com.rbysoft.myovertimebd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.rbysoft.myovertimebd.DbHelper.DbHelper;
import com.rbysoft.myovertimebd.Model.OverTime;

public class Fragment_Second extends Fragment {
    RadioGroup rd;
    View rootview;
    TextView txtdate;
    EditText editText;
    Button saveButton;
    RadioButton daytext,regulartext,nighttext,holidaytext;

    TextView selecttext;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_second, container, false);
        saveButton=rootview.findViewById(R.id.savebuttonid);
        txtdate = rootview.findViewById(R.id.date);
        editText = rootview.findViewById(R.id.ins_info);
        rd= rootview.findViewById(R.id.rg);
        initializeComponents();

        String mystring=getArguments().getString("DATEOFNOW");




        String lang=Saving.getAString(getActivity(),"Language");
        String da="";
        if (lang.equals("bn")){
             da=getActivity().getResources().getString(R.string.dateb);
             UpdateResource();
        }else{
            da=getActivity().getResources().getString(R.string.date);
        }



        txtdate.setText(da+" : "+mystring+" ");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insert();

            }
        });
        return rootview;

    }

    private void UpdateResource() {
        daytext.setText(getResources().getString(R.string.dayb));
        regulartext.setText(getResources().getString(R.string.regularb));
        nighttext.setText(getResources().getString(R.string.nightb));
        selecttext.setText(getResources().getString(R.string.selectyourshiftb));
        holidaytext.setText(getResources().getString(R.string.holidayb));
        saveButton.setText(getResources().getString(R.string.saveb));
        editText.setHint(getResources().getString(R.string.addhourb));

    }

    private void insertInfo() {
        DbHelper dbHelper = new DbHelper(getActivity());
        OverTime overTime= new OverTime();
        int SelectedId=rd.getCheckedRadioButtonId();

        if(SelectedId==R.id.btn01){
            overTime.setDate(getArguments().getString("DATEOFNOW"));
            overTime.setRegular(Double.valueOf(editText.getText().toString()));

            if (dbHelper.addDataRegular(overTime)>0){
                Toast.makeText(getActivity(), "Successfully Inserted!", Toast.LENGTH_SHORT).show();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Fragment_Container,new Fragment_home())
                        .commit();
            }else {
                Toast.makeText(getActivity(), "Already Inserted!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(SelectedId==R.id.btn02){
            overTime.setDate(getArguments().getString("DATEOFNOW"));
            overTime.setDay(Double.valueOf(editText.getText().toString()));
            if (dbHelper.addDataDay(overTime)>0){
                Toast.makeText(getActivity(), "Successfully Inserted!", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Fragment_Container,new Fragment_home())
                        .commit();
            }else {
                Toast.makeText(getActivity(), "Already Inserted!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(SelectedId==R.id.btn03){
            overTime.setDate(getArguments().getString("DATEOFNOW"));
            overTime.setNight(Double.valueOf(editText.getText().toString()));
            if (dbHelper.addDataNight(overTime)>0){
                Toast.makeText(getActivity(), "Successfully Inserted!", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(getActivity(), "Already Inserted!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(SelectedId==R.id.btn04) {
            overTime.setDate(getArguments().getString("DATEOFNOW"));
            overTime.setOff(Double.valueOf(editText.getText().toString()));
            if (dbHelper.addDataOff(overTime) > 0) {
                Toast.makeText(getActivity(), "Successfully Inserted!", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Fragment_Container,new Fragment_home())
                        .commit();

            } else {
                Toast.makeText(getActivity(), "Already Inserted!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "First Select Your shift", Toast.LENGTH_SHORT).show();
        }
    }
    public void Insert() {
        if(Valid()){
            insertInfo();
            CloseKeyBoard();
        }
    }

    private boolean Valid() {
        if(editText.getText().toString().length()==0)
        {
            AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(getActivity());
            alerDialogBuilder.setTitle("Error");
            alerDialogBuilder.setIcon(R.drawable.ic_warning);
            alerDialogBuilder.setMessage("Insert Hour.");

            alerDialogBuilder.setCancelable(true);
            alerDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alerDialogBuilder.create();
            alertDialog.show();
            return false;
        }
        return true;
    }

    @SuppressLint("NewApi")
    private void CloseKeyBoard() {
        View view =getActivity().getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


    private void initializeComponents(){
       daytext=rootview.findViewById(R.id.btn02);
       selecttext=rootview.findViewById(R.id.t);
       regulartext=rootview.findViewById(R.id.btn01);
       nighttext=rootview.findViewById(R.id.btn03);
       holidaytext=rootview.findViewById(R.id.btn04);
    }



}
