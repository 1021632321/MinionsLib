package lib.minions.com.minionslib.text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import lib.minions.com.minionslib.R;
import lib.minions.com.minionslib.util.dialog.DialogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view){
        DialogUtil.creatKickDialog(this,"",MainActivity.class);
    }

}
