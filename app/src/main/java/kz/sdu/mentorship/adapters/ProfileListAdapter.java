package kz.sdu.mentorship.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kz.sdu.mentorship.R;

public class ProfileListAdapter extends BaseAdapter {
    private Context context;
    private String[] titles;
    private int[] images;
    private int[] colors;

    public ProfileListAdapter(Context context, String[] titles, int[] images, int[] colors) {
        this.context = context;
        this.titles = titles;
        this.images = images;
        this.colors = colors;
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
        View row = layoutInflater.inflate(R.layout.profile_list_item, parent, false);

        TextView label = (TextView) row.findViewById(R.id.title);
        label.setText(titles[position]);
        label.setTextColor(colors[position]);

        ImageView icon = (ImageView) row.findViewById(R.id.icon);
        icon.setImageResource(images[position]);

        return row;
    }
}
