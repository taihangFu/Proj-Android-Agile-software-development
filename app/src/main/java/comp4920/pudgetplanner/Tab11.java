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
public class Tab11 extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnFocusChangeListener, TextWatcher {
    private TextView tvResult;
    private EditText etSaving;
    private EditText etTimeYear;
    private EditText etTimeMonth;
    private EditText etInterest;
    private SeekBar sbSaving;
    private SeekBar sbTime;
    private SeekBar sbInterest;

    private int saving = 1;
    private int time = 1;
    private double interestRate = 0;

    private int whoHasFocus= 0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab1, container, false);
        tvResult = (TextView) layout.findViewById(R.id.tvResult);
        etSaving = (EditText) layout.findViewById(R.id.etSavingGoal);
        etSaving.setSelection(1);
        etTimeYear = (EditText) layout.findViewById(R.id.etTimeYear);
        etTimeMonth = (EditText) layout.findViewById(R.id.etTimeMonth);
        etInterest = (EditText) layout.findViewById(R.id.etInterest);
        sbSaving = (SeekBar) layout.findViewById(R.id.sbSaving);
        sbTime = (SeekBar) layout.findViewById(R.id.sbTime);
        sbInterest = (SeekBar) layout.findViewById(R.id.sbInterest);

        sbSaving.setOnSeekBarChangeListener(this);
        sbTime.setOnSeekBarChangeListener(this);
        sbInterest.setOnSeekBarChangeListener(this);

        etSaving.addTextChangedListener(this);
        etSaving.setOnFocusChangeListener(this);
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

            case R.id.sbSaving:
                if(progress == 0){
                    saving = 1;
                    etSaving.setText(Integer.toString(1));
                }else {
                    saving = progress;
                    etSaving.setText(Integer.toString(progress));
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
            int a = saving * time;
            tvResult.setText("$" + a);
        }else{
            double b = Math.pow((1+interestRate/100.0/12),(time));
            double c = saving;
            double a = c*(b-1)/(interestRate/100.0/12);

            tvResult.setText("$" + Math.round(a));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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
                if(etSaving.getText().toString().trim().equals("")) {
                    saving = 1;
                    etSaving.setHint(Integer.toString(saving));
                }else if(Integer.parseInt(etSaving.getText().toString().trim())>5000) {
                    saving = 5000;
                    etSaving.setText(Integer.toString(saving));
                }else{
                    saving = Integer.parseInt(etSaving.getText().toString().trim());
                }
                sbSaving.setProgress(saving);
                etSaving.setSelection(etSaving.getText().length());
                break;
            case 2:
                if(etTimeYear.getText().toString().trim().equals("") ) {
                    time = Integer.parseInt(etTimeMonth.getText().toString().trim());
                    etTimeYear.setText("0");
                }else if(Integer.parseInt(etTimeYear.getText().toString().trim())==0 &
                        Integer.parseInt(etTimeMonth.getText().toString().trim())==0) {
                    time = 1;
                    etTimeMonth.setText("1");
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
}