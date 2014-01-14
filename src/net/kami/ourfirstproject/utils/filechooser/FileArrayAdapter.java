package net.kami.ourfirstproject.utils.filechooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.kami.ourfirstproject.R;

import java.util.List;

/**
 * Created by Michael on 09.01.14.
 */
public class FileArrayAdapter extends ArrayAdapter<Option> {

    private Context c;
    private int id;
    private List<Option> items;

    public FileArrayAdapter(Context context, int textViewResourceId,
                            List<Option> objects) {
        super(context, textViewResourceId, objects);
        c = context;
        id = textViewResourceId;
        items = objects;
    }

    public Option getItem(int i) {
        return items.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(id, null);
        }
        final Option o = items.get(position);
        if (o != null) {
            ImageView im = (ImageView) v.findViewById(R.id.img1);
            TextView t1 = (TextView) v.findViewById(R.id.TextView01);
            TextView t2 = (TextView) v.findViewById(R.id.TextView02);

            if (o.getData().equalsIgnoreCase("folder")) {
                im.setImageResource(R.drawable.folder);
            } else if (o.getData().equalsIgnoreCase("parent directory")) {
                im.setImageResource(R.drawable.back32);
            } else {
                String name = o.getName().toLowerCase();
                if (name.startsWith("fuellist") || name.endsWith(".xml")) {
                    im.setImageResource(R.drawable.fuelfile32);
                } else {
                    im.setImageResource(R.drawable.whitepage32);
                }
            }

            if (t1 != null)
                t1.setText(o.getName());
            if (t2 != null)
                t2.setText(o.getData());

        }
        return v;
    }

}