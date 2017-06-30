package com.mmks.sgbusstops.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.beans.BusService;
import com.mmks.sgbusstops.beans.BusStop;

public class BusServiceActivity extends Activity {

	BusService service;
	BusStop busStop;
	String SMRTURL = "http://www.smrt.com.sg/eBusGuideWebService.aspx?CallType=details&BusNo=";
	String SBSURL = "http://www.sbstransit.com.sg/journeyplan/servicedetails.aspx?serviceno=";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_service);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			service = (BusService) extras.getSerializable("busService");
			busStop = (BusStop) extras.getSerializable("busStop");
			TextView tw = (TextView) findViewById(R.id.service_info);
			WebView webInfo = (WebView) findViewById(R.id.servicewebdetail);
			webInfo.getSettings().setBuiltInZoomControls(true);
			
			webInfo.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {
					setTitle(R.string.loading);
					setProgress(progress * 100);
					if (progress == 100){
						setTitle(R.string.bus_service_details);
					}
				}
			});
			
			try {
				if (service.getOperater().contains("smrt")) {
					String callback = "" + Math.random();
					String loadData = "<html><body><span>Loading...</span></body></html>";
					webInfo.loadData(loadData, "text/html","UTF-8");
					new NetworkTask().execute(SMRTURL + service.getServiceNo() + "&callback=" + callback, callback);
				} else {
					webInfo.loadUrl(SBSURL + service.getServiceNo());
				}
			} catch (Exception e) {				
				//e.printStackTrace();
			}
			tw.setText(service.toString());
		}
	}
	
	public void showBusRouteMap(View v) {
		Intent i = new Intent(BusServiceActivity.this, BusRouteMapActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("busService", service);
		b.putSerializable("busStop", busStop);
		i.putExtras(b);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus, menu);
		return true;
	}
	
	
	
	private class NetworkTask extends AsyncTask<String, Void, String> {

		private String callback;

		@Override
		protected String doInBackground(String... params) {
			String link = params[0];
			callback = params[1];
			HttpGet request = new HttpGet(link);	
			
			try {
				return downloadUrl(link);
			} catch (IOException e) {
				e.printStackTrace();
				 return null;
			}
		
		}

		@Override
		protected void onPostExecute(String response) {
			try {
				if (response != null) {
					WebView webInfo = (WebView) findViewById(R.id.servicewebdetail);			
					
					String f1 = "";
					String l1 = "";
					String f2 = "";
					String l2 = "";
					String f3 = "";
					String l3 = "";
					
					String fromF1 = "";
					String fromF2 = "";
					String fromF3 = "";
					String fromF4 = "";
					
					String tof1 = "";
					String tol1 = "";
					String tof2 = "";
					String tol2 = "";
					String tof3 = "";
					String tol3 = "";
					
					String tofromF1 = "";
					String tofromF2 = "";
					String tofromF3 = "";
					String tofromF4 = "";
					
					SMRT SMRT = new SMRT();
					SMRT.clearAll();
								
					StringBuffer sb = new StringBuffer(response);		
					sb.replace(sb.length() - 1, sb.length(), "");
					sb.replace(0, callback.length() + 1, "");
					
					JSONParser parser = new JSONParser();
					JSONObject jsonObject = (JSONObject) parser.parse(sb.toString());
					if(jsonObject != null){
						JSONObject obj = (JSONObject) jsonObject.get("Result");
						if (obj != null) {
							String serviceNumber = (String) obj.get("ServiceNumber");
							String fromName = (String) obj.get("FromName");
							JSONObject cat1 = (JSONObject) obj.get("FromCategoryDay1");
							f1 = (String) cat1.get("First");
							l1 = (String) cat1.get("Last");
			
							JSONObject cat2 = (JSONObject) obj.get("FromCategoryDay2");
							f2 = (String) cat2.get("First");
							l2 = (String) cat2.get("Last");
			
							JSONObject cat3 = (JSONObject) obj.get("FromCategoryDay3");
							f3 = (String) cat3.get("First");
							l3 = (String) cat3.get("Last");
			
							JSONArray arr = (JSONArray) obj.get("FromFrequency");
							if (arr != null) {
								if (arr.size() > 0) {
									fromF1 = (String) arr.get(0);
								}
								if (arr.size() > 1) {
									fromF2 = (String) arr.get(1);
								}
								if (arr.size() > 2) {
									fromF3 = (String) arr.get(2);
								}
								if (arr.size() > 3) {
									fromF4 = (String) arr.get(3);
								}
							}
							
							//
							String toName = (String) obj.get("ToName");
							JSONObject tocat1 = (JSONObject) obj.get("ToCategoryDay1");
							if (tocat1 != null) {
								tof1 = (String) tocat1.get("First");
								tol1 = (String) tocat1.get("Last");
							}

							JSONObject tocat2 = (JSONObject) obj.get("ToCategoryDay2");
							if (tocat2 != null) {
								tof2 = (String) tocat2.get("First");
								tol2 = (String) tocat2.get("Last");
							}

							JSONObject tocat3 = (JSONObject) obj.get("ToCategoryDay3");
							if (tocat3 != null) {
								tof3 = (String) tocat3.get("First");
								tol3 = (String) tocat3.get("Last");
							}
			
							JSONArray toarr = (JSONArray) obj.get("ToFrequency");
							if (toarr != null) {
								if (toarr.size() > 0) {
									tofromF1 = (String) toarr.get(0);
								}
								if (toarr.size() > 1) {
									tofromF2 = (String) toarr.get(1);
								}
								if (toarr.size() > 2) {
									tofromF3 = (String) toarr.get(2);
								}
								if (toarr.size() > 3) {
									tofromF4 = (String) toarr.get(3);
								}
							}							
							//							
							SMRT.setBUSNO(serviceNumber);
							SMRT.setNAME(fromName);
							SMRT.setNAME2(fromName);
							SMRT.setFREQ1(fromF1);
							SMRT.setFREQ2(fromF2);
							SMRT.setFREQ3(fromF3);
							SMRT.setFREQ4(fromF4);
							
							SMRT.setF1(f1);
							SMRT.setL1(l1);
							SMRT.setF2(f2);
							SMRT.setL2(l2);
							SMRT.setF3(f3);
							SMRT.setL3(l3);
							
							SMRT.setTOBUSNO(serviceNumber);
							SMRT.setTONAME(toName);
							SMRT.setTONAME2(toName);
							SMRT.setTOFREQ1(tofromF1);
							SMRT.setTOFREQ2(tofromF2);
							SMRT.setTOFREQ3(tofromF3);
							SMRT.setTOFREQ4(tofromF4);
							
							SMRT.setTOF1(tof1);
							SMRT.setTOL1(tol1);
							SMRT.setTOF2(tof2);
							SMRT.setTOL2(tol2);
							SMRT.setTOF3(tof3);
							SMRT.setTOL3(tol3);
							
							StringBuffer page = new StringBuffer(SMRT.page1).append(SMRT.getBUSNO())	
							.append(SMRT.page2)
							.append(SMRT.page2_1)
							.append(SMRT.getNAME())
							.append(SMRT.page3).append(SMRT.getF1())
							.append(SMRT.page4).append(SMRT.getL1())
							.append(SMRT.page5).append(SMRT.getF2())
							.append(SMRT.page6).append(SMRT.getL2())
							.append(SMRT.page7).append(SMRT.getF3())
							.append(SMRT.page8).append(SMRT.getL3()).append(SMRT.page8_1);
							
							
							if((toName != null) && (!toName.equalsIgnoreCase("")) && 
									(tocat1 != null)){
								page.append(SMRT.page2_1)
								.append(SMRT.getTONAME())
								.append(SMRT.page3).append(SMRT.getTOF1())
								.append(SMRT.page4).append(SMRT.getTOL1())
								.append(SMRT.page5).append(SMRT.getTOF2())
								.append(SMRT.page6).append(SMRT.getTOL2())
								.append(SMRT.page7).append(SMRT.getTOF3())
								.append(SMRT.page8).append(SMRT.getTOL3()).append(SMRT.page8_1);
							}
							
							
							page.append(SMRT.page9)
							.append(SMRT.page9_1)
							.append(SMRT.getNAME2())						
							.append(SMRT.page10).append(SMRT.getFREQ1())
							.append(SMRT.page11).append(SMRT.getFREQ2())
							.append(SMRT.page12).append(SMRT.getFREQ3())
							.append(SMRT.page13).append(SMRT.getFREQ4())
							.append(SMRT.page14_1);
							
							if((toName != null) && (!toName.equalsIgnoreCase("")) && 
									(tocat1 != null)){
								page.append(SMRT.page9_1)
							.append(SMRT.getTONAME2())						
							.append(SMRT.page10).append(SMRT.getTOFREQ1())
							.append(SMRT.page11).append(SMRT.getTOFREQ2())
							.append(SMRT.page12).append(SMRT.getTOFREQ3())
							.append(SMRT.page13).append(SMRT.getTOFREQ4())
							.append(SMRT.page14_1);
							}
														
							page.append(SMRT.page14);
									
							webInfo.loadData(page.toString(),"text/html","UTF-8");
						}
					}
				}		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private String downloadUrl(String myurl) throws IOException {
		    InputStream is = null;
		    // Only display the first 500 characters of the retrieved
		    // web page content.
		    int len = 20000;
		        
		    try {
		        URL url = new URL(myurl);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setReadTimeout(100000 /* milliseconds */);
		        conn.setConnectTimeout(150000 /* milliseconds */);
		        conn.setRequestMethod("GET");
		        conn.setDoInput(true);
		        // Starts the query
		        conn.connect();
		        int response = conn.getResponseCode();
		        is = conn.getInputStream();

		        // Convert the InputStream into a string
		        String contentAsString = readIt(is, len);
		        return contentAsString;
		        
		    // Makes sure that the InputStream is closed after the app is
		    // finished using it.
		    } finally {
		        if (is != null) {
		            is.close();
		        } 
		    }
		}
		
		public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		    Reader reader = null;
		    reader = new InputStreamReader(stream, "UTF-8");        
		    char[] buffer = new char[len];
		    StringBuffer buf = new StringBuffer();
		    
		    int numRead = 0;
		    while ((numRead = reader.read(buffer)) >= 0) {
		        buf.append(new String(buffer, 0, numRead));
		    }
		    
		    return buf.toString();
		}
	}
}