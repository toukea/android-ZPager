package istat.android.freedev.pagers.pages;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by istat on 26/10/16.
 */

public class PicturePage extends Page {
    private Object path;
    private ImageView imageView;

    public final static PicturePage newInstance(String path) {
        PicturePage page = new PicturePage();
        page.path = path;
        return page;
    }

    public final static PicturePage newInstance(Drawable drawable) {
        PicturePage page = new PicturePage();
        page.path = drawable;
        return page;
    }

    public final static PicturePage newInstance(int resource) {
        PicturePage page = new PicturePage();
        page.path = new Integer(resource);
        return page;
    }

    public final static PicturePage newInstance(Object pathDescription) {
        PicturePage page = new PicturePage();
        page.path = pathDescription;
        return page;
    }

    public final static PicturePage[] newArrayInstances(Object... pathDescriptions) {
        PicturePage[] pages = new PicturePage[pathDescriptions.length];
        for (int i = 0; i < pathDescriptions.length; i++) {
            pages[i] = newInstance(pathDescriptions[i]);
        }
        return pages;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup.LayoutParams layoutParam = container.getLayoutParams();
        imageView = new ImageView(getActivity());
        imageView.setLayoutParams(layoutParam);
        if (TextUtils.isDigitsOnly(path + "")) {
            imageView.setImageResource(Integer.valueOf(path + ""));
        } else if (path instanceof Drawable) {
            imageView.setImageDrawable((Drawable) path);
        } else if (path instanceof String) {
            getImageLoader().onLoadImage(imageView, String.valueOf(path));
        } else {

        }
        return imageView;
    }

    ImageLoader imageLoader;

    ImageLoader getImageLoader() {
        return imageLoader;
    }

    public PicturePage setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public static interface ImageLoader {
        public void onLoadImage(ImageView view, String imagePath);
    }

    public Object getPath() {
        return path;
    }

    public <T> T optPath() {
        return (T) path;
    }
}
