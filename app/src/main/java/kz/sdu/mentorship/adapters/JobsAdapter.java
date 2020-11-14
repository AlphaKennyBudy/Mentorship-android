package kz.sdu.mentorship.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kz.sdu.mentorship.R;
import kz.sdu.mentorship.models.Vacancy;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobViewHolder> {
    int layoutId;
    ArrayList<Integer> images;
    List<Vacancy> vacancies;
    OnJobListener onJobListener;

    public JobsAdapter(int layoutId, ArrayList<Integer> images, List<Vacancy> vacancies, OnJobListener onJobListener) {
        this.layoutId = layoutId;
        this.images = images;
        this.vacancies = vacancies;
        this.onJobListener = onJobListener;
    }

    @NonNull
    @Override
    public JobsAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);

        return new JobViewHolder(view, onJobListener);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return vacancies.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView jobNameView;
        TextView dutyTypeView;
        TextView salaryView;
        OnJobListener onJobListener;

        public JobViewHolder(@NonNull View itemView, OnJobListener onJobListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.card_image);
            jobNameView = itemView.findViewById(R.id.job_name);
            dutyTypeView = itemView.findViewById(R.id.duty_type);
            salaryView = itemView.findViewById(R.id.job_salary);
            this.onJobListener = onJobListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onJobListener.onJobClick(getAdapterPosition());
        }

        void bind(int position) {
            imageView.setImageResource(images.get(position));

            Vacancy vacancy = vacancies.get(position);
            jobNameView.setText(vacancy.getJobName());
            dutyTypeView.setText(vacancy.getDutyType());

            @SuppressLint("DefaultLocale")
            String formattedPriceText = String.format("$%d/h", vacancy.getSalary());
            salaryView.setText(formattedPriceText);
        }


    }

    public interface OnJobListener {
        void onJobClick(int position);
    }
}
