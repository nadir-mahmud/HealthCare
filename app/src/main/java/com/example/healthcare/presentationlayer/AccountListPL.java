package com.example.healthcare.presentationlayer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.MyOwnAdapter;
import com.example.healthcare.applicationlayer.SearchAdapter;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface;
import com.example.healthcare.entity.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class AccountListPL extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Account> accounts;

    MyOwnAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list_pl);

        accounts = new ArrayList<Account>();



        Toolbar toolbar = findViewById(R.id.toolbar_account_list);
        toolbar.setTitle("Account List");
        toolbar.inflateMenu(R.menu.doctor_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.doctor_logout){
                    Intent intent = new Intent(getApplicationContext(), LoginPL.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        });


        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAccountListInfo();

        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(getApplicationContext(), SignUpPL.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

    public void getAccountListInfo(){

        db.collection("accounts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData().);
                                Account account = new Account();
                                account.setEmail(document.getData().get("email").toString());
                                account.setRole(document.getData().get("role").toString());
                                accounts.add(account);
                            }
                            //Toast.makeText(getApplicationContext(), accounts.get(0).getEmail(),Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), accounts.get(1).getEmail(),Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        //Toast.makeText(getApplicationContext(),accounts.get(1).getEmail(),Toast.LENGTH_SHORT).show();
                        adapter = new MyOwnAdapter(getApplicationContext(), accounts, new RecyclerViewClickInterface() {
                            @Override
                            public void onItemClick(int position) {

                            }
                        });
                        recyclerView.setAdapter(adapter);
                        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

                            @Override
                            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                                //Toast.makeText(context, "on Move", Toast.LENGTH_SHORT).show();
                                return true;
                            }

                            @Override
                            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                                //Toast.makeText(context, "on Swiped ", Toast.LENGTH_SHORT).show();
                                //Remove swiped item from list and notify the RecyclerView
                                int position = viewHolder.getAdapterPosition();

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                if (accounts.size() > 0) {

                                        db.collection("accounts")
                                                .whereEqualTo("email", accounts.get(position).getEmail())
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {

                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                //Log.d(TAG, document.getId() + " => " + document.getData().);
                                                                if (document.exists()) {
                                                                    document.getReference().delete();
                                                                } else {
                                                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                                                }

                                                            }

                                                        } else {
                                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                                        }
                                                        //Toast.makeText(getApplicationContext(),accounts.get(1).getEmail(),Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                }

                                accounts.remove(position);


                                adapter.notifyDataSetChanged();

                            }
                        };

                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                        itemTouchHelper.attachToRecyclerView(recyclerView);

                    }
                });

    }




}