# WSD Android

Small utilities, for lazy developers xD, to make easy the Android development.

## Features

* List Helpers
* GPS Helper
* Camera/Library Image Helper
* File downloader helper
* Reachability helper
* Date humanization helper
* Phone call helper
* Remote image loader with caching helper

## Instalation

Just download, add to your Eclipse as external library and incluye into your project

## List Helper

Add to your list Activity the list implementations

	public class MyActivity extends Activity implements WSDListImplementation
		@Override

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			setContentView(R.layout.activity_list);

			// Parameters
			//	- implementation, also you can do new WSDListImplementation(){ .... }
			//	- progressbar view, optional
			//	- load on create, this is just if you want to load on certain action
			adapter = new WSDBaseAdapter(this, findViewById(R.id.progressbar), true);
		
			ListView list = (ListView) findViewById(R.id.list);
			list.setAdapter(adapter);
		}

		// Layout of the list item

		@Override
		public int getItemLayout() {
			return R.layout.list_item;
		}

		// Just pass the activity context, this or getActivity() if is a fragment

		@Override
		public Context getContext() {
			return this;
		}

		// Thie method is fired when a list item is clicked

		@Override
		public void onClick(WSDListItem item, View view) {
			Bundle bundle = new Bundle();
			bundle.putString("shopId", item.mId);
			
			Intent intent = new Intent(this, ShopActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}

		// Return the data for the list

		@Override
		public void getData(final WSDBaseAdapter adapter) {
			List<WSDListItem> items = new ArrayList<WSDListItem>();
			
			Map<String, Object> data = new HashMap<String, Object>();
			// Iterates troght the data and set internally the fields with their respective value
			items.add(new WSDListItem(getContext(), "string_id", row));
			
			adapter.setItems(items);
		}

		// Return the fields to be used

		@Override
		public List<WSDListField> getFields() {
			List<WSDListField> fields = new ArrayList<WSDListField>();
			/**
			 * Field name, must be the same of the field/key of the data passed before
			 * Image resource Id, the image must be instance of com.applidium.shutterbug.FetchableImageView it you want to fetch image from url
			 * placeholder of the image
			 */
			fields.add(new WSDListField("icon", R.id.imgThumbnail, getResources().getDrawable(R.drawable.ic_launcher_generic)));
			/**
			 * Field name, must be the same of the field/key of the data passed before
			 * resource id, supported view types described on WSDListField.TYPE_*
			 * view type
			 */
			fields.add(new WSDListField("name", R.id.txtTitle, WSDListField.TYPE_TEXT));
			fields.add(new WSDListField("address", R.id.txtSubtitle, WSDListField.TYPE_TEXT));
			fields.add(new WSDListField("rating", R.id.ratingbar, WSDListField.TYPE_RATING));
			return fields;
		}

		// I you want to customize the List Adapter getView implement it and return the view, if null is returned the library use their own implementation

		@Override
		public View getView(int i, View view, ViewGroup viewGroup, WSDListItem item) {
			return null;
		}

## GPSTracker

Add those lines in the android manifest

	<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

Usage
	
	public void customGPSmethod() {
		GPSTracker gps = new GPSTracker(getContext(), new GPSTrackerImplementation() {
					
			@Override
			public void onLocationChange(GPSTracker tracker, Location location) {
				// This will remove the gps liteners
				tracker.stopUsingGPS();
				// Log location
				Log.i("location", location.getLatitude() + ", " + location.getLongitude());
			}
		});
		
		// If gps isn't enabled show dialog
		if (!gps.canGetLocation()) gps.showSettingsAlert();
	}

## TODO

* Update documentation with examples of each helper
* Add Parse.com support (maybe as other separate library integrated with this one)
* Distribute as .jar/.aar file or via maven

## Changelog

**0.3.1**
- Some Enhancements
- Added backgroundReload to List adapter

**0.3**
- Updated Readme
- Removed gradle implementation because the Android Studio was too inmature to develop in it
- Added Eclipse project
- Added List implementation
- Added GPS utility
- Added Camera/Library Image Helper
- Added File downloader
- Added Reachability utility
- Added Date humanization utility
- Added Dialog utility
- Added Call utility
- Added remote image loader with caching (Thanks to [Shutterbug](https://github.com/applidium/Shutterbug))

**0.1**
Initial Commit

## The MIT License

Copyright (c) 2013 Norman Paniagua (norman at wsdcamp dot com)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.