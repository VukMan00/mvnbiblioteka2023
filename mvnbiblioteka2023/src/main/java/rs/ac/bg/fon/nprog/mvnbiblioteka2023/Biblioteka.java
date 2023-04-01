package rs.ac.bg.fon.nprog.mvnbiblioteka2023;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rs.ac.bg.fon.nprog.mvnbiblioteka2023.interfejs.BibliotekaInterfejs;



/**
 * Implementira biblioteku, pri cemu je
 * lista sa knjigama implementirana preko ArrayList klase
 * 
 * @author Bojan Tomic
 * @since 0.9.0
 */
public class Biblioteka implements BibliotekaInterfejs {

	/**
	 * Lista sa knjigama iz biblioteke - ArrayList implementacija
	 * 
	 * @see biblioteka.Knjiga
	 */
	private List<Knjiga> knjige = new ArrayList<Knjiga>();
	
	
	//Pravi komentar, pravi kod
	@Override
	public void dodajKnjigu(Knjiga knjiga) {
		if (knjiga == null)
			throw new NullPointerException("Knjiga ne sme biti null");
		
		if (knjige.contains(knjiga))
			throw new IllegalArgumentException("Knjiga je duplikat");
		
		knjige.add(knjiga);
	}

	@Override
	public void obrisiKnjigu(Knjiga knjiga) {
		knjige.remove(knjiga);
	}

	@Override
	public List<Knjiga> vratiSveKnjige() {
		return knjige;
	}

	@Override
	public List<Knjiga> pronadjiKnjigu(Autor autor, long isbn, String naslov, String izdavac) {
		if (autor == null && isbn < 0 && naslov == null && izdavac == null)
			throw new IllegalArgumentException("Morate uneti bar neki kriterijum za pretragu");
		
		List<Knjiga> rezultati = new ArrayList<Knjiga>();
		
		if (naslov!=null)
		  for (Knjiga k: knjige)
			if (k.getNaslov().toLowerCase().contains(naslov.toLowerCase().trim()))
				rezultati.add(k);		
		
		return rezultati;
	}

	@Override
	public void upisKnjigaUFajl(String putanjaFajla) {
		if(putanjaFajla==null) {
			throw new NullPointerException("Putanja fajla ne sme biti null");
		}
		if(putanjaFajla.isEmpty()) {
			throw new IllegalArgumentException("Putanja fajla ne sme biti prazan String");
		}
		try(FileWriter out = new FileWriter(putanjaFajla)){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			out.write(gson.toJson(knjige));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void citanjeIzFajla(String putanjaFajla) {
		if(putanjaFajla==null) {
			throw new NullPointerException("Putanja fajla ne sme biti null");
		}
		if(putanjaFajla.isEmpty()) {
			throw new IllegalArgumentException("Putanja fajla ne sme biti prazan String");
		}
		try(FileReader in = new FileReader(putanjaFajla)){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			List<Knjiga> noveKnjige = Arrays.asList(gson.fromJson(in, Knjiga[].class));
			
			for(Knjiga knjiga:noveKnjige) {
				if(!knjige.contains(knjiga)) {
					knjige.add(knjiga);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pronadjiKnjigu(Autor autor, long isbn, String naslov, String izdavac, String putanjaFajla) {
		if (autor == null && isbn < 0 && naslov == null && izdavac == null) {
			throw new IllegalArgumentException("Morate uneti bar neki kriterijum za pretragu");
		}
		if(putanjaFajla==null) {
			throw new NullPointerException("Putanja fajla ne sme biti null");
		}
		if(putanjaFajla.isEmpty()) {
			throw new IllegalArgumentException("Putanja fajla ne sme biti prazan String");
		}
		
		List<Knjiga> rezultati = new ArrayList<Knjiga>();
		
		if (naslov!=null) {
		  for (Knjiga k: knjige) {
			if (k.getNaslov().toLowerCase().contains(naslov.toLowerCase().trim())) {
				rezultati.add(k);
			}
		  }
		}
		
		try(FileWriter out = new FileWriter(putanjaFajla)){
			Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
			out.write(gson.toJson(rezultati));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
