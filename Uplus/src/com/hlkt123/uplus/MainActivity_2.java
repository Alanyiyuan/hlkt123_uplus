package com.hlkt123.uplus;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity_2 extends Activity implements OnClickListener {

	private Button register_btn;
	private Button login_btn;
	private Button order;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_2);
        
        register_btn = (Button) findViewById(R.id.register);
        login_btn = (Button)findViewById(R.id.login);
        order = (Button) findViewById(R.id.order);
        
        register_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        order.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register:
			Intent intent1 = new Intent(MainActivity_2.this, RegisterActivity.class);
			intent1.putExtra("LOGINTYPE", LoginActivity.LOGIN_MAIN);
			startActivity(intent1);
			break;
		case R.id.login:
			Intent intent2 = new Intent(MainActivity_2.this, LoginActivity.class);
			intent2.putExtra("LOGINTYPE", LoginActivity.LOGIN_MAIN);
			startActivity(intent2);
			break;
		case R.id.order:
			Intent intent3 = new Intent(MainActivity_2.this, OrderActivity.class);
			startActivity(intent3);
			break;
		default:
			break;
		}
	}
    
}
