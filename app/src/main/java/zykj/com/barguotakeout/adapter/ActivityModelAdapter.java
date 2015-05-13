package zykj.com.barguotakeout.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.model.ActivitiesModel;

/**
 * Created by ss on 15-4-23.
 */
public class ActivityModelAdapter extends CommonAdapter {

    public ActivityModelAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ActivitiesModel model= (ActivitiesModel) list.get(position);
        View view=inflater.inflate(R.layout.item_category,null);
        TextView title= (TextView) view.findViewById(R.id.item_title);
        title.setText(model.getName());
        return view;
    }
}
