package com.binarybirds.hw258_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> arrayList, filteredList;
    Spinner spinnerGender, spinnerRole, spinnerBloodGroup, spinnerCountry, spinnerState, spinnerSortBy;
    SearchView searchView;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    TextView fullName, username;
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

        username = findViewById(R.id.username);
        fullName = findViewById(R.id.fullName);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerRole = findViewById(R.id.spinnerRole);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerSortBy = findViewById(R.id.spinnerSortBy);

        arrayList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("userList");
        filteredList = new ArrayList<>();
        loggedUsername = getIntent().getStringExtra("loggedUsername");

        if (arrayList == null || arrayList.isEmpty()) {
            Toast.makeText(this, "Error reading user list", Toast.LENGTH_SHORT).show();
            return;
        }

        // greet
        for (HashMap<String, String> user : arrayList) {
            if (loggedUsername.equals(user.get("userSignInUserName"))) {
                String fullNameText = user.get("firstName") + " " + user.get("lastName");
                fullName.setText(fullNameText + "!");
                username.setText("@" + loggedUsername);
                break;
            }
        }

        // spinners
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
        Collections.addAll(bloodGroupList, "A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-");

        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("Select Country");

        ArrayList<String> stateList = new ArrayList<>();
        stateList.add("Select State");

        ArrayList<String> sortList = new ArrayList<>();
        sortList.add("Select Sort Option");
        Collections.addAll(sortList, "Age", "Height", "Name", "Role Priority");

        HashSet<String> countries = new HashSet<>();
        HashSet<String> states = new HashSet<>();

        for (HashMap<String, String> user : arrayList) {
            String country = user.get("homeCountry");
            String state = user.get("homeState");

            if (country != null && !country.isEmpty()) countries.add(country);
            if (state != null && !state.isEmpty()) states.add(state);
        }

        countryList.addAll(countries);
        stateList.addAll(states);

        spinnerGender.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderList));
        spinnerRole.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roleList));
        spinnerBloodGroup.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bloodGroupList));
        spinnerCountry.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryList));
        spinnerState.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stateList));
        spinnerSortBy.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortList));

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFiltersAndSort(searchView.getQuery().toString());
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        };

        spinnerGender.setOnItemSelectedListener(listener);
        spinnerRole.setOnItemSelectedListener(listener);
        spinnerBloodGroup.setOnItemSelectedListener(listener);
        spinnerCountry.setOnItemSelectedListener(listener);
        spinnerState.setOnItemSelectedListener(listener);
        spinnerSortBy.setOnItemSelectedListener(listener);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                applyFiltersAndSort(query);
                return true;
            }
            @Override public boolean onQueryTextChange(String newText) {
                applyFiltersAndSort(newText);
                return true;
            }
        });

        filteredList.addAll(arrayList);
        userAdapter = new UserAdapter(this, filteredList);
        recyclerView.setAdapter(userAdapter);
    }

    private void applyFiltersAndSort(String query) {
        filteredList.clear();

        String gender = spinnerGender.getSelectedItem().toString();
        String role = spinnerRole.getSelectedItem().toString();
        String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();
        String country = spinnerCountry.getSelectedItem().toString();
        String state = spinnerState.getSelectedItem().toString();
        String sort = spinnerSortBy.getSelectedItem().toString();

        for (HashMap<String, String> user : arrayList) {
            if (!gender.equals("Select Gender") && !user.get("gender").equalsIgnoreCase(gender)) continue;
            if (!role.equals("Select Role") && !user.get("role").equalsIgnoreCase(role)) continue;
            if (!bloodGroup.equals("Select Blood Group") && !user.get("bloodGroup").equalsIgnoreCase(bloodGroup)) continue;
            if (!country.equals("Select Country") && !user.get("homeCountry").equalsIgnoreCase(country)) continue;
            if (!state.equals("Select State") && !user.get("homeState").equalsIgnoreCase(state)) continue;

            if (!query.isEmpty()) {
                String name = user.get("firstName").toLowerCase() + " " + user.get("lastName").toLowerCase();
                String email = user.get("email").toLowerCase();
                String uname = user.get("userSignInUserName").toLowerCase();
                if (!(name.contains(query.toLowerCase()) || email.contains(query.toLowerCase()) || uname.contains(query.toLowerCase())))
                    continue;
            }

            filteredList.add(user);
        }

        if (!sort.equals("Select Sort Option")) {
            Comparator<HashMap<String, String>> comparator = null;
            switch (sort) {
                case "Age":
                    comparator = Comparator.comparingInt(u -> Integer.parseInt(u.get("age")));
                    break;
                case "Height":
                    comparator = Comparator.comparingDouble(u -> Double.parseDouble(u.get("height")));
                    break;
                case "Name":
                    comparator = Comparator.comparing(u -> (u.get("firstName") + u.get("lastName")));
                    break;
                case "Role Priority":
                    comparator = Comparator.comparingInt(u -> getRolePriority(u.get("role")));
                    break;
            }
            if (comparator != null) Collections.sort(filteredList, comparator);
        }

        userAdapter.notifyDataSetChanged();
    }

    private int getRolePriority(String role) {
        switch (role.toLowerCase()) {
            case "admin": return 1;
            case "moderator": return 2;
            default: return 3;
        }
    }

    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

        Context context;
        ArrayList<HashMap<String, String>> list;

        public UserAdapter(Context context, ArrayList<HashMap<String, String>> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.user_info, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(UserViewHolder holder, int position) {
            HashMap<String, String> user = list.get(position);
            holder.tvFullName.setText(user.get("firstName") + " " + user.get("lastName"));
            holder.tvPosition.setText(user.get("role"));
            holder.tvPhone.setText(user.get("phone"));
            holder.tvEmail.setText(user.get("email"));

            String image = user.get("image");
            if (image != null && !image.isEmpty()) {
                Picasso.get().load(image).into(holder.profileImage);
            } else {
                holder.profileImage.setImageResource(R.drawable.app_icon2);
            }

            holder.cardUser.setOnClickListener(v -> {
                try {
                    JSONObject json = new JSONObject(user);
                    Intent intent = new Intent(context, UserDetailsActivity.class);
                    intent.putExtra("userDataJson", json.toString());
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Error loading user details", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class UserViewHolder extends RecyclerView.ViewHolder {
            TextView tvFullName, tvPosition, tvPhone, tvEmail;
            RoundedImageView profileImage;
            CardView cardUser;

            public UserViewHolder(View itemView) {
                super(itemView);
                tvFullName = itemView.findViewById(R.id.nameUser);
                tvPosition = itemView.findViewById(R.id.positionUser);
                tvPhone = itemView.findViewById(R.id.phoneUser);
                tvEmail = itemView.findViewById(R.id.emailUser);
                profileImage = itemView.findViewById(R.id.imageUser);
                cardUser = itemView.findViewById(R.id.cardUser);
            }
        }
    }
}
