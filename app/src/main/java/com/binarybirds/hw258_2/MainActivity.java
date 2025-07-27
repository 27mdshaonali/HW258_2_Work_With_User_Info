package com.binarybirds.hw258_2;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import soup.neumorphism.NeumorphImageView;

public class MainActivity extends AppCompatActivity {

    ArrayList<JSONObject> userList = new ArrayList<>();
    ArrayList<JSONObject> filteredList = new ArrayList<>();

    Spinner spinnerGender, spinnerRole, spinnerBloodGroup, spinnerCountry, spinnerState, spinnerSortBy;
    SearchView searchView;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView username, fullNameSet;
    RoundedImageView userRoundedImage;
    String loggedUsername, loggedRole, loggedEmail;

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
        userRoundedImage = findViewById(R.id.userRoundedImage);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Receive intent data
        String jsonArrayString = getIntent().getStringExtra("userListJson");
        loggedUsername = getIntent().getStringExtra("loggedUsername");
        String loggedGender = getIntent().getStringExtra("loggedGender");
        String loggedProfileImage = getIntent().getStringExtra("loggedProfileImage");
        loggedRole = getIntent().getStringExtra("loggedRole");

        if (loggedGender != null) {
            Picasso.get().load(loggedProfileImage).into(userRoundedImage);
        } else {
            userRoundedImage.setVisibility(GONE);
        }

        if (jsonArrayString == null) {
            Toast.makeText(this, "Error reading user list", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            for (int i = 0; i < jsonArray.length(); i++) {
                userList.add(jsonArray.getJSONObject(i));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to parse user data", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userList != null && loggedUsername != null) {
            for (JSONObject user : userList) {
                if (loggedUsername.equals(user.optString("username"))) {
                    String fullName = user.optString("firstName") + " " + user.optString("lastName");
                    fullNameSet.setText(fullName + "!");
                    username.setText("@" + loggedUsername);
                    loggedEmail = user.optString("email"); // Store logged-in user's email
                    break;
                }
            }
        } else {
            fullNameSet.setText("User not found!");
            username.setText("User not found!");
        }



        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("Select Gender");
        genderList.add("male");
        genderList.add("female");

        ArrayList<String> roleList = new ArrayList<>();
        roleList.add("Select Role");

        // Set up role spinner based on loggedRole
        if (loggedRole != null) {
            if (loggedRole.equalsIgnoreCase("admin")) {
                roleList.add("admin");
                roleList.add("moderator");
                roleList.add("user");
            } else if (loggedRole.equalsIgnoreCase("moderator")) {
                roleList.add("moderator");
                roleList.add("user");
            } else if (loggedRole.equalsIgnoreCase("user")) {
                spinnerRole.setVisibility(GONE);
            }
        }

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

        HashSet<String> countries = new HashSet<>();
        HashSet<String> states = new HashSet<>();

        for (JSONObject user : userList) {
            JSONObject address = user.optJSONObject("address");
            if (address != null) {
                String country = address.optString("country");
                String state = address.optString("state");
                if (!country.isEmpty()) countries.add(country);
                if (!state.isEmpty()) states.add(state);
            }
        }

        countryList.addAll(countries);
        stateList.addAll(states);

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

        filteredList.addAll(userList);
        userAdapter = new UserAdapter(this, filteredList);
        recyclerView.setAdapter(userAdapter);
    }

    private void applyFiltersAndSort(String query) {
        filteredList.clear();

        String gender = spinnerGender.getSelectedItem().toString();
        String role = spinnerRole.getVisibility() == View.VISIBLE ? spinnerRole.getSelectedItem().toString() : "Select Role";
        String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();
        String country = spinnerCountry.getSelectedItem().toString();
        String state = spinnerState.getSelectedItem().toString();
        String sortBy = spinnerSortBy.getSelectedItem().toString();

        for (JSONObject user : userList) {
            String userRole = user.optString("role");

            if (loggedRole.equalsIgnoreCase("moderator") && userRole.equalsIgnoreCase("admin"))
                continue;
            if (loggedRole.equalsIgnoreCase("user") && (userRole.equalsIgnoreCase("admin") || userRole.equalsIgnoreCase("moderator")))
                continue;

            if (!gender.equals("Select Gender") && !user.optString("gender").equalsIgnoreCase(gender))
                continue;
            if (spinnerRole.getVisibility() == View.VISIBLE && !role.equals("Select Role") && !user.optString("role").equalsIgnoreCase(role))
                continue;
            if (!bloodGroup.equals("Select Blood Group") && !user.optString("bloodGroup").equalsIgnoreCase(bloodGroup))
                continue;

            JSONObject address = user.optJSONObject("address");
            if (!country.equals("Select Country") && (address == null || !address.optString("country").equalsIgnoreCase(country)))
                continue;
            if (!state.equals("Select State") && (address == null || !address.optString("state").equalsIgnoreCase(state)))
                continue;

            if (!query.isEmpty()) {
                String name = user.optString("firstName") + " " + user.optString("lastName");
                String email = user.optString("email");
                String username = user.optString("username");

                if (!(name.toLowerCase().contains(query.toLowerCase()) || email.toLowerCase().contains(query.toLowerCase()) || username.toLowerCase().contains(query.toLowerCase()))) {
                    continue;
                }
            }

            filteredList.add(user);
        }

        if (!sortBy.equals("Select Sort Option")) {
            Collections.sort(filteredList, (a, b) -> {
                switch (sortBy) {
                    case "Age":
                        return Integer.compare(a.optInt("age"), b.optInt("age"));
                    case "Height":
                        return Double.compare(a.optDouble("height"), b.optDouble("height"));
                    case "Name":
                        return (a.optString("firstName") + a.optString("lastName")).compareToIgnoreCase(b.optString("firstName") + b.optString("lastName"));

                    case "Role Priority":
                        return getRolePriority(a.optString("role")) - getRolePriority(b.optString("role"));

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

        Context context;
        ArrayList<JSONObject> users;

        public UserAdapter(Context context, ArrayList<JSONObject> users) {
            this.context = context;
            this.users = users;
        }

        @Override
        public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.user_info, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(UserViewHolder holder, int position) {
            JSONObject user = users.get(position);

            String fullName = user.optString("firstName") + " " + user.optString("lastName");
            holder.tvFullName.setText(fullName);

            JSONObject company = user.optJSONObject("company");
            if (company != null) {
                String title = company.optString("title");
                holder.tvPosition.setText(title);
            } else {
                holder.tvPosition.setText("N/A"); // fallback
            }


            holder.tvPhone.setText(user.optString("phone"));
            holder.tvEmail.setText(user.optString("email"));
            holder.bloodUser.setText(user.optString("bloodGroup"));

            String imageUrl = user.optString("image");
            if (!imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(holder.profileImage);
            } else {
                holder.profileImage.setImageResource(R.drawable.app_icon2);
            }

            holder.cardUser.setOnClickListener(v -> {
                Intent intent = new Intent(context, UserDetailsActivity.class);
                intent.putExtra("userDataJson", user.toString());
                intent.putExtra("loggedEmail", loggedEmail);
                context.startActivity(intent);
            });

            holder.tvEmail.setOnClickListener(v -> {
                String recipientEmail = user.optString("email");
                sendEmail(recipientEmail);
            });

            holder.call.setOnClickListener(v -> {
                String phoneNumber = user.optString("phone");
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                context.startActivity(intent);
            });

            holder.message.setOnClickListener(v -> {

                String phoneNumber = user.optString("phone");
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + phoneNumber));
                context.startActivity(intent);

            });

            holder.profileImage.setOnClickListener(v -> {

                startActivity(new Intent(context, ModeratorUserDetailsActivity.class));

            });


        }

        private void sendEmail(String recipientEmail) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:")); // Only email apps should handle this

            // Set the recipient email
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});

            // Include the sender's email in the body
            String emailBody = "This email is sent from: " + loggedEmail + "\n\n";
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Message from User Directory");
            emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

            try {
                context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public class UserViewHolder extends RecyclerView.ViewHolder {
            TextView tvFullName, tvPosition, tvPhone, tvEmail, bloodUser;
            RoundedImageView profileImage;
            CardView cardUser;
            NeumorphImageView call, message;

            public UserViewHolder(View itemView) {
                super(itemView);
                tvFullName = itemView.findViewById(R.id.nameUser);
                tvPosition = itemView.findViewById(R.id.positionUser);
                tvPhone = itemView.findViewById(R.id.phoneUser);
                tvEmail = itemView.findViewById(R.id.emailUser);
                profileImage = itemView.findViewById(R.id.imageUser);
                cardUser = itemView.findViewById(R.id.cardUser);
                call = itemView.findViewById(R.id.call);
                message = itemView.findViewById(R.id.message);
                bloodUser = itemView.findViewById(R.id.bloodUser);
            }
        }
    }
}