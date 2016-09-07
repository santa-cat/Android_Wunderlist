package com.example.santa.wunderlist;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by santa on 16/7/18.
 */
public class GroupSelectFragment extends Fragment {
    private ListView listView;
    private ClickItemListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groupselect, container, false);
        listView = (ListView) view.findViewById(R.id.group_select);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, ((AddTipActivity)getActivity()).getGroupData()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(getResources().getColor(R.color.midGrary));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimensionPixelSize(R.dimen.textsize_normal));
                return textView;
            }
        });
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setDividerHeight(0);
        listView.setItemChecked(((AddTipActivity)getActivity()).getCurSelected(), true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != mListener) {
                    mListener.selectChange(position);
                    getFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }


    public void setListener (ClickItemListener listener) {
        mListener = listener;
    }

    public interface ClickItemListener{
        void selectChange(int position);
    }




}
