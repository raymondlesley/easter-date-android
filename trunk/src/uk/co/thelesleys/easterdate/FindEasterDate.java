package uk.co.thelesleys.easterdate;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FindEasterDate extends Activity {
	EditText edittext;
	TextView message;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	    edittext = (EditText) findViewById(R.id.edittext);
	    message = (TextView)findViewById(R.id.label);

	    /*
	     * 
	     */
	    edittext.setOnKeyListener(new OnKeyListener() {
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            // If the event is a key-down event on the "enter" button
	            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
	                (keyCode == KeyEvent.KEYCODE_ENTER)) {
	              // Perform action on key press
//	              Toast.makeText(FindEasterDate.this, edittext.getText(), Toast.LENGTH_SHORT).show();
		        	String yeartext = edittext.getText().toString();
		        	int year = Integer.parseInt(yeartext);
		            CalcEasterDate(year);
	              return true;
	            }
	            return false;
	        }
	    });

	    final Button downbutton = (Button) findViewById(R.id.downbutton);
	    downbutton.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	            // Perform action on clicks
	        	String yeartext = edittext.getText().toString();
	        	int year = Integer.parseInt(yeartext);
	        	int newyear = year - 1;
	        	String newyeartext = Integer.toString(newyear);
//	        	String message = "Beep Bop: " + newyeartext;
//	            Toast.makeText(OpenHelloFormStuff.this, message, Toast.LENGTH_SHORT).show();
	            edittext.setText(newyeartext);
	            CalcEasterDate(newyear);
	        }
	    });

	    final Button upbutton = (Button) findViewById(R.id.upbutton);
	    upbutton.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	            // Perform action on clicks
	        	String yeartext = edittext.getText().toString();
	        	int year = Integer.parseInt(yeartext);
	        	int newyear = year + 1;
	        	String newyeartext = Integer.toString(newyear);
//	        	String message = "Beep Bop: " + newyeartext;
//	            Toast.makeText(OpenHelloFormStuff.this, message, Toast.LENGTH_SHORT).show();
	            edittext.setText(newyeartext);
	            CalcEasterDate(newyear);
	        }
	    });
	    /*
	     * Init state
	     */
	    edittext.setText("2010");
	    CalcEasterDate(2010);
    }

    final void CalcEasterDate(int year) {
    	/*
    	 * Anonymous Gregorian Algorithm
    	 * http://en.wikipedia.org/wiki/Computus#Anonymous_Gregorian_algorithm
    	 */
    	int a = year % 19;
    	int b = year / 100;
    	int c = year % 100;
    	int d = b / 4;
    	int e = b % 4;
    	int f = (b + 8) / 25;
    	int g = (b - f + 1) / 3;
    	int h = (19 * a + b - d - g + 15) % 30;
    	int i = c / 4;
    	int k = c % 4;
    	int L = (32 + 2 * e + 2 * i - h - k) % 7;
    	int m = (a + 11 * h + 22 * L) / 451;
    	int month = (h + L - 7 * m + 114)  /31;
    	int day = ((h + L - 7 * m + 114) % 31) + 1;

    	final String march = getString(R.string.march);
    	final String april = getString(R.string.april); 
    	final String easterDate = getString(R.string.easter_date);

    	String date = month == 3 ? march : april; 
    	String result = easterDate + " " + day + getStNdTh(day) + " " + date + " " + Integer.toString(year);
    	message.setText(result);
    }

    final String getStNdTh(int day) {
    	if (day == 1 || day == 21 || day == 31) {
    		return "st";
    	}
    	else
    	if (day == 2 || day == 22) {
    		return "nd";
    	}
    	else {
    		return "th";
    	}
    }
}