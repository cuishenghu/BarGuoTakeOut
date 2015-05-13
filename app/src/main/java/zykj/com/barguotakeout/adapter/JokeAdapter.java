package zykj.com.barguotakeout.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.model.JokeEntity;


/**
 * Created by ss on 15-4-17.
 */
public class JokeAdapter extends CommonAdapter {


    public JokeAdapter(Context context, List<JokeEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder;
        if(convertView== null){
            convertView = inflater.inflate(R.layout.joke_item, null);
            holder = new ViewHolder();
            holder.title= (TextView) convertView.findViewById(R.id.joke_tv_title);
            holder.content= (TextView) convertView.findViewById(R.id.joke_tv_content);
            holder.date= (TextView) convertView.findViewById(R.id.joke_tv_date);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        JokeEntity joke = (JokeEntity) list.get(position);
        if(joke.getContent()==null || !joke.getContent().equals("")){
        holder.content.setText(joke.getContent());}
        else {
            holder.content.setText("");
        }
       return  convertView;
    }

    static class ViewHolder{
        TextView title;
        TextView content;
        TextView date;
    }
}
