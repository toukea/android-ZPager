package istat.android.pager.page;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class ScreenPage extends Fragment {
    protected String title = "";
    protected int icon = 0;
    protected Bundle savedInstanceState;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @Nullable
    @Override
    public  View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View getView() {
        View view = super.getView();
        if (view != null) {
            return view;
        }
        return rootView;
    }

    public final LayoutInflater getDefaultLayoutInflater() {
        return getLayoutInflater(getArguments());
    }

    public final String getTitle() {
        return title;
    }

    public final int getIcon() {
        return icon;
    }

    public final void setIcon(int icon) {
        this.icon = icon;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final void reload() {
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.detach(this);
        transaction.attach(this);
        transaction.commit();
    }

    public final boolean isReady() {
        return getView() != null;
    }

    public final boolean isViewCreated() {
        return getView() != null;
    }

    public final boolean isRestoring() {
        return savedInstanceState != null;
    }

    protected View setContentView(int layout, ViewGroup container) {
        rootView = (ViewGroup) getDefaultLayoutInflater().inflate(layout,
                container, false);
        return rootView;
    }

    protected View setContentView(int layout, LayoutInflater inflater,
                                  ViewGroup container) {
        rootView = (ViewGroup) inflater.inflate(layout, container, false);
        return rootView;
    }

    protected final View findViewById(int id) {
        View fragmentView = getView();
        if (fragmentView != null) {
            return fragmentView.findViewById(id);
        } else {
            return null;
        }
    }

    protected Fragment findFragmentById(int id) {
        return getChildFragmentManager().findFragmentById(id);
    }

    protected Fragment findFragmentByTag(String tag) {
        return getChildFragmentManager().findFragmentByTag(tag);
    }

    // ---------------------------------------------
    protected final void toast(String txt) {
        Toast.makeText(getActivity(), txt, Toast.LENGTH_SHORT).show();
    }

    protected final void toast2(String txt) {
        Toast.makeText(getActivity(), txt, Toast.LENGTH_LONG).show();
    }

    protected final void toast(int txt) {
        Toast.makeText(getActivity(), txt, Toast.LENGTH_SHORT).show();
    }

    protected final void toast2(int txt) {
        Toast.makeText(getActivity(), txt, Toast.LENGTH_LONG).show();
    }
}