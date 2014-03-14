package com.example.android.infocongreso;

import organizacion.VistaOrganizacion;

import noticia.VistaNoticias;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Button btnNoticias = (Button) findViewById(R.id.BtnNoticias);
		final Button btnLocalizacion = (Button) findViewById(R.id.BtnLocalizacion);
		final Button btnPrograma = (Button) findViewById(R.id.BtnPrograma);
		final Button btnOrganizacion = (Button) findViewById(R.id.BtnOrganizacion);

		btnNoticias.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						VistaNoticias.class);

				startActivity(intent);
			}
		});

		btnLocalizacion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						VistaLocalizacion.class);

				startActivity(intent);
			}
		});

		btnPrograma.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						VistaPrograma.class);

				startActivity(intent);
			}
		});

		btnOrganizacion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						VistaOrganizacion.class);

				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

}