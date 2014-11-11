package com.vimeotest;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.VimeoApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

//========================================================
public class VimeoActivity extends ActionBarActivity {

	private final static String ApiKey 		= "";
	private final static String SecretKey  	= "";
	
	private TextView resp;
	
	//===========================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vimeo);
		resp = (TextView)findViewById(R.id.resp);
		if(ApiKey.equals("") || SecretKey.equals(""))
			resp.setText("Set credentials please");
		else{
			new Thread(){
				public void run(){
					fetchToken();
				}
			}.start();
		}
	}
	
	//===========================================
	private void fetchToken(){
		OAuthService service = new ServiceBuilder()
	        .provider(VimeoApi.class)
	        .apiKey(ApiKey)
	        .apiSecret(SecretKey)
	        .build();
		OAuthRequest req = new OAuthRequest(Verb.GET, "http://vimeo.com/api/rest/v2?format=json&method=vimeo.videos.getAll&user_id=brellow");
		Token token = new Token("","");
		service.signRequest(token, req);
		final Response res = req.send();
		Log.i("vimeo", res.getBody());
		runOnUiThread(new Runnable(){
			public void run(){
				resp.setText(res.getBody());
			}
		});
	}
}
