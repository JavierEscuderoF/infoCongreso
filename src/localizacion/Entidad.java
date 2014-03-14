package localizacion;

public class Entidad {
	private String nombre;
	private double latitud;
	private double longitud;
	private String web;
	private String tipo;

	public Entidad(String nombre, double latitud, double longitud, String web,
			String tipo) {
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
		this.web = web;
		this.tipo = tipo;
	}

	public Entidad() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
