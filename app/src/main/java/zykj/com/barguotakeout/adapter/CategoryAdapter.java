package zykj.com.barguotakeout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zykj.com.barguotakeout.R;

/**
 * Created by ss on 15-4-23.
 */
public class CategoryAdapter extends BaseAdapter{

    private String[] titles;
    private String[] resid;

    private LayoutInflater inflater;

    public CategoryAdapter(Context ctx,String[] titles,String[] resid) {
        inflater=LayoutInflater.from(ctx);
        this.titles=titles;
        this.resid=resid;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(R.layout.item_category,null);
        TextView title= (TextView) view.findViewById(R.id.item_title);
        title.setText(titles[position]);
        ImageView item_icon= (ImageView) view.findViewById(R.id.item_icon);
        if(resid !=null){
        if( resid.length > position){
            item_icon.setImageResource(Integer.valueOf(resid[position]));
        }else{
            item_icon.setVisibility(View.GONE);
        }}

        return view;
    }
}
