package com.example.android.infocongreso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import localizacion.Entidad;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class VistaLocalizacion extends ActionBarActivity {

	private GoogleMap mapa = null;
	private LatLng ciudaddelcongreso = new LatLng(37.358518, -5.986456);
	int zoom = 13;

	List<Entidad> listaEntidades;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_localizacion);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (resultCode != ConnectionResult.SUCCESS) {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this, 10).show();
		} else {

			mapa = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
					ciudaddelcongreso, zoom);
			mapa.moveCamera(update);

			marcaCongreso();

			listaEntidades = obtenerEntidades();

			// Acción que se ejecutará cuando se pulse un marcador
			mapa.setOnMarkerClickListener(new OnMarkerClickListener() {
				public boolean onMarkerClick(Marker marker) {
					String url = getURL(marker.getTitle(), listaEntidades);
					Locale idioma = Locale.getDefault();

					if (marker.getTitle().equals("Congreso")) {
						irGooglePlus(url, marker.getTitle());
					} else {
						url = new String("https://plus.google.com/" + url
								+ "/about?gl=" + idioma.getLanguage() + "&hl="
								+ idioma.getLanguage());
						irGooglePlus(url, marker.getTitle());
					}

					return false;
				}
			});
		}
	}

	public List<Entidad> obtenerEntidades() {
		List<Entidad> entidades = null;
		XmlResourceParser parser = getResources().getXml(R.xml.lugares);

		int evento;
		try {
			evento = parser.getEventType();

			Entidad entidadActual = null;
			while (evento != XmlResourceParser.END_DOCUMENT) {
				String etiqueta = null;
				switch (evento) {
				case XmlResourceParser.START_DOCUMENT:
					entidades = new ArrayList<Entidad>();
					break;
				case XmlResourceParser.START_TAG:
					etiqueta = parser.getName();
					if (etiqueta.equals("item")) {
						entidadActual = new Entidad();
					} else if (entidadActual != null) {
						if (etiqueta.equals("nombre")) {
							entidadActual.setNombre(parser.nextText());
						} else if (etiqueta.equals("latitud")) {
							entidadActual.setLatitud(Double.valueOf(parser
									.nextText()));
						} else if (etiqueta.equals("longitud")) {
							entidadActual.setLongitud(Double.valueOf(parser
									.nextText()));
						} else if (etiqueta.equals("web")) {
							entidadActual.setWeb(parser.nextText());
						} else if (etiqueta.equals("tipo")) {
							entidadActual.setTipo(parser.nextText());
						}
					}
					break;

				case XmlResourceParser.END_TAG:
					etiqueta = parser.getName();
					if (etiqueta.equals("item") && entidadActual != null) {
						entidades.add(entidadActual);
					}
					break;
				}
				evento = parser.next();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return entidades;
	}

	private void irGooglePlus(final String url, String titulo) {

		AlertDialog.Builder alert = new AlertDialog.Builder(
				VistaLocalizacion.this);

		alert.setMessage(R.string.ir_web);
		alert.setTitle(titulo);
		alert.setPositiveButton(R.string.ir,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(url));
						startActivity(intent);
					}
				});
		alert.setNegativeButton(R.string.cancelar,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
		alert.show();
	}

	private void marcaCongreso() {
		mapa.addMarker(
				new MarkerOptions().position(ciudaddelcongreso).title(
						"Congreso")).setIcon(
				BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
	}

	private void addEntidades(List<Entidad> entidades, String tipo, float color) {
		for (int i = 0; i < entidades.size(); i++) {
			Entidad aux = entidades.get(i);
			if (aux.getTipo().equals(tipo)) {
				mapa.addMarker(
						new MarkerOptions()
								.position(
										new LatLng(aux.getLatitud(), aux
												.getLongitud())).title(
										aux.getNombre())).setIcon(
						BitmapDescriptorFactory.defaultMarker(color));
			} else if (aux.getTipo().equals("C")) {
				marcaCongreso();
			}
		}
	}

	private String getURL(String nombre, List<Entidad> lista) {
		String result = null;
		for (int i = 0; i < lista.size(); i++) {
			Entidad e = lista.get(i);
			if (e.getNombre().equals(nombre))
				result = e.getWeb();
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_localizacion, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_mapa:
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
					ciudaddelcongreso, zoom);
			mapa.moveCamera(update);
			return true;
		case R.id.menu_hotel:
			mapa.clear();
			addEntidades(listaEntidades, "H", BitmapDescriptorFactory.HUE_BLUE);
			return true;
		case R.id.menu_restaurante:
			mapa.clear();
			addEntidades(listaEntidades, "R", BitmapDescriptorFactory.HUE_RED);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
