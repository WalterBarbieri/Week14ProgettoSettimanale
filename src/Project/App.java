package Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

public class App {
	static Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws IOException {

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

		log.info("*****************RICERCA ELEMENTO BY AUTORE RANDOM******************");

		try {
			searchRandomElementsByAutore(catalogo);
			searchRandomElementsByAutore(catalogo);
			searchRandomElementsByAutore(catalogo);
		} catch (Exception e) {
			log.error("Errore: ", e);
		}

		log.info("*****************SCRIVI E LEGGI DA DISCO******************");
		saveOnDisk(catalogo);

		log.info(readFromFile().toString());

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
			int randomAutore = rnd.nextInt(list.size());
			Libro libro = (Libro) list.get(randomAutore);
			String autore = libro.getAutore();
			try {

				searchByAutore(catalogo, autore);
			} catch (Exception e) {
				log.info("Non ci sono elementi pubblicati dall'Autore: " + autore);
				e.printStackTrace();
			}

		}
	}

	// METODO PER SCRIVERE SU DISCO
	public static void saveOnDisk(Set<Pubblicazione> catalogo) throws IOException {
		File file = new File("Catalogo.txt");
		try (FileWriter writer = new FileWriter(file)) {
			for (Pubblicazione pubblicazione : catalogo) {
				writer.write(pubblicazione.toString());
				writer.write(System.lineSeparator());
			}
		}

	}

	// METODO PER LEGGERE DA DISCO
	public static Set<Pubblicazione> readFromFile() throws IOException {
		Set<Pubblicazione> catalogo = new HashSet<>();

		File file = new File("Catalogo.txt");
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] lineElements = line.split(" - ");

				long codiceIsbn = Long.parseLong(lineElements[0].split(": ")[1]);
				String titolo = lineElements[1].split(": ")[1];

				String annoPubblicazioneString = lineElements[2].split(" ")[2];

				LocalDate annoPubblicazione = LocalDate.of(Integer.parseInt(annoPubblicazioneString.substring(0, 4)),
						Integer.parseInt(annoPubblicazioneString.substring(5, 7)),
						Integer.parseInt(annoPubblicazioneString.substring(8, 10)));

				int numPagine = Integer.parseInt(lineElements[3].split(": ")[1]);

				if (lineElements[4].startsWith("Autore")) {
					String autore = lineElements[4].split(": ")[1];
					Genere genere = Genere.valueOf(lineElements[5].split(": ")[1]);

					Libro libro = new Libro(codiceIsbn, titolo, annoPubblicazione, numPagine, autore, genere);
					catalogo.add(libro);
				}

				if (lineElements[4].startsWith("Periodico")) {
					Periodicita periodicita = Periodicita.valueOf(lineElements[4].split(": ")[1]);

					Rivista rivista = new Rivista(codiceIsbn, titolo, annoPubblicazione, numPagine, periodicita);
					catalogo.add(rivista);
				}
			}
		} catch (IOException e) {
			log.error("Errore nella lettura dal Disco");
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Errore nella lettura del Disco");
			e.printStackTrace();
		}
		return catalogo;
	}
}
