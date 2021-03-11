package com.example.chc;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // declare DBHandler
    DBHandler dbHandler;

    // declare EditText
    EditText nameEditText;

    // declare Spinners
    Spinner yearSpinner;
    Spinner majorSpinner;

    // declare Strings to store year and major selected in Spinners
    String year;
    String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // initialize EditText
        nameEditText = (EditText) findViewById(R.id.nameEditText);

        // initialize Spinners
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        majorSpinner = (Spinner) findViewById(R.id.majorSpinner);

        // initialize ArrayAdapters with values in year and major string arrays
        // and stylize them with style defined by simple_spinner_item
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.year, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(this,
                R.array.major, android.R.layout.simple_spinner_item);

        // further stylize ArrayAdapters with style defined by simple_spinner_dropdown_item
        yearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        majorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // set ArrayAdapters on Spinners
        yearSpinner.setAdapter(yearAdapter);
        majorSpinner.setAdapter(majorAdapter);

        // register On Item Selected Listener to Spinners
        yearSpinner.setOnItemSelectedListener(this);
        majorSpinner.setOnItemSelectedListener(this);
    }

    /**
     * This method further initializes the Action Bar of the MainActivity.
     * It gets the code in the menu main resource file and incorporates it
     * into the Action Bar.
     * @param menu menu main resource file
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected.
     * @param item selected menu item in overflow menu
     * @return true if menu item is selected, else false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get the id of the menu item selected
        switch (item.getItemId()) {
            case R.id.action_get_count_cis :
                // Display toast that has count of CIS students
                Toast.makeText(this, getMessage("CIS"), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_get_count_cit :
                // Display toast that has count of CIT students
                Toast.makeText(this, getMessage("CIT"), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_get_count_csm :
                // Display toast that has count of CIT students
                Toast.makeText(this, getMessage("CSM"), Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when a menu-item in the overflow menu is selected.
     * @param major selected major (CIS, CIT, or CSM)
     * @return String that contains count of the number of students who have the selected major.
     */
    public String getMessage (String major) {
        int count = dbHandler.getCount(major);
        return (count == 1 ? count + " student." : count + " students.");
    }

    /**
     * This method gets called when the add button in the Action Bar gets clicked.
     * @param menuItem add student menu item
     */
    public void addStudent(MenuItem menuItem) {
        // get data input in EditText and store it in String
        String name = nameEditText.getText().toString();

        // trim Strings and see if they're equal to empty Strings
        if (name.trim().equals("") || year.trim().equals("") || major.trim().equals("")){
            // display "Please enter a name, store, and date!" Toast if any of the Strings are empty
            Toast.makeText(this, "Please enter a name, year, and major!", Toast.LENGTH_LONG).show();
        } else {
            // add item into database
            dbHandler.addStudent(name, year, major);

            // display "Student added!" Toast of none of the Strings are empty
            Toast.makeText(this, "Student added!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method gets called when an item in one of the Spinners is selected.
     * @param parent Spinner AdapterView
     * @param view MainActivity view
     * @param position position of item in Spinner that was selected
     * @param id database id of item in Spinner that was selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // get the id of the Spinner that called method
        switch (parent.getId()) {
            case R.id.majorSpinner:
                // get the item selected in the Spinner and store it in String
                major = parent.getItemAtPosition(position).toString();
                break;
            case R.id.yearSpinner:
                // get the item selected in the Spinner and store it in String
                year = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}