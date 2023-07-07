package Project;

import java.time.LocalDate;

public class Rivista extends Pubblicazione {
	private Periodicita periodicita;

	public Rivista(long codiceIsbn, String titolo, LocalDate annoPubblicazione, int numPagine,
			Periodicita periodicita) {
		super(codiceIsbn, titolo, annoPubblicazione, numPagine);
		this.setPeriodicita(periodicita);
	}

	public Periodicita getPeriodicita() {
		return periodicita;
	}

	public void setPeriodicita(Periodicita periodicita) {
		this.periodicita = periodicita;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ISBN: ").append(getCodiceIsbn());
		sb.append(" - Title: ").append(getTitolo());
		sb.append(" - Anno Pubblicazione: ").append(getAnnoPubblicazione());
		sb.append(" - Pagine: ").append(getNumPagine());
		sb.append(" - Periodico: ").append(getPeriodicita());
		return sb.toString();
	}

}
