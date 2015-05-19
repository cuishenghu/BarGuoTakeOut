package zykj.com.barguotakeout.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import info.hoang8f.android.segmented.SegmentedGroup;
import zykj.com.barguotakeout.R;

/**
 * Created by ss on 15-5-5.
 */
public class BgBangFragment extends CommonFragment implements RadioGroup.OnCheckedChangeListener{

    private static final String TAG=BgBangFragment.class.getSimpleName();
    private RadioButton button1;
    private RadioButton button2;

    private Integer currentItem=0;
    private RankList rankList;
    private RankList rankList2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTAG(BgBangFragment.class.getSimpleName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_baguobang,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initFragment();
    }

    private void initFragment() {
        rankList = new RankList();
        rankList2 = new RankList();
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frag_layout,rankList)
                .add(R.id.frag_layout,rankList2)
                .show(rankList)
                .hide(rankList2).commit();
        currentItem=0;
    }

    private void initView() {
        SegmentedGroup group= (SegmentedGroup) getView().findViewById(R.id.radios);
        group.setTintColor(getResources().getColor(R.color.white),getResources().getColor(R.color.top_color));
        group.setOnCheckedChangeListener(this);

        button1 = (RadioButton) getView().findViewById(R.id.radio_btn1);
        button2 = (RadioButton) getView().findViewById(R.id.radio_btn2);

        button1.setChecked(true);//设置选中


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId){
            case R.id.radio_btn1:
                if(currentItem == 0){
                    return;
                }
                getActivity().getSupportFragmentManager().beginTransaction()
                        .show(rankList).commit();
                currentItem=0;
                break;
            case R.id.radio_btn2:
                if(currentItem == 1){
                    return;
                }
                getActivity().getSupportFragmentManager().beginTransaction()
                        .show(rankList2)
                        .commit();
                currentItem=1;
                break;
        }
    }
}
