package sh.cau.dailyphoto.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import sh.cau.dailyphoto.R;

/**
 * Created by SH on 2016-06-27.
 */
public class AlarmActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        initRefToolbar();
    }

    private void initRefToolbar() {
        this.toolbar = (Toolbar)findViewById(R.id.toolbar_sub_menu);
        setSupportActionBar(toolbar);
        if( getSupportActionBar() != null ) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setTitle("알람설정");
            this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

}
