package com.example.projetws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.example.projetws.beans.Etudiant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListEtudiant extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EtudiantAdapter etudiantAdapter;
    private ArrayList<Etudiant> etudiants;
    private RequestQueue requestQueue;
    private String loadUrl = "http://10.0.2.2/TPVolley/ws/loadEtudiant.php";
    public static final int REQUEST_CODE_EDIT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Liste des étudiants");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(etudiantAdapter);


        etudiants = new ArrayList<>();
        etudiantAdapter = new EtudiantAdapter(this, etudiants);
        recyclerView.setAdapter(etudiantAdapter);

        requestQueue = Volley.newRequestQueue(this);


        loadEtudiants();

        Button btnAjouterEtudiant = findViewById(R.id.btnAjouterEtudiant);
        btnAjouterEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity to add a new student
                Intent intent = new Intent(ListEtudiant.this, AddEtudiant.class);
                startActivity(intent);
            }
        });


        setupSwipeToDelete();



    }

    private void loadEtudiants() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, loadUrl, null,
                response -> {
                    try {
                        etudiants.clear(); // Assure-toi de vider la liste avant de la remplir
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject etudiantObject = response.getJSONObject(i);
                            int id = etudiantObject.getInt("id");
                            String nom = etudiantObject.getString("nom");
                            String prenom = etudiantObject.getString("prenom");
                            String ville = etudiantObject.getString("ville");
                            String sexe = etudiantObject.getString("sexe");
                            String photo = etudiantObject.getString("photo");

                            Etudiant etudiant = new Etudiant(id, nom, prenom, ville, sexe, photo);
                            etudiants.add(etudiant);
                        }

                        etudiantAdapter.notifyDataSetChanged();
                        Toast.makeText(ListEtudiant.this, "Données chargées", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(ListEtudiant.this, "Erreur de chargement des données", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(jsonArrayRequest);
    }


    private void setupSwipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Etudiant etudiant = etudiants.get(position);


                new androidx.appcompat.app.AlertDialog.Builder(ListEtudiant.this)
                        .setTitle("Confirmation de suppression")
                        .setMessage("Voulez-vous vraiment supprimer l'étudiant " + etudiant.getNom() + " " + etudiant.getPrenom() + "?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                            etudiantAdapter.removeItem(position);
                            Toast.makeText(ListEtudiant.this, "Élève supprimé", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(android.R.string.no, (dialog, which) -> {

                            etudiantAdapter.notifyItemChanged(position);
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                etudiantAdapter.getFilter().filter(newText);
                return true;
            }
        });


        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            int etudiantId = data.getIntExtra("etudiant_id", -1);
            String nom = data.getStringExtra("etudiant_nom");
            String prenom = data.getStringExtra("etudiant_prenom");
            String ville = data.getStringExtra("etudiant_ville");
            String sexe = data.getStringExtra("etudiant_sexe");
            String photo = data.getStringExtra("etudiant_photo");


            for (Etudiant etudiant : etudiants) {
                if (etudiant.getId() == etudiantId) {
                    etudiant.setNom(nom);
                    etudiant.setPrenom(prenom);
                    etudiant.setVille(ville);
                    etudiant.setSexe(sexe);
                    etudiant.setPhoto(photo);
                    etudiantAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        // Recharger les étudiants au retour sur l'activité pour éviter les problèmes de disparition
        loadEtudiants();
    }






}