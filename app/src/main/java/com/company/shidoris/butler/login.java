package com.company.shidoris.butler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class login extends AppCompatActivity {


    private LoginButton loginButton;
    private CallbackManager callbackManager;

    public login() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final Button btn_login = (Button)findViewById(R.id.button_login);

    btn_login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            login();
        }
    });


        callbackManager = CallbackManager.Factory.create();
        loginButton =(LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               /* Toast.makeText(login.this, "Bienvenido",
                        Toast.LENGTH_LONG).show();*/

               login();
            }

            @Override
            public void onCancel() {
                Toast.makeText(login.this, "Cancelado Por el Usuario",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(login.this, "Error",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void login(){

        Intent myIntent = new Intent(login.this, MainActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        login.this.startActivity(myIntent);
    }
}
