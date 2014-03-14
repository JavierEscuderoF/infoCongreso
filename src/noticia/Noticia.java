package noticia;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Noticia implements Serializable {
	private String titulo;
	private String link;
	private String descripcion;
	private String guid;
	private String fecha;

	public Noticia() {
		this.titulo = new String();
		this.link = new String();
		this.descripcion = new String();
		this.guid = new String();
		this.fecha = new String();
	}

	public String getTitulo() {
		return titulo;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return descripcion;
	}

	public String getGuid() {
		return guid;
	}

	public String getFecha() {
		return fecha;
	}

	public void setTitulo(String t) {
		titulo = t;
	}

	public void setLink(String l) {
		link = l;
	}

	public void setDescripcion(String d) {
		descripcion = d;
	}

	public void setGuid(String g) {
		guid = g;
	}

	public void setFecha(String f) {
		fecha = f;
	}
}
