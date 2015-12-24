package comp4920.pudgetplanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by michellephan on 15/10/2015.
 */
public class Tab22 extends Fragment implements SeekBar.OnSeekBarChangeListener, TextWatcher, View.OnFocusChangeListener {

    private TextView tvResult;
    private EditText etSavingGoal;
    private EditText etSPM;
    private EditText etInterest;
    private SeekBar sbSavingGoal;
    private SeekBar sbSPM;
    private SeekBar sbInterest;

    private int savingAmount = 1;
    private int spm = 1;
    private double interestRate = 0;

    private int whoHasFocus= 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab22, container, false);
        tvResult = (TextView) layout.findViewById(R.id.tvResult);
        etSavingGoal = (EditText) layout.findViewById(R.id.etSavingGoal);
        etSavingGoal.setSelection(1);
        etSPM = (EditText) layout.findViewById(R.id.etSPM);
        etInterest = (EditText) layout.findViewById(R.id.etInterest);
        sbSavingGoal = (SeekBar) layout.findViewById(R.id.sbSaving);
        sbSPM = (SeekBar) layout.findViewById(R.id.sbSPM);
        sbInterest = (SeekBar) layout.findViewById(R.id.sbInterest);

        sbSavingGoal.setOnSeekBarChangeListener(this);
        sbSPM.setOnSeekBarChangeListener(this);
        sbInterest.setOnSeekBarChangeListener(this);

        etSavingGoal.addTextChangedListener(this);
        etSavingGoal.setOnFocusChangeListener(this);
        etSPM.addTextChangedListener(this);
        etSPM.setOnFocusChangeListener(this);
        etInterest.addTextChangedListener(this);
        etInterest.setOnFocusChangeListener(this);

        return layout;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {

            case R.id.sbSaving:

                if(progress == 0){
                    savingAmount = 1;
                    etSavingGoal.setText(Integer.toString(savingAmount));
                }else {
                    savingAmount = progress;
                    etSavingGoal.setText(Integer.toString(savingAmount));
                }
                break;

            case R.id.sbSPM:
                if(progress == 0){
                    sbSPM.setProgress(1);
                    spm = 1;
                    etSPM.setText(Integer.toString(spm));
                }else {
                    spm = progress;
                    etSPM.setText(Integer.toString(spm));
                }
                break;

            case R.id.sbInterest:
                interestRate = progress/100.0;
                etInterest.setText(Double.toString(interestRate));
                break;
        }
        if(interestRate == 0){
            int t = savingAmount/spm;
            int year = t/12;
            int month = t - (12*year);
            double tt = savingAmount*1.0/spm;
            if(tt>t){
                month++;
            }
            if(month == 1 && year == 1){
                tvResult.setText(year + " years, "+ month + " month");
            }else if(year == 1){
                tvResult.setText(year + " year, "+ month + " months");
            }else if(month == 1){
                tvResult.setText(year + " years, "+ month + " month");
            }else {
                tvResult.setText(year + " years, " + month + " months");
            }
        }else{
            double a = (savingAmount) * (interestRate/100.0/12);
            double b = 1+interestRate/100.0/12;
            double c = (a/spm)+1;
            int t = (int)(Math.log(c)/Math.log(b));
            double tt = (Math.log(c)/Math.log(b));

            int year = t/12;
            int month = t - (12*year);
            if(tt>t){
                month++;
            }
            if(month == 1 && year == 1){
                tvResult.setText(year + " years, "+ month + " month");
            }else if(year == 1){
                tvResult.setText(year + " year, "+ month + " months");
            }else if(month == 1){
                tvResult.setText(year + " years, "+ month + " month");
            }else {
                tvResult.setText(year + " years, " + month + " months");
            }
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
                if (etSavingGoal.getText().toString().trim().equals("")) {
                    savingAmount = 1;
                    etSavingGoal.setHint(Integer.toString(savingAmount));
                } else if (Integer.parseInt(etSavingGoal.getText().toString().trim()) > 100000) {
                    savingAmount = 100000;
                    etSavingGoal.setText(Integer.toString(savingAmount));
                } else {
                    savingAmount = Integer.parseInt(etSavingGoal.getText().toString().trim());
                }
                sbSavingGoal.setProgress(savingAmount);
                etSavingGoal.setSelection(etSavingGoal.getText().length());
                break;
            case 2:
                if(etSPM.getText().toString().trim().equals("")) {
                    spm = 1;
                    etSPM.setHint(Integer.toString(spm));
                }else if(Integer.parseInt(etSPM.getText().toString().trim())>5000) {
                    spm = 5000;
                    etSPM.setText(Integer.toString(spm));
                }else{
                    spm = Integer.parseInt(etSPM.getText().toString().trim());
                }
                sbSPM.setProgress(spm);
                etSPM.setSelection(etSPM.getText().length());
                break;
            case 3:
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
            case R.id.etSPM:
                whoHasFocus =2;
                break;
            case R.id.etInterest:
                whoHasFocus=3;
                break;
        }
    }
}
