package comp4920.pudgetplanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * Created by michellephan on 15/10/2015.
 */
public class Tab33 extends Fragment implements SeekBar.OnSeekBarChangeListener, TextWatcher, View.OnFocusChangeListener {

    private TextView tvResult;
    private EditText etSavingGoal;
    private EditText etTimeYear;
    private EditText etTimeMonth;
    private EditText etInterest;
    private SeekBar sbSavingGoal;
    private SeekBar sbTime;
    private SeekBar sbInterest;

    private int savingAmount = 1;
    private int time = 1;
    private double interestRate = 0;
    private int amountPerMonth = 0;

    private int whoHasFocus= 0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab33, container, false);

        tvResult = (TextView) layout.findViewById(R.id.tvResult);
        etSavingGoal = (EditText) layout.findViewById(R.id.etSavingGoal);
        etSavingGoal.setSelection(1);
        etTimeYear = (EditText) layout.findViewById(R.id.etTimeYear);
        etTimeMonth = (EditText) layout.findViewById(R.id.etTimeMonth);
        etInterest = (EditText) layout.findViewById(R.id.etInterest);
        sbSavingGoal = (SeekBar) layout.findViewById(R.id.sbSavingGoal);
        sbTime = (SeekBar) layout.findViewById(R.id.sbTime);
        sbInterest = (SeekBar) layout.findViewById(R.id.sbInterest);

        sbSavingGoal.setOnSeekBarChangeListener(this);
        sbTime.setOnSeekBarChangeListener(this);
        sbInterest.setOnSeekBarChangeListener(this);

        etSavingGoal.addTextChangedListener(this);
        etSavingGoal.setOnFocusChangeListener(this);
        etTimeYear.addTextChangedListener(this);
        etTimeYear.setOnFocusChangeListener(this);
        etTimeMonth.addTextChangedListener(this);
        etTimeMonth.setOnFocusChangeListener(this);
        etInterest.addTextChangedListener(this);
        etInterest.setOnFocusChangeListener(this);
        return layout;

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        switch (seekBar.getId()) {

            case R.id.sbSavingGoal:

                if(progress == 0) {
                    savingAmount = 1;
                    etSavingGoal.setText(Integer.toString(savingAmount));
                }else {
                    savingAmount = progress;
                    etSavingGoal.setText(Integer.toString(savingAmount));
                }
                break;

            case R.id.sbTime:
                if(progress == 0){
                    sbTime.setProgress(1);
                    time = 1;
                    int year = 0;
                    int month = 1;
                    etTimeYear.setText(Integer.toString(year));
                    etTimeMonth.setText(Integer.toString(month));
                }else {
                    time = progress;
                    int year = progress/12;
                    int month = progress - (12*year);
                    etTimeYear.setText(Integer.toString(year));
                    etTimeMonth.setText(Integer.toString(month));
                }
                break;

            case R.id.sbInterest:
                interestRate = progress/100.0;
                etInterest.setText(Double.toString(interestRate));
                break;
        }
        if(interestRate == 0){
            amountPerMonth = (savingAmount)/(time);
            tvResult.setText("$" + amountPerMonth + " per month");
        }else{
            double a = (savingAmount) * (interestRate/100.0/12);
            double b = Math.pow((1+interestRate/100.0/12),(time));
            double c = a/(b-1);

            tvResult.setText("$" + Math.round(c) + " per month");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (whoHasFocus) {
            case 1:
                if(etSavingGoal.getText().toString().trim().equals("")) {
                    savingAmount = 1;
                    etSavingGoal.setHint(Integer.toString(savingAmount));
                }else if(Integer.parseInt(etSavingGoal.getText().toString().trim())>100000) {
                    savingAmount = 100000;
                    etSavingGoal.setText(Integer.toString(savingAmount));
                }else{
                    savingAmount = Integer.parseInt(etSavingGoal.getText().toString().trim());
                }
                sbSavingGoal.setProgress(savingAmount);
                etSavingGoal.setSelection(etSavingGoal.getText().length());
                break;
            case 2:
                if(etTimeYear.getText().toString().trim().equals("") ) {
                    time = Integer.parseInt(etTimeMonth.getText().toString().trim());
                    etTimeYear.setText("0");
                }else if(Integer.parseInt(etTimeYear.getText().toString().trim())==0 &
                        Integer.parseInt(etTimeMonth.getText().toString().trim())==0) {
                    time = 1;
                    etTimeMonth.setHint("1");
                }else if(Integer.parseInt(etTimeYear.getText().toString().trim())>10) {
                    time = 120;
                    etTimeYear.setText("10");
                    etTimeMonth.setText("0");
                }else{
                    int year = Integer.parseInt(etTimeYear.getText().toString().trim());
                    int month = Integer.parseInt(etTimeMonth.getText().toString().trim());
                    time = (year * 12) + month;
                }
                sbTime.setProgress(time);
                etTimeYear.setSelection(etTimeYear.getText().length());
                break;
            case 3:
                if(etTimeMonth.getText().toString().trim().equals("")) {
                    time = Integer.parseInt(etTimeYear.getText().toString().trim()) * 12;
                    etTimeMonth.setText("0");
                }else if(Integer.parseInt(etTimeYear.getText().toString().trim())==10 &&
                        Integer.parseInt(etTimeMonth.getText().toString().trim())>0){
                    etTimeMonth.setText("0");
                }else if(Integer.parseInt(etTimeYear.getText().toString().trim())==0 &
                        Integer.parseInt(etTimeMonth.getText().toString().trim())==0) {
                    time = 1;
                    etTimeMonth.setText("1");
                }else if(Integer.parseInt(etTimeMonth.getText().toString().trim())>11) {
                    int year = Integer.parseInt(etTimeYear.getText().toString().trim());
                    year = year + 1 ;
                    etTimeMonth.setText("0");
                    etTimeYear.setText(Integer.toString(year));
                    time = Integer.parseInt(etTimeYear.getText().toString().trim()) * 12;
                }else{
                    int year = Integer.parseInt(etTimeYear.getText().toString().trim());
                    int month = Integer.parseInt(etTimeMonth.getText().toString().trim());
                    time = (year * 12) + month;
                }
                sbTime.setProgress(time);
                etTimeMonth.setSelection(etTimeMonth.getText().length());
                break;
            case 4:
                if(etInterest.getText().toString().trim().equals("")) {
                    interestRate = 0;
                    etInterest.setText("0");
                }else if(Double.parseDouble(etInterest.getText().toString().trim())>5) {
                    interestRate = 5;
                    etInterest.setText("5");
                }else{
                    interestRate = Double.parseDouble(etInterest.getText().toString().trim());
                }
                sbInterest.setProgress((int)interestRate*100);
                etInterest.setSelection(etInterest.getText().length());
                break;
        }

}

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.etSavingGoal:
                whoHasFocus =1;
                break;
            case R.id.etTimeYear:
                whoHasFocus =2;
                break;
            case R.id.etTimeMonth:
                whoHasFocus=3;
                break;
            case R.id.etInterest:
                whoHasFocus=4;
                break;
        }
    }
}
