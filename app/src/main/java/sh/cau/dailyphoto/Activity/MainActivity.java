package sh.cau.dailyphoto.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sh.cau.dailyphoto.Fragment.MainGalleryFragment;
import sh.cau.dailyphoto.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton[] imgButtons;
    private Spinner dropdownSpinner;

    private Uri fileUri;

    private final int TAKE_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize
        initRefToolbar();
        initFragmentHolder();
        initRefImageButtons();
        initRefDropDownSpinner();

        // attachActions
        setActionOnBtnCamera();
        setActionOnDropDownSpinner();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode == RESULT_OK && requestCode == TAKE_CAMERA ){
            File tempImg = new File(fileUri.getPath());

            if(!tempImg.exists()){ // file이 존재하지 않으면
                try {
                    FileOutputStream fos = new FileOutputStream(new File(fileUri.getPath())); // FileOutputStream 변수를 선언한 후
                    try {
                        fos.write(data.getExtras().getByte("data")); // Intent의 data 값을 write 하여 저장
                        fos.close(); // 그러고 나서 FileOutputStream을 종료한다.
                    } catch(IOException e){
                        Log.d("IOE", "IOException occur : " + e.getMessage());
                    }
                } catch (FileNotFoundException e){
                    Log.d("FNE" , "The image doesn't exist : "+ tempImg.toString());
                }
            }

            Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath()); // 해당 file을 Bitmap으로 decode 하여 저장한 후

            ((MainGalleryFragment)getSupportFragmentManager().getFragments().get(0)).getCustomImageAdapter().notifyDataSetChanged();
        }
    }

    private void initRefToolbar(){
        this.toolbar = (Toolbar)findViewById(R.id.toolbar_top_menu);
        this.setSupportActionBar(toolbar);
    }
    private void initFragmentHolder(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHolder, new MainGalleryFragment())
                .commit();
    }
    private void initRefImageButtons(){
        imgButtons = new ImageButton[4];
        imgButtons[0] = (ImageButton)findViewById(R.id.btnCamera);
        imgButtons[1] = (ImageButton)findViewById(R.id.btnCinema);
        imgButtons[2] = (ImageButton)findViewById(R.id.btnClock);
        imgButtons[3] = (ImageButton)findViewById(R.id.btnGrid);
    }
    private void initRefDropDownSpinner(){
        this.dropdownSpinner = (Spinner)findViewById(R.id.spinner_btn_grid);
    }

    private void setActionOnBtnCamera(){

        imgButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri();
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(i, TAKE_CAMERA);
            }
        });
    }
    private void setActionOnDropDownSpinner(){

        String[] items = {"SNS에 공유", "설정"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdownSpinner.setAdapter(adapter);

        imgButtons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropdownSpinner.performClick();
            }
        });
    }

    private Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
    }
    private File getOutputMediaFile(){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "DailyPhoto");

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){ // 해당 directory가 아직 생성되지 않았을 경우 mkdirs(). 즉 directory를 생성한다.
            if (! mediaStorageDir.mkdirs()){ // 만약 mkdirs()가 제대로 동작하지 않을 경우, 오류 Log를 출력한 뒤, 해당 method 종료
                Log.d("DailyPhoto", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

}