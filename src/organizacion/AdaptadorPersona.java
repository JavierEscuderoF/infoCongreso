package organizacion;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.infocongreso.R;

public class AdaptadorPersona extends ArrayAdapter<Persona> {
	Activity context;
	List<Persona> lista;

	AdaptadorPersona(Activity context, List<Persona> lista) {
		super(context, R.layout.persona_individual, lista);
		this.context = context;
		this.lista = lista;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View item = inflater.inflate(R.layout.persona_individual, null);

		TextView lblIcon = (TextView) item.findViewById(R.id.numero);
		lblIcon.setText("#" + (position + 1));

		TextView lblTitulo = (TextView) item.findViewById(R.id.PersonaNombre);
		lblTitulo.setText(lista.get(position).getNombre());

		TextView lblSubtitulo = (TextView) item
				.findViewById(R.id.PersonaDescripcion);
		lblSubtitulo.setText(lista.get(position).getOrigen());

		return (item);
	}
}
