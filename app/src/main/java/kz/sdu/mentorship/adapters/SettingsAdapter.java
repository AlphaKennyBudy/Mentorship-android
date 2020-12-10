package kz.sdu.mentorship.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kz.sdu.mentorship.R;

public class SettingsAdapter extends BaseAdapter {
    private Context context;
    private String[] titles;
    private int[] icons;

    public SettingsAdapter(Context context, String[] titles, int[] icons) {
        this.context = context;
        this.titles = titles;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View row = layoutInflater.inflate(R.layout.settings_list_item, parent, false);

        TextView title = row.findViewById(R.id.title);
        title.setText(titles[position]);

        ImageView icon = row.findViewById(R.id.icon);
        if (icons[position] != -1) {
            icon.setImageResource(icons[position]);
        }

        return row;
    }
}
