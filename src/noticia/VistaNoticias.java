package noticia;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.infocongreso.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VistaNoticias extends ActionBarActivity {

	private Boolean primeraVez = false;
	private List<Noticia> noticias;
	private ListView listaNoticias;

	// Dirección del archivo css desde donde obtenemos las noticias
	private String direccionweb = "http://www.europapress.es/rss/rss.aspx";

	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noticias);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		listaNoticias = (ListView) findViewById(R.id.ListaNoticias);

		SharedPreferences appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(VistaNoticias.this
						.getApplicationContext());

		// Buscamos la lista de noticias en las preferencias compartidas
		if (appSharedPrefs.contains("Lista")) {
			String json = appSharedPrefs.getString("Lista", "");
			Gson gson = new Gson();
			Type tiponoticia = new TypeToken<ArrayList<Noticia>>() {
			}.getType();
			noticias = gson.fromJson(json, tiponoticia);
		} else {
			noticias = new ArrayList<Noticia>();
			primeraVez = true;
		}

		// Si es la primera vez que abrimos la aplicación, actualizamos la
		// lista; si no, devolvemos la guardada
		if (primeraVez) {
			progressDialog = ProgressDialog.show(VistaNoticias.this,
					getString(R.string.noticias),
					getString(R.string.cargando_noticias));
			CargarXmlTask tarea = new CargarXmlTask();
			tarea.execute(direccionweb);
		} else {
			AdaptadorNoticias adaptador = new AdaptadorNoticias(
					VistaNoticias.this);
			listaNoticias.setAdapter(adaptador);
		}

		// Cuando hacemos click en un elemento de la lista, abrimos un toast con
		// la descripción de la noticia
		listaNoticias.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> pariente, View view,
					int posicion, long id) {
				Noticia elegido = (Noticia) pariente
						.getItemAtPosition(posicion);

				final AlertDialog.Builder alert = new AlertDialog.Builder(
						VistaNoticias.this);

				alert.setMessage(elegido.getDescription());
				alert.setTitle(elegido.getTitulo());
				alert.setNegativeButton(R.string.cancelar,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});

				alert.show();

			}
		});

	}

	class AdaptadorNoticias extends ArrayAdapter<Noticia> {
		Activity context;

		AdaptadorNoticias(Activity context) {
			super(context, R.layout.noticia_individual, noticias);
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.noticia_individual, null);

			TextView lblIcon = (TextView) item.findViewById(R.id.icon);
			lblIcon.setText("#" + (position + 1));

			TextView lblTitulo = (TextView) item
					.findViewById(R.id.NoticiaTitulo);
			lblTitulo.setText(noticias.get(position).getTitulo());

			TextView lblFecha = (TextView) item.findViewById(R.id.Fecha);
			lblFecha.setText(noticias.get(position).getFecha());

			TextView lblSubtitulo = (TextView) item
					.findViewById(R.id.NoticiaDescripcion);
			lblSubtitulo.setText(noticias.get(position).getDescription());

			return (item);
		}
	}

	// Tarea asíncrona que carga de Internet el documento XML con las noticias
	private class CargarXmlTask extends AsyncTask<String, Integer, Boolean> {

		protected Boolean doInBackground(String... params) {
			String primeraNoticia = null;

			if (!primeraVez) {
				primeraNoticia = noticias.get(0).getTitulo();
			}

			RssParserPull saxparser = new RssParserPull(params[0]);
			noticias = saxparser.parse();

			// Guardamos la lista actualizada en las preferencias compartidas de
			// la aplicación
			SharedPreferences appSharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(VistaNoticias.this
							.getApplicationContext());
			Editor prefsEditor = appSharedPrefs.edit();
			Gson gson = new Gson();
			String json = gson.toJson(noticias);
			prefsEditor.putString("Lista", json);
			prefsEditor.commit();

			// Únicamente actualizamos la vista cuando las noticias han cambiado
			// o es la primera vez que usamos la aplicación
			if (primeraVez) {
				return true;
			} else {
				return !primeraNoticia.equals(noticias.get(0).getTitulo());
			}

		}

		protected void onPostExecute(Boolean result) {
			progressDialog.dismiss();
			Toast.makeText(getApplicationContext(),
					getString(R.string.noticias_actualizadas),
					Toast.LENGTH_SHORT).show();
			if (result) {
				AdaptadorNoticias adaptador = new AdaptadorNoticias(
						VistaNoticias.this);
				listaNoticias.setAdapter(adaptador);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_noticias, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// Actualizamos la lista de noticias
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_actualizar:

			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

			if (networkInfo != null && networkInfo.isConnected()) {
				progressDialog = ProgressDialog.show(VistaNoticias.this,
						getString(R.string.noticias),
						getString(R.string.actualizando_noticias));
				CargarXmlTask tarea = new CargarXmlTask();
				tarea.execute(direccionweb);
			} else {
				Toast.makeText(getApplicationContext(),
						getString(R.string.no_hay_conexion), Toast.LENGTH_SHORT)
						.show();
			}

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}