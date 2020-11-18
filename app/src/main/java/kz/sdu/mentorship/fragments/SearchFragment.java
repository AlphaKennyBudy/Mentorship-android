package kz.sdu.mentorship.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kz.sdu.mentorship.R;
import kz.sdu.mentorship.adapters.JobsAdapter;
import kz.sdu.mentorship.models.Vacancy;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class SearchFragment extends Fragment implements JobsAdapter.OnJobListener {
    private RecyclerView jobsRecycler;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        context = view.getContext();
        jobsRecycler = view.findViewById(R.id.rv_jobs);
        configureJobsRecycler();
        createOnClickFilterListener(view, container);
        return view;
    }

    public void createOnClickFilterListener(final View view, final ViewGroup container) {
        Button filterButton = view.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                showFilterPopupWindow(view, container);
            }
        });
    }

    private void showFilterPopupWindow(View root, final ViewGroup container) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.filter_popup, null);
        Spinner sortBySpinner = popupView.findViewById(R.id.sort_by_spinner);
        setAdapterToSpinner(sortBySpinner, R.array.sort_by_items);
        Spinner categorySpinner = popupView.findViewById(R.id.category_spinner);
        setAdapterToSpinner(categorySpinner, R.array.category_items);

        applyDim(container, 0.5f);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.showAtLocation(root, Gravity.BOTTOM, 0, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim(container);
            }
        });
    }

    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    private void setAdapterToSpinner(Spinner spinner, int listId) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                spinner.getContext(),
                R.layout.spinner_item,
                getResources().getStringArray(listId)
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    private void configureJobsRecycler() {
        List<Vacancy> dummyVacancies = generateDummyVacancies();
        ArrayList<Integer> dummyImages = generateDummyImages(dummyVacancies.size());

        JobsAdapter jobsAdapter = new JobsAdapter(R.layout.nearby_job_list_item, dummyImages, dummyVacancies, this);
        jobsRecycler.setAdapter(jobsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        jobsRecycler.setLayoutManager(layoutManager);
        jobsRecycler.setHasFixedSize(true);
    }

    private List<Vacancy> generateDummyVacancies() {
        List<Vacancy> vacancies = new ArrayList<>();
        vacancies.add(new Vacancy("f493da2", "e12e34", "Android Developer", "Half time",
                "Nothing", 25000, "Kazakhstan", "Almaty", 1));
        vacancies.add(new Vacancy("f493da3", "e12e46", "iOS Developer", "Half time",
                "Nothing", 200000, "Kazakhstan", "Almaty", 1));
        vacancies.add(new Vacancy("f493da4", "e12e52", "Data Scientist", "Half time",
                "Nothing", 400000, "Kazakhstan", "Almaty", 1));
        vacancies.add(new Vacancy("f493d2d", "e12e52", "DL Engineer", "Half time",
                "Nothing", 40000, "Kazakhstan", "Almaty", 1));
        vacancies.add(new Vacancy("f493d12", "e12e56", "Product Management", "Half time",
                "Nothing", 30000, "Kazakhstan", "Almaty", 1));
        vacancies.add(new Vacancy("f493das", "e12e53", "Junior Front-end Developer Lol Kek Cheburek", "Half time",
                "Nothing", 1000, "Kazakhstan", "Almaty", 1));
        return vacancies;
    }

    private ArrayList<Integer> generateDummyImages(int vacanciesSize) {
        ArrayList<Integer> dummyImages = new ArrayList<>();
        for (int i = 0; i < vacanciesSize; i++) {
            dummyImages.add(R.drawable.dummy_img_2);
        }
        return dummyImages;
    }


    @Override
    public void onJobClick(int position) {
        // TODO
    }
}
