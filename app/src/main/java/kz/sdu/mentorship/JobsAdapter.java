package kz.sdu.mentorship;

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

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobViewHolder> {
    int layoutId;
    ArrayList<Integer> images;
    List<Vacancy> vacancies;

    public JobsAdapter(int layoutId, ArrayList<Integer> images, List<Vacancy> vacancies) {
        this.layoutId = layoutId;
        this.images = images;
        this.vacancies = vacancies;
    }

    @NonNull
    @Override
    public JobsAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);

        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return vacancies.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView jobNameView;
        TextView dutyTypeView;
        TextView salaryView;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.card_image);
            jobNameView = itemView.findViewById(R.id.job_name);
            dutyTypeView = itemView.findViewById(R.id.duty_type);
            salaryView = itemView.findViewById(R.id.job_salary);
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
}
