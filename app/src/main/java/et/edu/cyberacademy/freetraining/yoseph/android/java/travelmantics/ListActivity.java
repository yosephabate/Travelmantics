package et.edu.cyberacademy.freetraining.yoseph.android.java.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        FirebaseUtils.openFBReference("traveldeals", this);
//
//        RecyclerView rvDeals = findViewById(R.id.rvDeals);
//        final DealAdapter adapter = new DealAdapter();
//        rvDeals.setAdapter(adapter);
//        LinearLayoutManager dealsLayoutManager =
//                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        rvDeals.setLayoutManager(dealsLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);

        MenuItem insertMenu = menu.findItem(R.id.insert_menu);
        if (FirebaseUtils.isAdmin) {
            insertMenu.setVisible(true);
        } else {
            insertMenu.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_menu:
                Intent intent = new Intent(this, DealActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout_menu:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Logout", "User Logged Out");
                                FirebaseUtils.attachListener();
                            }
                        });
                FirebaseUtils.detachListener();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtils.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUtils.openFBReference("traveldeals", this);

        RecyclerView rvDeals = findViewById(R.id.rvDeals);
        // Apply Item Decoration
        rvDeals.addItemDecoration(new DividerItemDecoration(getApplicationContext()));

        final DealAdapter adapter = new DealAdapter();
        rvDeals.setAdapter(adapter);
        rvDeals.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager dealsLayoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvDeals.setLayoutManager(dealsLayoutManager);

        FirebaseUtils.attachListener();
    }

    public void showMenu() {
        invalidateOptionsMenu();
    }
}
