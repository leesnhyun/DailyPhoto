package sh.cau.dailyphoto.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;

import sh.cau.dailyphoto.R;

/**
 * Created by SH on 2016-06-27.
 */
public class CustomImageAdapter extends BaseAdapter {

    private String dirPath;
    private String[] images;
    private Context ctx;
    private Bitmap bitmap;
    private DataSetObservable dataSetObservable = new DataSetObservable();

    private final String TAG = "Gallery Adapter";

    public CustomImageAdapter(Context ctx, String dirPath){
        this.ctx = ctx;
        this.dirPath = dirPath;

        File file = new File(dirPath);
        if(!file.exists()){
            if(!file.mkdirs()){
                Log.d(TAG, "failed to create directory");
            }
        }

        images = file.list();
    }

    @Override
    public int getCount() {
        File dir = new File(dirPath);
        images = dir.list();

        return images.length;
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    public String getItemPath(int pos){
        return dirPath + File.separator + images[pos];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView img = view == null ? new ImageView(ctx) : (ImageView) view;

        File dir = new File(dirPath);
        images = dir.list();
        bitmap = BitmapFactory.decodeFile(dirPath + File.separator + images[i]);

        Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
        img.setImageBitmap(thumbnail);

//        if( bitmap != null && !bitmap.isRecycled() ) bitmap.recycle();

        return img;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        dataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        dataSetObservable.unregisterObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        dataSetObservable.notifyChanged();
    }
}
