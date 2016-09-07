package com.example.santa.wunderlist;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by santa on 16/7/18.
 */
public class AddTipFragment extends android.app.Fragment {
    private InputMethodManager imm;
    private EditText editText;
    private ClickListener mListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addtip, container, false);
        TextView textView = (TextView) view.findViewById(R.id.addtip_group);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener) {
                    mListener.onClick(AddTipFragment.this);
                }
            }
        });

        textView.setText(((AddTipActivity)getActivity()).getCurGroup());
        editText = (EditText) view.findViewById(R.id.addtip_edit);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        initTimeBar(view);

        return view;
    }


    private void initTimeBar(View view) {
        PopuImageView.PopuListener listener = new PopuImageView.PopuListener() {
            @Override
            public void onDismiss() {
                softInputShow();
            }

            @Override
            public void onShow() {
                softInputHide();
            }
        };
        PopuImageView date = (PopuImageView) view.findViewById(R.id.addtip_date);
        date.setPopupListener(listener);


        PopuImageView clock = (PopuImageView) view.findViewById(R.id.addtip_clock);
        clock.setPopupListener(listener);
    }



    public void setListener(ClickListener listener) {
        mListener = listener;
    }

    public interface ClickListener{
        void onClick(android.app.Fragment fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        softInputShow();
    }

    private void softInputShow() {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    private void softInputHide() {
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        softInputHide();
    }



}
