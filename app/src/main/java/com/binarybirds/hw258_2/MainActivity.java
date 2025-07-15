package com.binarybirds.hw258_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> userList;
    ArrayList<HashMap<String, String>> filteredList = new ArrayList<>();

    Spinner spinnerGender, spinnerRole, spinnerBloodGroup, spinnerCountry, spinnerState, spinnerSortBy;
    SearchView searchView;
    RecyclerView recyclerView;
    UserAdapter userAdapter;

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

        // Initialize
        userList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("userList");
        loggedUsername = getIntent().getStringExtra("loggedUsername");

        username = findViewById(R.id.username);
        fullNameSet = findViewById(R.id.fullName);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerRole = findViewById(R.id.spinnerRole);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerSortBy = findViewById(R.id.spinnerSortBy);

        // Greet logged-in user
        if (userList != null && loggedUsername != null) {
            for (HashMap<String, String> user : userList) {
                if (loggedUsername.equals(user.get("userSignInUserName"))) {
                    String fullName = user.get("firstName") + " " + user.get("lastName");
                    fullNameSet.setText(fullName + "!");
                    username.setText("@" + loggedUsername);
                    break;
                }
            }
        } else {
            fullNameSet.setText("User not found!");
            username.setText("User not found!");
            Toast.makeText(this, "Logged user not found", Toast.LENGTH_SHORT).show();
        }

        // Prepare Spinner Options
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

        ArrayList<String> sortOptions = new ArrayList<>();
        sortOptions.add("Select Sort Option");
        Collections.addAll(sortOptions, "Age", "Height", "Name", "Role Priority");

        if (userList != null) {
            HashSet<String> countries = new HashSet<>();
            HashSet<String> states = new HashSet<>();

            for (HashMap<String, String> user : userList) {
                String country = user.get("country");
                String state = user.get("state");

                if (country != null && !country.trim().isEmpty()) {
                    countries.add(country);
                }
                if (state != null && !state.trim().isEmpty()) {
                    states.add(state);
                }
            }

            countryList.addAll(countries);
            stateList.addAll(states);
        }

        spinnerGender.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderList));
        spinnerRole.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roleList));
        spinnerBloodGroup.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bloodGroupList));
        spinnerCountry.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryList));
        spinnerState.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stateList));
        spinnerSortBy.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortOptions));

        AdapterView.OnItemSelectedListener filterListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFiltersAndSort(searchView.getQuery().toString());
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFiltersAndSort(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                applyFiltersAndSort(newText);
                return true;
            }
        });

        // Set Initial Data
        filteredList.addAll(userList);
        userAdapter = new UserAdapter(this, filteredList);
        recyclerView.setAdapter(userAdapter);
    }

    private void applyFiltersAndSort(String query) {
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

            if (query != null && !query.isEmpty()) {
                String name = (user.get("firstName") + " " + user.get("lastName")).toLowerCase();
                String email = user.get("email").toLowerCase();
                String uname = user.get("userSignInUserName").toLowerCase();
                if (!(name.contains(query.toLowerCase()) || email.contains(query.toLowerCase()) || uname.contains(query.toLowerCase())))
                    continue;
            }

            filteredList.add(user);
        }

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

        userAdapter.notifyDataSetChanged();
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

    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

        private final Context context;
        private final ArrayList<HashMap<String, String>> userList;

        public UserAdapter(Context context, ArrayList<HashMap<String, String>> userList) {
            this.context = context;
            this.userList = userList;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.user_info, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            HashMap<String, String> user = userList.get(position);

            String fullName = user.get("firstName") + " " + user.get("lastName");
            String positionStr = user.get("role");
            String phone = user.get("phone");
            String email = user.get("email");
            String imageUrl = user.get("image");

            holder.tvFullName.setText(fullName);
            holder.tvPosition.setText(positionStr);
            holder.tvPhone.setText(phone);
            holder.tvEmail.setText(email);

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(holder.profileImage);
            } else {
                holder.profileImage.setImageResource(R.drawable.app_icon2);
            }

            holder.cardUser.setOnClickListener(v -> {
                try {
                    JSONObject userJson = new JSONObject(user);
                    Intent intent = new Intent(context, UserDetailsActivity.class);
                    intent.putExtra("userDataJson", userJson.toString());
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Error parsing user data", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        public class UserViewHolder extends RecyclerView.ViewHolder {
            TextView tvFullName, tvUsername, tvEmail, tvPhone, tvPosition;
            CardView cardUser;
            RoundedImageView profileImage;

            public UserViewHolder(@NonNull View itemView) {
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
