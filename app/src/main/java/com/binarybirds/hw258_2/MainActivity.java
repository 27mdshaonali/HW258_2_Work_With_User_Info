package com.binarybirds.hw258_2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> userList;
    ArrayList<HashMap<String, String>> filteredList = new ArrayList<>();

    Spinner spinnerGender, spinnerRole, spinnerBloodGroup, spinnerCountry, spinnerState, spinnerSortBy;
    TextView username, fullNameSet;

    String loggedUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get Data
        userList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("userList");
        loggedUsername = getIntent().getStringExtra("loggedUsername");

        // Views
        username = findViewById(R.id.username);
        fullNameSet = findViewById(R.id.fullName);

        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerRole = findViewById(R.id.spinnerRole);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerSortBy = findViewById(R.id.spinnerSortBy);

        // Greet User
        if (userList != null && loggedUsername != null) {
            for (HashMap<String, String> user : userList) {
                if (loggedUsername.equals(user.get("userSignInUserName"))) {
                    String fullName = user.get("firstName") + " " + user.get("lastName");
                    fullNameSet.setText(fullName + "!");
                    username.setText("@" + loggedUsername);
                    Toast.makeText(this, "Welcome " + fullName + "!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        } else {
            fullNameSet.setText("User not found!");
            username.setText("User not found!");
            Toast.makeText(this, "Logged user not found", Toast.LENGTH_SHORT).show();
        }

        // Spinner Data
        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("Select Gender");
        genderList.add("male");
        genderList.add("female");

        ArrayList<String> roleList = new ArrayList<>();
        roleList.add("Select Role");
        roleList.add("admin");
        roleList.add("moderator");
        roleList.add("user");

        ArrayList<String> bloodGroupList = new ArrayList<>();
        bloodGroupList.add("Select Blood Group");
        bloodGroupList.add("A+");
        bloodGroupList.add("B+");
        bloodGroupList.add("O+");
        bloodGroupList.add("AB+");
        bloodGroupList.add("A-");
        bloodGroupList.add("B-");
        bloodGroupList.add("O-");
        bloodGroupList.add("AB-");

        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("Select Country");

        ArrayList<String> stateList = new ArrayList<>();
        stateList.add("Select State");

        ArrayList<String> sortOptions = new ArrayList<>();
        sortOptions.add("Select Sort Option");
        sortOptions.add("Age");
        sortOptions.add("Height");
        sortOptions.add("Name");
        sortOptions.add("Role Priority");

        // Fill country and state from userList
        if (userList != null) {
            HashSet<String> countries = new HashSet<>();
            HashSet<String> states = new HashSet<>();
            for (HashMap<String, String> user : userList) {
                countries.add(user.get("country"));
                states.add(user.get("state"));
            }
            countryList.addAll(countries);
            stateList.addAll(states);
        }

        // Set Spinners
        spinnerGender.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderList));
        spinnerRole.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roleList));
        spinnerBloodGroup.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bloodGroupList));
        spinnerCountry.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryList));
        spinnerState.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stateList));
        spinnerSortBy.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortOptions));

        // Spinner Change Listener
        AdapterView.OnItemSelectedListener filterListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFiltersAndSort();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinnerGender.setOnItemSelectedListener(filterListener);
        spinnerRole.setOnItemSelectedListener(filterListener);
        spinnerBloodGroup.setOnItemSelectedListener(filterListener);
        spinnerCountry.setOnItemSelectedListener(filterListener);
        spinnerState.setOnItemSelectedListener(filterListener);
        spinnerSortBy.setOnItemSelectedListener(filterListener);
    }

    private void applyFiltersAndSort() {
        filteredList.clear();

        String selectedGender = spinnerGender.getSelectedItem().toString();
        String selectedRole = spinnerRole.getSelectedItem().toString();
        String selectedBlood = spinnerBloodGroup.getSelectedItem().toString();
        String selectedCountry = spinnerCountry.getSelectedItem().toString();
        String selectedState = spinnerState.getSelectedItem().toString();
        String selectedSort = spinnerSortBy.getSelectedItem().toString();

        for (HashMap<String, String> user : userList) {
            if (!selectedGender.equals("Select Gender") && !user.get("gender").equalsIgnoreCase(selectedGender))
                continue;
            if (!selectedRole.equals("Select Role") && !user.get("role").equalsIgnoreCase(selectedRole))
                continue;
            if (!selectedBlood.equals("Select Blood Group") && !user.get("bloodGroup").equalsIgnoreCase(selectedBlood))
                continue;
            if (!selectedCountry.equals("Select Country") && !user.get("country").equalsIgnoreCase(selectedCountry))
                continue;
            if (!selectedState.equals("Select State") && !user.get("state").equalsIgnoreCase(selectedState))
                continue;

            filteredList.add(user);
        }

        // Sort the filtered list
        if (!selectedSort.equals("Select Sort Option")) {
            Collections.sort(filteredList, (a, b) -> {
                switch (selectedSort) {
                    case "Age":
                        return Integer.compare(Integer.parseInt(a.get("age")), Integer.parseInt(b.get("age")));
                    case "Height":
                        return Float.compare(Float.parseFloat(a.get("height")), Float.parseFloat(b.get("height")));
                    case "Name":
                        return (a.get("firstName") + a.get("lastName")).compareToIgnoreCase(b.get("firstName") + b.get("lastName"));
                    case "Role Priority":
                        return getRolePriority(a.get("role")) - getRolePriority(b.get("role"));
                }
                return 0;
            });
        }

        // TODO: Update your RecyclerView adapter here with filteredList
        Toast.makeText(this, "Filtered users: " + filteredList.size(), Toast.LENGTH_SHORT).show();
    }

    private int getRolePriority(String role) {
        switch (role.toLowerCase()) {
            case "admin":
                return 1;
            case "moderator":
                return 2;
            default:
                return 3;
        }
    }
}
