package com.example.projetws;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projetws.beans.Etudiant;

import java.util.ArrayList;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.ViewHolder> implements Filterable {

    private ArrayList<Etudiant> etudiantList;
    private ArrayList<Etudiant> etudiantListFull;
    private Context context;

    public EtudiantAdapter(Context context, ArrayList<Etudiant> etudiantList) {
        this.context = context;
        this.etudiantList = etudiantList; // Copie de la liste originale
        this.etudiantListFull = new ArrayList<>(etudiantList);
        // Sauvegarde de la liste complète
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Etudiant> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(etudiantListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Etudiant etudiant : etudiantListFull) {
                        if (etudiant.getNom().toLowerCase().contains(filterPattern) ||
                                etudiant.getPrenom().toLowerCase().contains(filterPattern)) {
                            filteredList.add(etudiant);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                // Log to check what's happening during filtering
                System.out.println("Filtrage effectué : " + filteredList.size() + " résultats trouvés.");

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                etudiantList.clear();
                etudiantList.addAll((ArrayList<Etudiant>) results.values);

                // Log pour vérifier la taille des résultats filtrés
                System.out.println("Nombre de résultats après filtrage : " + etudiantList.size());
                notifyDataSetChanged();
            }


        };
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_etudiant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Etudiant etudiant = etudiantList.get(position);

        holder.nomTextView.setText(etudiant.getNom());
        holder.prenomTextView.setText(etudiant.getPrenom());
        holder.villeTextView.setText(etudiant.getVille());
        holder.sexeTextView.setText(etudiant.getSexe());

        // Load the photo with Glide (replace with actual image logic if needed)
        String imageUrl = etudiant.getPhoto();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.star)
                    .into(holder.imageView);
        } else {
            Glide.with(context)
                    .load(R.drawable.star)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return etudiantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomTextView, prenomTextView, villeTextView, sexeTextView;
        public de.hdodenhof.circleimageview.CircleImageView imageView;
        public Button editButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nomTextView = itemView.findViewById(R.id.nom);
            prenomTextView = itemView.findViewById(R.id.prenom);
            villeTextView = itemView.findViewById(R.id.ville);
            sexeTextView = itemView.findViewById(R.id.sexe);
            imageView = itemView.findViewById(R.id.img);
            editButton = itemView.findViewById(R.id.edit);

            editButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Etudiant etudiant = etudiantList.get(position);
                    Intent intent = new Intent(context, EditEtudiantActivity.class);
                    intent.putExtra("etudiant_id", etudiant.getId());
                    intent.putExtra("etudiant_nom", etudiant.getNom());
                    intent.putExtra("etudiant_prenom", etudiant.getPrenom());
                    intent.putExtra("etudiant_ville", etudiant.getVille());
                    intent.putExtra("etudiant_sexe", etudiant.getSexe());
                    intent.putExtra("etudiant_photo", etudiant.getPhoto());
                    // Start activity for result
                    ((Activity) context).startActivityForResult(intent, ListEtudiant.REQUEST_CODE_EDIT);
                }
            });
        }
    }

    public void removeItem(int position) {
        etudiantList.remove(position);
        notifyItemRemoved(position);
    }
}
