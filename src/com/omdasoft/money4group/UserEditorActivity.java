package com.omdasoft.money4group;

import java.util.Date;

import net.yihabits.monitor.R;

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

public class UserEditorActivity extends Activity {

	private UserModel m_um = new UserModel();
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
				
				saveUser();
				
			}
		});

		Intent intent = getIntent();
		UserModel sm = (UserModel) intent.getSerializableExtra("UserModel");
		if (sm == null) {
			//new user
			nameTxt.setText("");
			
			contactTxt.setText("");
			
		}else{
			//modify server
			m_um = sm;
			
			nameTxt.setText(m_um.getUserName());
			
			contactTxt.setText(m_um.getContact());
		}
	}
	
	private void saveUser(){
		UserDAO userDba = UserDAO.getInstance(this);
		
		//1.save it to db
		userDba.open();
		
		long flag;
		if(this.m_sm.getId() != -1){
			//update
			flag = dba.update(m_sm);
			 dba.close();
		}else{
			//insert
			 flag = dba.insert(m_sm);
			 dba.close();
		}
		
		if(flag != -1){
			//successful message
			toastMsg(getString(R.string.saveSuccess));
			this.finish();
		}else{
			//failed message
			toastMsg(getString(R.string.saveFail));
		}
		m_um.setUserName(nameTxt.getText().toString());
		m_um.setContact(contactTxt.getText().toString());
		m_um.setCreatedAt(new Date().getTime());
		m_um.setCreatedBy("me");
		m_um.setModifiedAt(new Date().getTime());
		m_um.setModifiedBy("me");
		userDba.insert(m_um);
		
		//2.notify by toast
		toastMsg(R.string.saveSucess);
		
		//3.back to main window
		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(UserEditorActivity.this, Money4GroupActivity.class);
		startActivity(intent);
		UserEditorActivity.this.finish();
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
