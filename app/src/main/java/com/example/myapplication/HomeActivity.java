package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication.logInActivity.logedInUser;

public class HomeActivity extends AppCompatActivity {
    public  static  String LU=logedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }
    public void gotoAdmin(View view) {
        Intent intent = new Intent(this, AdminPage.class);
        startActivity(intent);
    }

    public void gotoShowPolls(View view) {
        Intent intent2 = new Intent(this, ShowPollsActivity.class);
        startActivity(intent2);
    }

    public void gotoProfile(View view) {
        Intent intent3 = new Intent(this, Profile.class);
        startActivity(intent3);
    }
    public void gotoWinners(View view) {
        Intent intent4 = new Intent(this, WinnerList.class);
        startActivity(intent4);
    }
}
//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

