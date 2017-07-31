package lib.minions.com.minionslib.util.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import lib.minions.com.minionslib.R;
import lib.minions.com.minionslib.app.BaseApplication;
import lib.minions.com.minionslib.util.MyUtils;

/**
 * 这是一个 Activity  当做 Dialog使用
 */
public class KickDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getI().addActivity(this);
        setContentView(R.layout.activity_kick);
        final String activity = getIntent().getStringExtra("activity");
        final String info = getIntent().getStringExtra("content");
        findViewById(R.id.btn_Confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.getI().exit();
                Intent intent = new Intent();
                intent.setClassName(KickDialog.this,activity);
                startActivity(intent);
            }
        });
        TextView title = (TextView) findViewById(R.id.dialog_title);
        if (!MyUtils.isEmptyString(info))title.setText(info);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getI().removeActivity(this);
    }
}
