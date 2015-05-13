package zykj.com.barguotakeout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ss on 15-4-21. 地址adapter
 */
public class AddressAdapter extends BaseAdapter{

    private  List list;

    private LayoutInflater inflater;

    public AddressAdapter(Context ctx,List list) {
        this.list=list;
        this.inflater=LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){

            }

        return null;
    }
}
