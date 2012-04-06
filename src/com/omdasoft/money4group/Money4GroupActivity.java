package com.omdasoft.money4group;

import java.io.File;
import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.omdasoft.money4group.db.ActivityDAO;
import com.omdasoft.money4group.db.ActivityModel;
import com.omdasoft.money4group.db.Money4GroupDBOpenHelper;
import com.omdasoft.money4group.db.RecordDAO;
import com.omdasoft.money4group.db.RecordModel;
import com.omdasoft.money4group.db.UserDAO;
import com.omdasoft.money4group.db.UserModel;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Money4GroupActivity extends ListActivity {
	
	private int mode = 1; // 1 - activity list ; 2 - records list ; 3 - balance list ; 
	
	private ActivityModel currentActivity; // initiated in the onstart method
	
	private ActivityDAO actDba;
	private ArrayList<ActivityModel> actList;
	
	private RecordDAO recDba;
	private ArrayList<RecordModel> recList;
	
	private UserDAO userDba;
	private ArrayList<UserModel> userList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if(actList == null){
        	actList = new ArrayList<ActivityModel>();
        }
        if(recList == null){
        	recList = new ArrayList<RecordModel>();
        }
        if(userList == null){
        	userList = new ArrayList<UserModel>();
        }
        
        initBtn();
        
        actDba = ActivityDAO.getInstance(this);
        recDba = RecordDAO.getInstance(this);
        userDba = UserDAO.getInstance(this);
        
     // ad initialization
     		// Create the adView
     		AdView adView = new AdView(this, AdSize.BANNER, "a14e9fe338c5fc7");
     		// Lookup your LinearLayout assuming its been given
     		// the attribute android:id="@+id/mainLayout"
     		LinearLayout layout = (LinearLayout) findViewById(R.id.ad_layout);
     		// Add the adView to it
     		layout.addView(adView);
     		// Initiate a generic request to load it with an ad
     		// if(!AboutActivity.paid){
     		adView.loadAd(new AdRequest());
     		// }
    }
    
    private void initBtn() {
		final Button addActBtn = (Button) findViewById(R.id.addActBtn);

		final Button addUserBtn = (Button) findViewById(R.id.addUserBtn);

		final Button setBtn = (Button) findViewById(R.id.settingBtn);


		addActBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});

		addUserBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});

		setBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//TODO:TBD
			}
		});


	}

    @Override
	protected void onStart() {
		super.onStart();
		
		refreshActList();
		
		if(actList.size() > 0){
			//set the current activity
			currentActivity = actList.get(0);
		}else{
			//default, no possible go here.
		}

		// initialize the record type spinner
		initSpinner();

		getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);

		// getListView().setFocusable(true);
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	private void initSpinner() {

		// sites list
		final Spinner actSpinner = (Spinner) findViewById(R.id.recordTypeSpinner);
		ArrayAdapter<CharSequence> mAdapter2 = ArrayAdapter.createFromResource(this, R.array.recordType, R.layout.spinner_layout);
		actSpinner.setAdapter(mAdapter2);
		actSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {
						
						

					}

					public void onNothingSelected(AdapterView<?> adapterView) {

						return;
					}
				});
	}

	@Override
	protected void onListItemClick(final ListView list, View v,
			final int position, long id) {
		// super.onListItemClick(list, v, position, id);

		// for the last row
		if (position == postList.size()) {
			addMore();

		} else {

			selected_item = position;

			refreshList(-1);

			// show waiting dialog
			final ProgressDialog dialog = ProgressDialog.show(this,
					getString(R.string.waitPostTitle),
					getString(R.string.waitPost), true);

			Runnable saveUrl = new Runnable() {

				public void run() {

					// the post url
					String postUrl = postList.get(selected_item).getUrl();

					dba.open();

					// 1.try to get the post from db
					String location = "";
					String urls = "";
					Cursor c = null;
					try {
						c = dba.getPostByUrl(postUrl);
						if (c.moveToFirst()) {
							// 2.1 get image locations of the post
							location = c.getString(c
									.getColumnIndex(PostDBOpenHelper.LOCATION));
							urls = c.getString(c
									.getColumnIndex(PostDBOpenHelper.IMG_URL));

						}
						if (location == null || urls == null
								|| "".equals(location) || "".equals(urls)) {
							// 2.2.1 get image locations from the web
							urls = util.getPostImgUrl(postUrl);
							location = util.convertUrl2Location(postUrl, urls);

							// 2.2.2 update the list
							postList.get(selected_item).setImgUrl(urls);
							postList.get(selected_item).setLocation(location);
							dba.update(postList.get(selected_item));
						}
					} finally {
						c.close();
						dba.close();
					}

					// 3.set up the list of image locations
					imgLocationList = new ArrayList<String>();
					for (String url : location.split(",")) {
						if (!"".equals(url)) {
							imgLocationList.add(url);
						}
					}

					// 4.1 show all of images of the post page
					if (imgLocationList.size() > 0 && !"".equals(location)) {

						imageSequence = 0;

						displayViewPanel(true);

						// 5.asynchronous download all of images if needed
						util.runSaveUrl(postUrl, urls);

						// notify the activity refresh imageswitcher
						if (isGrid) {
							refreshGrid();
						} else {
							refreshImageSwitcher();
						}

					} else {
						// 4.2 no image to show and notify user
						toastMsg(R.string.noImages);
					}

					dialog.dismiss();
				}
			};
			new Thread(saveUrl).start();
		}
	}

	public void toastMsg(int resId, String... args) {
		final String msg = this.getString(resId, args);
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// hide detailspanel

		switch (item.getItemId()) {
		case R.id.menu_home: {
			//set mode
			mode = 1;

			resetList();
			
			return true;
		}
		case R.id.menu_favorite: {
			return true;
		}
		case R.id.menu_help:
			// popup the about window
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setClass(PostPictureReaderActivity.this, AboutActivity.class);
			startActivity(intent);
			return true;
		}
		// change the title
		String suffix = (PostPictureReaderActivity.this.getResources()
				.getStringArray(R.array.source))[mode - 1];
		suffix += " - "
				+ ((source == 0) ? getString(R.string.web)
						: getString(R.string.local));
		setTitle(getString(R.string.app_name_suffix, suffix));

		return super.onMenuItemSelected(featureId, item);
	}

	private void resetList(){
		switch(mode){
		case 1:{
			break;
		}
		case 2:{
			break;
		}
		case 3:{
			break;
		}
		default:{
			;
		}
		}
		
		// set list adapter
					PostAdapter adapter = new PostAdapter(
							PostPictureReaderActivity.this);

					setListAdapter(adapter);
	}
	
	private void refreshActList(){
		actDba.open();

		Cursor c = actDba.getAllActivity();
		startManagingCursor(c);
		if (c.moveToFirst()) {
			do {
				long id = c.getLong(0);
				String name = c.getString(c
						.getColumnIndex(Money4GroupDBOpenHelper.ACT_NAME));
				int actType = c.getInt(c
						.getColumnIndex(Money4GroupDBOpenHelper.ACT_TYPE));
				long startAt = c.getLong(c
						.getColumnIndex(Money4GroupDBOpenHelper.START_AT));
				long endAt = c.getLong(c
						.getColumnIndex(Money4GroupDBOpenHelper.END_AT));
				String createdBy = c.getString(c
						.getColumnIndex(Money4GroupDBOpenHelper.CREATED_BY));
				long createdAt = c.getLong(c
						.getColumnIndex(Money4GroupDBOpenHelper.CREATED_AT));
				String modifiedBy = c.getString(c
						.getColumnIndex(Money4GroupDBOpenHelper.MODIFIED_BY));
				long modifiedAt = c.getLong(c
						.getColumnIndex(Money4GroupDBOpenHelper.MODIFIED_AT));

				ActivityModel temp = new ActivityModel();
				temp.setId(id);
				temp.setActName(name);
				temp.setActType(actType);
				temp.setStartAt(startAt);
				temp.setEndAt(endAt);
				temp.setCreatedAt(createdAt);
				temp.setCreastedBy(createdBy);
				temp.setModifiedAt(modifiedAt);
				temp.setModifiedBy(modifiedBy);

				actList.add(temp);
			} while (c.moveToNext());
		}

		c.close();

		actDba.close();

	}
	
	private String[] getActStrList(){
		String[] res = new String[actList.size()];
		
		int i = 0;
		for(ActivityModel am : actList){
			res[i] = am.getActName();
			i++;
		}
		
		return res;
	}
	
	private 
	
	private class PostAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public PostAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return postList.size() + 1;
		}

		public PostModel getItem(int i) {
			return postList.get(i);
		}

		public long getItemId(int i) {
			return i;
		}

		public View getView(final int position, View convertView, ViewGroup vg) {
			if (postList == null || position < 0 || position > postList.size())
				return null;

			final View row;

			// the last row is the more... row
			if (position == postList.size()) {
				// hide two buttons
				// holder.optBtn.setVisibility(View.GONE);
				// holder.playBtn.setVisibility(View.GONE);

				row = mInflater.inflate(R.layout.more_list_item, null);

				ViewHolder holder = (ViewHolder) row.getTag();
				if (holder == null) {
					holder = new ViewHolder(row);
					row.setTag(holder);
				}

				// set size of the row
				// row.setLayoutParams(new
				// ListView.LayoutParams(LayoutParams.FILL_PARENT, 120));

				// set the title
				holder.title.setText(R.string.more);

				// set center
				// holder.title.setGravity(Gravity.TOP+Gravity.CENTER_HORIZONTAL);

			} else {
				row = mInflater.inflate(R.layout.list_item, null);

				ViewHolder holder = (ViewHolder) row.getTag();
				if (holder == null) {
					holder = new ViewHolder(row);
					row.setTag(holder);
				}

				// other normal row
				final PostModel rm = postList.get(position);

				// set name to label
				if (!locale.startsWith("zh")) {
					holder.title.setText(position + 1 + "." + rm.getNameEn());
				} else {
					holder.title.setText(position + 1 + "." + rm.getName());
				}

				// hide the icon
				if (position == selected_item) {
					holder.icon.setVisibility(View.VISIBLE);
				} else {
					holder.icon.setVisibility(View.GONE);
				}

			}

			return (row);
		}

	}

	public boolean updateOrInsertRingtone(final PostModel rm) {
		boolean flag = false;
		// update or insert db
		dba.open();
		flag = dba.updateOrInsert(rm);

		dba.close();

		return flag;
	}
}