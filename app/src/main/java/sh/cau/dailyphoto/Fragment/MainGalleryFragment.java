package sh.cau.dailyphoto.Fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;

import sh.cau.dailyphoto.Adapter.CustomImageAdapter;
import sh.cau.dailyphoto.R;

/**
 * Created by SH on 2016-06-27.
 */
public class MainGalleryFragment extends Fragment {

    private String dirPath;
    private GridView gridView;
    private CustomImageAdapter customImageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "DailyPhoto");

        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                Log.d("DailyPhoto", "failed to create dic");
            }
        }

        this.dirPath = mediaStorageDir.getPath();
        this.gridView = (GridView)rootView.findViewById(R.id.gridview);
        this.customImageAdapter = new CustomImageAdapter(getActivity().getApplicationContext(), dirPath);

        this.gridView.setAdapter(customImageAdapter);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),
                        customImageAdapter.getItemPath(i), Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    public CustomImageAdapter getCustomImageAdapter(){
        return this.customImageAdapter;
    }

}
