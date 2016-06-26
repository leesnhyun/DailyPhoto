package sh.cau.dailyphoto.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import sh.cau.dailyphoto.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize
        initRefToolbar();

    }

    private void initRefToolbar(){
        this.toolbar = (Toolbar)findViewById(R.id.toolbar_top_menu);
        this.setSupportActionBar(toolbar);
    }

}
