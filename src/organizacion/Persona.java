package organizacion;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Persona implements Serializable {
	private String nombre;
	private String correo;
	private String web;
	private String imagen;
	private String grupo;
	private String web_grupo;
	private String origen;
	private String web_origen;

	public Persona() {
		this.nombre = new String();
		this.correo = new String();
		this.web = new String();
		this.imagen = new String();
		this.grupo = new String();
		this.web_grupo = new String();
		this.origen = new String();
		this.web_origen = new String();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getWeb_grupo() {
		return web_grupo;
	}

	public void setWeb_grupo(String web_grupo) {
		this.web_grupo = web_grupo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getWeb_origen() {
		return web_origen;
	}

	public void setWeb_origen(String web_origen) {
		this.web_origen = web_origen;
	}
}
