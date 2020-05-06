package ch.noseryoung.lernendeverwaltung;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ApprenticeAdapter extends RecyclerView.Adapter<ApprenticeAdapter.MyViewHolder> {
    private ArrayList<String> apprenticeDataset;
    OnListItemClickListener onItemClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ApprenticeAdapter(ArrayList<String> apprenticeDataset, OnListItemClickListener onItemClickListener ) {
        this.apprenticeDataset = apprenticeDataset;
        this.onItemClickListener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_apprentice, parent, false);
        MyViewHolder vh = new MyViewHolder(v, onItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from apprentice dataset at this position
        // - replace the contents of the view with that element
        String[] apprenticeDatas = apprenticeDataset.get(position).split(" ");
        holder.firstname.setText(apprenticeDatas[0]);
        holder.lastname.setText(apprenticeDatas[1]);
    }

    // Return the size of the apprentice dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return apprenticeDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnListItemClickListener onItemClickListener;

        // each data item is just a string in this case
        public TextView firstname;
        public TextView lastname;

        public MyViewHolder(View v, OnListItemClickListener onItemclickListener) {
            super(v);
            firstname = v.findViewById(R.id.list_apprentice_firstnameTextView);
            lastname = v.findViewById(R.id.list_apprentice_lastnameTextView);
            this.onItemClickListener = onItemclickListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnListItemClickListener {

        void onItemClick(int position);
    }
}
