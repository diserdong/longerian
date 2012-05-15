package me.longerian.radiogroup;

import me.longerian.radiogroup.RadioGroup.OnCheckedChangeListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

public class RelativeRadioGroupActivity extends Activity {
	
	private RadioGroup feedback;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        feedback = (RadioGroup) findViewById(R.id.feedback_radio_group);
        feedback.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId != -1) {
					RadioButton rb = (RadioButton) group.findViewById(checkedId);
					Toast.makeText(getApplicationContext(), (String) rb.getTag(), Toast.LENGTH_SHORT).show();
				}
			}
		});
    }
}