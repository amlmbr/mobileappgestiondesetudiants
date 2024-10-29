package com.example.projetws;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditEtudiantActivity extends AppCompatActivity {

    private EditText editNom, editPrenom, editVille, editSexe,editPhoto;
    private Button btnModifier, btnSupprimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_etudiant);
        getSupportActionBar().setTitle("Editer les information");

        // Initialize views
        editNom = findViewById(R.id.edit_nom);
        editPrenom = findViewById(R.id.edit_prenom);
        editVille = findViewById(R.id.edit_ville);
        editSexe = findViewById(R.id.edit_sexe);
        editPhoto = findViewById(R.id.edit_photo);
        btnModifier = findViewById(R.id.btn_modifier);
        btnSupprimer = findViewById(R.id.btn_supprimer);

        // Get the passed data from Intent
        Intent intent = getIntent();
        int etudiantId = intent.getIntExtra("etudiant_id", -1);
        String etudiantNom = intent.getStringExtra("etudiant_nom");
        String etudiantPrenom = intent.getStringExtra("etudiant_prenom");
        String etudiantVille = intent.getStringExtra("etudiant_ville");
        String etudiantSexe = intent.getStringExtra("etudiant_sexe");
        String etudiantPhoto = intent.getStringExtra("etudiant_photo");

        // Set the data in EditText fields
        editNom.setText(etudiantNom);
        editPrenom.setText(etudiantPrenom);
        editVille.setText(etudiantVille);
        editSexe.setText(etudiantSexe);
        editPhoto.setText(etudiantPhoto);
        // Set up event listeners for the modify and delete buttons
        btnModifier.setOnClickListener(v -> {
            String newNom = editNom.getText().toString();
            String newPrenom = editPrenom.getText().toString();
            String newVille = editVille.getText().toString();
            String newSexe = editSexe.getText().toString();
            String newPhoto = editPhoto.getText().toString();

            // Create an Intent to hold the result data
            Intent resultIntent = new Intent();
            resultIntent.putExtra("etudiant_id", etudiantId);
            resultIntent.putExtra("etudiant_nom", newNom);
            resultIntent.putExtra("etudiant_prenom", newPrenom);
            resultIntent.putExtra("etudiant_ville", newVille);
            resultIntent.putExtra("etudiant_sexe", newSexe);
            resultIntent.putExtra("etudiant_photo", newPhoto);
            setResult(RESULT_OK, resultIntent);
            finish(); // Close the activity
        });

        btnSupprimer.setOnClickListener(v -> {
            // Send a result to delete the student
            Intent resultIntent = new Intent();
            resultIntent.putExtra("etudiant_id", etudiantId);
            setResult(RESULT_OK, resultIntent);
            finish();
        });


        btnSupprimer.setOnClickListener(v -> {
            // Delete logic here, e.g., call an API to delete the student
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            // Navigate back to ListEtudiant activity
            Intent intent = new Intent(this, ListEtudiant.class);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            // Handle the action bar's Up button
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



}
