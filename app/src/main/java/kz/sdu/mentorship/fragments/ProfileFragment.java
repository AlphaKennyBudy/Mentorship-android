package kz.sdu.mentorship.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

import kz.sdu.mentorship.R;
import kz.sdu.mentorship.adapters.ProfileSkillsAdapter;

public class ProfileFragment extends Fragment {
    private Context context;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = view.getContext();
        setDummyContent(view);
        initSkillRecyclerView(view);
        return view;
    }

    private void initSkillRecyclerView(View view) {
        RecyclerView skillsView = view.findViewById(R.id.rv_skills);
        List<Integer> skillImages = generateDummyImages(R.drawable.dummy_skill, 10);
        ProfileSkillsAdapter skillsAdapter = new ProfileSkillsAdapter(skillImages);
        skillsView.setAdapter(skillsAdapter);
        skillsView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    private List<Integer> generateDummyImages(int source, int count) {
        List<Integer> images = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            images.add(source);
        }
        return images;
    }

    private void setDummyContent(View view) {
        TextView nameView = view.findViewById(R.id.full_name);
        nameView.setText("Adil Akhmetov");
        TextView descriptionView = view.findViewById(R.id.description);
        descriptionView.setText("DevOps Engineer");
        TextView locationView = view.findViewById(R.id.location);
        locationView.setText("Almaty, Kazakhstan");
        TextView educationView = view.findViewById(R.id.education);
        educationView.setText("Suleyman Demirel University");
        setDefaultProfileImage(view, String.valueOf(nameView.getText().charAt(0)));
    }

    private void setDefaultProfileImage(View view, String firstLetter) {
        ImageView profileImage = view.findViewById(R.id.profile_image);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, Color.BLACK);
        profileImage.setImageDrawable(drawable);
    }
}
