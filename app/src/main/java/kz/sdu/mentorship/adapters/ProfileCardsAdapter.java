package kz.sdu.mentorship.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.sdu.mentorship.R;

public class ProfileCardsAdapter extends RecyclerView.Adapter<ProfileCardsAdapter.ProfileCardViewHolder> {
    List<String> titles;
    List<List<String>> chips;
    List<String> timePeriods;

    public ProfileCardsAdapter(List<String> titles, List<List<String>> chips, List<String> timePeriods) {
        this.titles = titles;
        this.chips = chips;
        this.timePeriods = timePeriods;
    }

    @NonNull
    @Override
    public ProfileCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_card_list_item, parent, false);

        return new ProfileCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileCardViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class ProfileCardViewHolder extends RecyclerView.ViewHolder {
        private ImageView cardImage;
        private TextView titleView;
        private TextView firstChip;
        private TextView secondChip;
        private TextView timeView;

        public ProfileCardViewHolder(@NonNull View itemView) {
            super(itemView);

            cardImage = itemView.findViewById(R.id.card_image);
            titleView = itemView.findViewById(R.id.title);
            firstChip = itemView.findViewById(R.id.first_chip);
            secondChip = itemView.findViewById(R.id.second_chip);
            timeView = itemView.findViewById(R.id.time_period);
        }

        void bind(int position) {
            titleView.setText(titles.get(position));
            List<String> currentChips = chips.get(position);
            if (currentChips.size() > 0) {
                firstChip.setText(currentChips.get(0));
                firstChip.setVisibility(View.VISIBLE);
            }
            if (currentChips.size() > 1) {
                secondChip.setText(currentChips.get(1));
                secondChip.setVisibility(View.VISIBLE);
            }
            timeView.setText(timePeriods.get(position));
        }
    }
}
