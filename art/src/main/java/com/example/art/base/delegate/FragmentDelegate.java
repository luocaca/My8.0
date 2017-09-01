package com.example.art.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public interface FragmentDelegate extends Parcelable {

    String FRAGMENT_DELEGATE = "fragment_delegate";


    void onAttach(Context context);

    void onCreate(Bundle saveInstanceState);

    void onCreateView(View view, Bundle saveInstanceState);

    void onActivityCreate(Bundle saveInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroyView();

    void onDestroy();

    void onDetach();


    /**
     * return true if the framgent is currently added to its activity
     */
    boolean isAdded();


}
