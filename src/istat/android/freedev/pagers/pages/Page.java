package istat.android.freedev.pagers.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class Page extends Fragment {
    protected String title = "";
    protected int icon = 0;
    protected Bundle savedInstanceState;
    private View rootView;
    private int layoutResource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null && layoutResource != 0) {
            rootView = inflater.inflate(layoutResource, container, false);
        }
        return rootView;
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

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public final void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
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
        return getView() != null && !isDetached();
    }

    public final boolean isViewCreated() {
        return getView() != null;
    }

    public final boolean isRestoring() {
        return savedInstanceState != null;
    }

    protected void setContentView(int layout) {
        this.layoutResource = layout;
    }

    protected final <T extends View> T findViewById(int id) {
        View fragmentView = getView();
        if (fragmentView != null) {
            return (T) fragmentView.findViewById(id);
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

    protected boolean existFragmentWithId(int id) {
        return getChildFragmentManager().findFragmentById(id) != null;
    }

    protected boolean existFragmentWithTag(String tag) {
        return getChildFragmentManager().findFragmentByTag(tag) != null;
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