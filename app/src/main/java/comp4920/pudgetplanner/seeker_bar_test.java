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
import android.widget.Toast;

/**
 * Created by michellephan on 15/10/2015.
 */
//THIS CLASS IS A TEST FOR THE SEEKBARS
    //ACCUMULATING THE RESULT TEXT_VIEW for both savings_bar and months_bar (seek bar) does not work
    //trying to figure out how to simultanously adjust the seekbars and change it on the text_view
public class seeker_bar_test extends Fragment {
    private SeekBar savings_bar;
    private TextView text_view;
    private EditText savings;
    private int count;
    private SeekBar months_bar;
    private EditText month;
    private int counter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.seeker_bar_test, container, false);
        Toast.makeText(getContext(),
                "abcdefgds",
                Toast.LENGTH_LONG).show();
        System.out.println("aaaaaaaaa  working" );
        savings_bar = (SeekBar) layout.findViewById(R.id.savings_bar);
        savings_bar.setOnSeekBarChangeListener(new savings());
        savings_bar.setMax(5000);

        text_view = (TextView) layout.findViewById(R.id.results);
        //initialises the value of text_view16 with 0
        text_view.setText(String.valueOf(savings_bar.getProgress()));

        savings = (EditText) layout.findViewById(R.id.savings);

        months_bar = (SeekBar) layout.findViewById(R.id.months_bar);
        months_bar.setMax(120); //120 months
        months_bar.setOnSeekBarChangeListener(new times());
        month = (EditText) layout.findViewById(R.id.months);

        month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    months_bar.setProgress(Integer.parseInt(s.toString()));
                    Toast.makeText(getContext(),
                            "aaaasdsds",
                            Toast.LENGTH_LONG).show();
                    month.setSelection(month.getText().length());
                    System.out.println("aaaaaaaaa OKKKKK");
                    text_view.setText(String.valueOf(count));

                } catch (Exception ex) {}

            }
        });


        savings.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    //Update Seekbar value after entering a number
                    if(savings.length() == 0) {
                        text_view.setText(0);

                    }
                    savings_bar.setProgress(Integer.parseInt(s.toString()));
                    Toast.makeText(getContext(),
                            "bbbbbb",
                            Toast.LENGTH_LONG).show();
                    savings.setSelection(savings.getText().length());
                    System.out.println("aaaaaaaaa OKKKKK");
                    text_view.setText(String.valueOf(count));

                } catch (Exception ex) {}
            }
        });

        return layout;
    }

    class savings implements SeekBar.OnSeekBarChangeListener {


        @Override
        //progress of the seeker
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //changing the output process (does not update the progress)
            count = progress;
            System.out.println("aaaaaaaaa"  + progress);
            Toast.makeText(getContext(),
                    "abcde" + progress,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        //start of the seek bar
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        //tracks at the designated number
        public void onStopTrackingTouch(SeekBar seekBar) {
            text_view.setText(String.valueOf(count));
            counter= (Integer.parseInt(String.valueOf(count)));

            savings.setText(String.valueOf(count));
        }
    }

    class times implements SeekBar.OnSeekBarChangeListener {
        @Override
        //progress of the seeker
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //changing the output process (does not update the progress)
            count = progress;
        }

        @Override
        //start of the seek bar
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        //tracks at the designated number
        public void onStopTrackingTouch(SeekBar seekBar) {
            text_view.setText(String.valueOf(count));

            month.setText(String.valueOf(count));
        }
    }
}