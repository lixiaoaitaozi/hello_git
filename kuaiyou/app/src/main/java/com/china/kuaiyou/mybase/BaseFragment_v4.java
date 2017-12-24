package com.china.kuaiyou.mybase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment_v4 extends Fragment {
    public View view;
    public String tag = this.getClass().toString() + ">>>";

    public BaseFragment_v4() {
        LG.d(getClass().toString(), "---------开始构造");
    }

    private MyBaseFragmentImp myBaseFragmentImp;
    private boolean isShow = false;
    private boolean isCreateView = false;

    public void setMyBaseFragmentImp(MyBaseFragmentImp myBaseFragmentImp) {
        this.myBaseFragmentImp = myBaseFragmentImp;

    }


    @Override

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (myBaseFragmentImp != null) {
            if (isVisibleToUser) {
                LG.d(tag, "------viewShow()");
                if (!isShow&&isCreateView) {
                    isShow = true;
                    myBaseFragmentImp.viewShow();
                }

            } else {
                LG.d(tag.toString(), "------viewHide()");
                isShow = false;
                myBaseFragmentImp.viewHide();
            }
        }

    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (!isShow) {
            isShow = true;
            if (myBaseFragmentImp != null)
                myBaseFragmentImp.viewShow();
        }else{
            isCreateView=true;
            LG.d(tag.toString(), "------onCreateView(()");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        LG.d(tag, "------onResume()");
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        if (myBaseFragmentImp != null) {
            myBaseFragmentImp.over();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        LG.d(tag, "------onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
    }

    public void toActivity(Class toclass) {
        startActivity(new Intent(getActivity(), toclass));
    }

    public void toActivity(Class toclass, String Gsonstring) {
        startActivity(new Intent(getActivity(), toclass).putExtra("tb",
                Gsonstring));
    }

    public void showToast(String str) {
        ToastUtil.showToast(str);
    }

    public String getIntentString() {
        return getActivity().getIntent().getStringExtra("tb");
    }


}
