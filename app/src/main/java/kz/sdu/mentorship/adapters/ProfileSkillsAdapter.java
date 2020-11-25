package kz.sdu.mentorship.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.sdu.mentorship.R;

public class ProfileSkillsAdapter extends RecyclerView.Adapter<ProfileSkillsAdapter.SkillViewHolder> {
    List<Integer> images;

    public ProfileSkillsAdapter(List<Integer> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_skill_list_item, parent, false);

        return new ProfileSkillsAdapter.SkillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class SkillViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.skill_image);
        }

        void bind(int position) {
            imageView.setImageResource(images.get(position));
        }
    }
}
