package com.example.android.infocongreso;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VistaPrograma extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_programa);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(
				this, R.array.valores_topics, R.layout.milista_xml);

		ListView lista = (ListView) findViewById(R.id.topics);

		View header = View.inflate(this, R.layout.programa_header, null);
		View footer = View.inflate(this, R.layout.programa_footer, null);

		lista.addHeaderView(header);
		lista.addFooterView(footer);

		lista.setAdapter(adaptador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_programa, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);

	}
}
