package com.omdasoft.money4group;

import java.util.Date;

import com.omdasoft.money4group.db.RecordDAO;
import com.omdasoft.money4group.db.RecordModel;
import com.omdasoft.money4group.db.UserDAO;
import com.omdasoft.money4group.db.UserModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecordEditorActivity extends Activity {

	private EditText nameTxt;
	private EditText contactTxt;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user);
		
		nameTxt = (EditText) findViewById(R.id.userNameTxt);
		contactTxt = (EditText) findViewById(R.id.contactTxt);

		// set OK button
		Button saveBtn = (Button) findViewById(R.id.saveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				saveRecord();
				
			}
		});

		// set text of about content
		WebView about = (WebView) findViewById(R.id.about_content);
		about.loadUrl("file:///android_asset/help.html");
	}
	
	private void saveRecord(){
		RecordDAO userDba = RecordDAO.getInstance(this);
		
		//1.save it to db
		userDba.open();
		RecordModel um = new RecordModel();
		um.setUserName(nameTxt.getText().toString());
		um.setContact(contactTxt.getText().toString());
		um.setCreatedAt(new Date().getTime());
		um.setCreatedBy("me");
		um.setModifiedAt(new Date().getTime());
		um.setModifiedBy("me");
		userDba.insert(um);
		
		//2.notify by toast
		toastMsg(R.string.saveSucess);
		
		//3.back to main window
		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(RecordEditorActivity.this, Money4GroupActivity.class);
		startActivity(intent);
		RecordEditorActivity.this.finish();
	}
	
	public void toastMsg(int strId) {
		final String msg = getString(strId);
		
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}
