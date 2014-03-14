package organizacion;

import java.util.ArrayList;
import java.util.List;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.infocongreso.R;

public class VistaOrganizacion extends ActionBarActivity {

	List<Persona> comiteOrganizador;
	List<Persona> comiteCientifico;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organizacion);

		ActionBar ab = getSupportActionBar();

		ab.setDisplayHomeAsUpEnabled(true);
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab tab1 = ab.newTab().setText(R.string.comite_organizador);
		ActionBar.Tab tab2 = ab.newTab().setText(R.string.comite_cientifico);

		Fragment fragment1 = new TabComiteOrganizador();
		Fragment fragment2 = new TabComiteCientifico();
		
		tab1.setTabListener(new TabsListener(fragment1));
		tab2.setTabListener(new TabsListener(fragment2));

		ab.addTab(tab1);
		ab.addTab(tab2);

	}


	public static List<Persona> obtenerPersonas(XmlResourceParser parse) {
		List<Persona> personas = null;
		XmlResourceParser parser = parse;

		int evento;
		try {
			evento = parser.getEventType();

			Persona personaActual = null;
			while (evento != XmlResourceParser.END_DOCUMENT) {
				String etiqueta = null;
				switch (evento) {
				case XmlResourceParser.START_DOCUMENT:
					personas = new ArrayList<Persona>();
					break;
				case XmlResourceParser.START_TAG:
					etiqueta = parser.getName();
					if (etiqueta.equals("item")) {
						personaActual = new Persona();
					} else if (personaActual != null) {
						if (etiqueta.equals("nombre")) {
							personaActual.setNombre(parser.nextText());
						} else if (etiqueta.equals("correo")) {
							personaActual.setCorreo(parser.nextText());
						} else if (etiqueta.equals("web")) {
							personaActual.setWeb(parser.nextText());
						} else if (etiqueta.equals("imagen")) {
							personaActual.setImagen(parser.nextText());
						} else if (etiqueta.equals("grupo")) {
							personaActual.setGrupo(parser.nextText());
						} else if (etiqueta.equals("web-grupo")) {
							personaActual.setWeb_grupo(parser.nextText());
						} else if (etiqueta.equals("origen")) {
							personaActual.setOrigen(parser.nextText());
						} else if (etiqueta.equals("web-origen")) {
							personaActual.setWeb_origen(parser.nextText());
						}
					}
					break;

				case XmlResourceParser.END_TAG:
					etiqueta = parser.getName();
					if (etiqueta.equals("item") && personaActual != null) {
						personas.add(personaActual);
					}
					break;
				}
				evento = parser.next();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return personas;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_organizacion, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);

	}
}
