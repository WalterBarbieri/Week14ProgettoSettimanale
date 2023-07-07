package Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

public class App {
	static Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		Set<Pubblicazione> catalogo = new HashSet<>();

		log.info("*****************CREAZIONE RANDOM LIBRI E RIVISTE******************");

		try {
			for (int i = 0; i < 100; i++) {
				Libro libro = genRandomBook();
				catalogo.add(libro);
			}

			for (int i = 0; i < 100; i++) {
				Rivista rivista = genRandomRivista();
				catalogo.add(rivista);
			}

			catalogo.forEach(element -> log.info(element.toString()));
		} catch (Exception e) {
			log.error("Errore: ", e);
			;
		}

		log.info("*****************RIMOZIONE ELEMENTO RANDOM******************");
		try {
			removeRandomElement(catalogo);
			removeRandomElement(catalogo);
			removeRandomElement(catalogo);
		} catch (Exception e) {
			log.error("Errore: ", e);
		}

		log.info("*****************RICERCA ELEMENTO BY ISBN RANDOM******************");
		try {
			searchRandomElementByIsbn(catalogo);
			searchRandomElementByIsbn(catalogo);
			searchRandomElementByIsbn(catalogo);
		} catch (Exception e) {
			log.error("Errore: ", e);
		}

		log.info("*****************RICERCA ELEMENTO BY ANNO PUBBLICAZIONE RANDOM******************");

		try {
			searchRandomElementByAnnoPubblicazione(catalogo);
			searchRandomElementByAnnoPubblicazione(catalogo);
			searchRandomElementByAnnoPubblicazione(catalogo);
		} catch (Exception e) {
			log.error("Errore: ", e);
		}

	}

	// METODI PER LA GENERAZIONE RANDOM DI LIBRI E RIVISTE
	private static Libro genRandomBook() {
		Faker faker = new Faker();
		Random rnd = new Random();

		long codiceIsbn = faker.number().randomNumber(13, true);
		String title = faker.book().title();
		LocalDate annoPubblicazione = LocalDate.of(rnd.nextInt(2023 - 1950) + 1950, rnd.nextInt(12) + 1,
				rnd.nextInt(28) + 1);
		int numPagine = rnd.nextInt((500) + 100);
		String autore = faker.book().author();

		Genere[] generi = Genere.values();
		Genere genere = generi[rnd.nextInt(generi.length)];

		Libro libro = new Libro(codiceIsbn, title, annoPubblicazione, numPagine, autore, genere);
		return libro;
	}

	private static Rivista genRandomRivista() {
		Faker faker = new Faker();
		Random rnd = new Random();

		long codiceIsbn = faker.number().randomNumber(13, true);
		String title = faker.book().title();
		LocalDate annoPubblicazione = LocalDate.of(rnd.nextInt(2023 - 1950) + 1950, rnd.nextInt(12) + 1,
				rnd.nextInt(28) + 1);
		int numPagine = rnd.nextInt((500) + 100);
		String autore = faker.book().author();

		Periodicita[] periodi = Periodicita.values();
		Periodicita periodicita = periodi[rnd.nextInt(periodi.length)];

		Rivista rivista = new Rivista(codiceIsbn, title, annoPubblicazione, numPagine, periodicita);
		return rivista;
	}

	// METODO PER LA RIMOZIONE TRAMITE ISBN -> IMPLEMENTATO IN METODO
	// removeRandomElement() PER IMPOSTAZIONE PROGETTO -> SI PU0' USARE
	// INDIPENDENTEMENTE.
	private static void removeByIsbn(Set<Pubblicazione> catalogo, long codiceIsbn) {

		Iterator<Pubblicazione> iterator = catalogo.iterator();
		while (iterator.hasNext()) {
			Pubblicazione pubblicazione = iterator.next();
			if (pubblicazione.getCodiceIsbn() == codiceIsbn) {
				iterator.remove();
				break;
			}
		}
	}

	// METODO RIMOZIONE TRAMITE ISBN RANDOM -> NON AVEVA SENSO USARE IL
	// POLIMORFISMO QUA
	private static void removeRandomElement(Set<Pubblicazione> catalogo) {
		Random rnd = new Random();
		List<Pubblicazione> list = new ArrayList<>(catalogo);
		if (!list.isEmpty()) {
			int randomIndex = rnd.nextInt(list.size());

			Pubblicazione pubblicazione = list.get(randomIndex);
			log.info("Hai eleminato il seguente oggetto: " + pubblicazione.toString());
			long codiceIsbn = pubblicazione.getCodiceIsbn();
			removeByIsbn(catalogo, codiceIsbn);
		}
	}

	// METODO PER RICERCA TRAMITE ISBN - COME SOPRA
	private static void searchByIbsn(Set<Pubblicazione> catalogo, long codiceIsbn) {
		Optional<Pubblicazione> pubblicazione = catalogo.stream()
				.filter(element -> element.getCodiceIsbn() == codiceIsbn).findFirst();
		log.info("Elemento selezionato: " + pubblicazione.get().toString());
	}

	// METODO RICERCA TRMITE ISBN RANDOM - COME SOPRA
	private static void searchRandomElementByIsbn(Set<Pubblicazione> catalogo) {
		Random rnd = new Random();
		List<Pubblicazione> list = new ArrayList<>(catalogo);
		if (!list.isEmpty()) {
			int randomIndex = rnd.nextInt(list.size());

			Pubblicazione pubblicazione = list.get(randomIndex);
			long codiceIsbn = pubblicazione.getCodiceIsbn();
			searchByIbsn(catalogo, codiceIsbn);
		}
	}

	// METODO PER RICERCA TRAMITE ANNO PUBBLICAZIONE - COME SOPRA
	private static void searchByAnnoPubblicazione(Set<Pubblicazione> catalogo, int annoPubblicazione) {
		List<Pubblicazione> pubblicazione = catalogo.stream()
				.filter(element -> element.getAnnoPubblicazione().getYear() == annoPubblicazione)
				.collect(Collectors.toList());
		log.info("Elementi trovati pubblicati nell'anno: " + annoPubblicazione);
		pubblicazione.forEach(el -> log.info(el.toString()));
	}

	// METODO RICERCA TRAMITE ANNO PUBBLICAZIONE RANDOM - COME SOPRA
	private static void searchRandomElementByAnnoPubblicazione(Set<Pubblicazione> catalogo) {
		Random rnd = new Random();

		List<Pubblicazione> list = new ArrayList<>(catalogo);
		if (!list.isEmpty()) {
			int randomYear = rnd.nextInt(2023 - 1950) + 1950;
			try {
				searchByAnnoPubblicazione(catalogo, randomYear);
			} catch (Exception e) {
				log.info("Non ci sono elementi pubblicati nell'anno: " + randomYear);
				e.printStackTrace();
			}
		}
	}

	// METODO PER RICERCA TRAMITE AUTORE - COME SOPRA
	private static void searchByAutore(Set<Pubblicazione> catalogo, String autore) {
		List<Pubblicazione> pubblicazione = catalogo.stream().filter(el -> el instanceof Libro)
				.filter(el -> ((Libro) el).getAutore().equalsIgnoreCase(autore)).collect(Collectors.toList());
		log.info("Elementi trovati pubblicati dall'Autore: " + autore);
		pubblicazione.forEach(el -> log.info(el.toString()));
	}

	// METODO RICERCA TRAMITE AUTORE RANDOM - COME SOPRA
	private static void searchRandomElementsByAutore(Set<Pubblicazione> catalogo) {
		Random rnd = new Random();
		List<Pubblicazione> list = catalogo.stream().filter(el -> el instanceof Libro).collect(Collectors.toList());
		if (!list.isEmpty()) {
			int randomAutor = rnd.nextInt(list.size());

		}
	}

}
