package organizacion;

import java.util.List;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.android.infocongreso.R;
import com.google.gson.Gson;

public class TabComiteCientifico extends Fragment {

	List<Persona> comiteCientifico;

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);

		XmlResourceParser parser = getResources().getXml(
				R.xml.comite_cientifico);
		comiteCientifico = VistaOrganizacion.obtenerPersonas(parser);

		ListView listaCientifico = (ListView) getView().findViewById(
				R.id.ComiteCientifico);
		listaCientifico.setAdapter(new AdaptadorPersona(getActivity(),
				comiteCientifico));

		listaCientifico.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> pariente, View view,
					int posicion, long id) {
				Persona elegido = (Persona) pariente
						.getItemAtPosition(posicion);

				Gson gson = new Gson();
				String persona = gson.toJson(elegido);
				
				Intent intent = new Intent (getActivity(), PersonActivity.class);				
				intent.putExtra("persona", persona);
				
				getActivity().startActivity(intent);
				
			}
		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_cientifico, container, false);
	}
}
