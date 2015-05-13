package zykj.com.barguotakeout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ss on 15-4-18.
 */
public abstract class CommonAdapter<T> extends BaseAdapter{

    public  List<T> list;
    public  LayoutInflater inflater;

    public CommonAdapter(Context context,List<T> list){
        this.list=list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
           if(list !=null && list.size() > 0)
               return list.size();
        return 0;
    }

    @Override
    public T getItem(int position) {
        if(list != null && list.size() >0 )
            return list.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}
