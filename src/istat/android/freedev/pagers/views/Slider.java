package istat.android.freedev.pagers.views;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import istat.android.freedev.pagers.interfaces.SlideAble;

/**
 * Created by istat on 27/10/16.
 */

public final class Slider {
    FragmentManager fragmentManager;
    final List<Object> slidePath = new ArrayList<Object>();
    SlideAble slideAble;

    Slider(FragmentManager fragmentManager, SlideAble slideAble) {
        this.fragmentManager = fragmentManager;
        this.slideAble = slideAble;
    }

    public Slider addSlideRessource(int ressource) {
        return this;
    }

    public Slider addSlideDrawable(Drawable drawable) {

        return this;
    }

    public Slider addSlideUri(Uri... uris) {
        for (Uri uri : uris) {
            slidePath.add(uri.getPath());
        }
        return this;
    }

    public Slider addSlidePath(String... url_or_paths) {
        for (String path : url_or_paths) {
            path = path.startsWith("/") ? path : "/" + path;
            slidePath.add("file://" + path);
        }
        return this;
    }

    public void start() {
        slideAble.startSliding(this.fragmentManager, slideAble.getCurrentItem());
    }

    public void stop() {
        slideAble.stopSliding();
    }

    public void slideTo(int index) {
        slideAble.startSliding(this.fragmentManager, index);
    }

    void begin() {

    }

    void end() {
        
    }
}
