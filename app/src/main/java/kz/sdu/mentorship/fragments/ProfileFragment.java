package kz.sdu.mentorship.fragments;

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

import com.amulyakhare.textdrawable.TextDrawable;

import kz.sdu.mentorship.R;

public class ProfileFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setDummyContent(view);
        return view;
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
