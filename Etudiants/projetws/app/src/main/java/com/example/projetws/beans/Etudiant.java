package com.example.projetws.beans;

public class Etudiant {
    private int id;
    private String nom;
    private String prenom;
    private String ville;
    private String sexe;
    private String photo; // Nouveau champ pour l'URL de la photo

    // Constructeur par défaut
    public Etudiant() { }

    // Constructeur avec tous les champs
    public Etudiant(int id, String nom, String prenom, String ville, String sexe, String photo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.sexe = sexe;
        this.photo = photo; // Initialisation du champ photo
    }

    // Constructeur sans l'id
    public Etudiant(String nom, String prenom, String ville, String sexe, String photo) {
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.sexe = sexe;
        this.photo = photo; // Initialisation du champ photo
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPhoto() {
        return photo; // Getter pour le champ photo
    }

    public void setPhoto(String photo) {
        this.photo = photo; // Setter pour le champ photo
    }
}
