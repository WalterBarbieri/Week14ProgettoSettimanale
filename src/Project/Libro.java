package Project;

import java.time.LocalDate;

public class Libro extends Pubblicazione {
	private String autore;

	private Genere genere;

	public Libro(long codiceIsbn, String titolo, LocalDate annoPubblicazione, int numPagine, String autore,
			Genere genere) {
		super(codiceIsbn, titolo, annoPubblicazione, numPagine);
		this.setAutore(autore);
		this.setGenere(genere);
		;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public Genere getGenere() {
		return genere;
	}

	public void setGenere(Genere generi) {
		this.genere = generi;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ISBN: ").append(getCodiceIsbn());
		sb.append(", Title: ").append(getTitolo());
		sb.append(", Anno Pubblicazione: ").append(getAnnoPubblicazione());
		sb.append(", Pagine: ").append(getNumPagine());
		sb.append(", Autore: ").append(getAutore());
		sb.append(", Genere: ").append(getGenere());
		return sb.toString();
	}

}
