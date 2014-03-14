package organizacion;

import java.lang.reflect.Type;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.infocongreso.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PersonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		Intent intent = getIntent();
		String persona = intent.getStringExtra("persona");

		Gson gson = new Gson();
		Type tipopersona = new TypeToken<Persona>() {
		}.getType();
		final Persona elegido = gson.fromJson(persona, tipopersona);

		TextView nombre = (TextView) findViewById(R.id.DialogNombre);
		TextView grupo = (TextView) findViewById(R.id.DialogGrupo);
		TextView origen = (TextView) findViewById(R.id.DialogOrigen);

		nombre.setText(elegido.getNombre());
		grupo.setText(elegido.getGrupo());
		origen.setText(elegido.getOrigen());

		Button boton_correo = (Button) findViewById(R.id.BtnCorreo);
		boton_correo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri direccion = Uri.parse("mailto:" + elegido.getCorreo());
				Intent in = new Intent(Intent.ACTION_SENDTO, direccion);
				startActivity(in);
			}

		});

		Button boton_webpersonal = (Button) findViewById(R.id.BtnWeb1);
		boton_webpersonal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(elegido.getWeb()));
				startActivity(intent);
			}

		});

		Button boton_webgrupo = (Button) findViewById(R.id.BtnWeb2);
		boton_webgrupo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(elegido.getWeb_grupo()));
				startActivity(intent);
			}

		});

		Button boton_weborigen = (Button) findViewById(R.id.BtnWeb3);
		boton_weborigen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(elegido.getWeb_origen()));
				startActivity(intent);
			}

		});

		ImageView imagen = (ImageView) findViewById(R.id.foto);

		if (elegido.getImagen().length() == 0) {
			imagen.setBackgroundResource(R.drawable.fondo_foto);
			imagen.setImageResource(R.drawable.placeholder);
		} else {
			String foto = elegido.getImagen();
			int identificador = getResources().getIdentifier(foto, "drawable",
					getPackageName());
			imagen.setImageResource(identificador);

			int heightpx = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 120, getResources()
							.getDisplayMetrics());

			imagen.getLayoutParams().height = heightpx;
		}

	}

}
