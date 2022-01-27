package com.veritasliberat.esp_trainer;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private final Session[] sessions;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Element " + getAdapterPosition() + " clicked.");
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), SessionResultsActivity.class);

                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

                    String completeSession = gson.toJson(sessions[position]);
                    intent.putExtra(Session.SESSION_EXTRA_KEY, completeSession);

                    v.getContext().startActivity(intent);
                }
            });
            textView = (TextView) v.findViewById(R.id.historyRowView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public CustomAdapter(Session[] sessions) {
        this.sessions = sessions;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(v);
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Session session = sessions[position];
        String displayText = session.endTimestamp + " | Score: " + session.score;
        viewHolder.getTextView().setTextSize(20);
        viewHolder.getTextView().setText(displayText);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return sessions.length;
    }
}
