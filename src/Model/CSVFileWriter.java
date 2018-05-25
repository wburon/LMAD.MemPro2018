package Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import DAO.ClientDAO;

public class CSVFileWriter {

	private static File file;
	private static char separator;
	private static String[] titles;

	public CSVFileWriter(File file) {
		this(file, ';');
	}

	public CSVFileWriter(File file, char separator) {

		if (file == null) {
			throw new IllegalArgumentException("Le fichier ne peut pas etre nul");
		}

		this.file = file;
		this.separator = separator;
	}

	private static void writeEmptyFile() {
		// TODO
	}

	public static void write(List<Map<String, String>> mappedData, String[] titles) throws IOException {
		if (mappedData == null) {
			throw new IllegalArgumentException("la liste ne peut pas être nulle");
		}

		if (titles == null) {
			throw new IllegalArgumentException("les titres ne peuvent pas être nuls");
		}

		if (mappedData.isEmpty()) {
			writeEmptyFile();
		}

		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);

		// Les titres
		boolean first = true;
		for (String title : titles) {
			if (first) {
				first = false;
			} else {
				bw.write(separator);
			}
			write(title, bw);
		}
		bw.write("\n");

		// Les données
		for (Map<String, String> oneData : mappedData) {
			first = true;
			for (String title : titles) {
				if (first) {
					first = false;
				} else {
					bw.write(separator);
				}
				final String value = oneData.get(title);
				write(value, bw);

			}
			bw.write("\n");

		}
		bw.close();
		fw.close();
	}

	private static void write(String value, BufferedWriter bw) throws IOException {

		if (value == null) {
			value = "";
		}

		boolean needQuote = false;

		if (value.indexOf("\n") != -1) {
			needQuote = true;
		}

		if (value.indexOf(separator) != -1) {
			needQuote = true;
		}

		if (value.indexOf("\"") != -1) {
			needQuote = true;
			value = value.replaceAll("\"", "\"\"");
		}

		if (needQuote) {
			value = "\"" + value + "\"";
		}

		bw.write(value);
	}

	private static List<Map<String, String>> createMap() {

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		ClientDAO clDAO = new ClientDAO();
		ArrayList<Client> listClient = clDAO.getListAccueil();

		for (Client c : listClient) {
			Map<String, String> oneData = new HashMap<String, String>();
			oneData.put("Nom", c.getNom());
			oneData.put("Prénom", c.getPrenom());
			oneData.put("Adresse", c.getAdresse());
			oneData.put("Ville", c.getVille());
			oneData.put("Telephone", String.valueOf(c.getTel()));
			oneData.put("Courriel", c.getMail());
			int i = 1;
			for (Materiel m : clDAO.getListMateriel(c)) {
				oneData.put("Nom Materiel " + i, m.getNom());
				oneData.put("Type Materiel " + i, m.getType());
				oneData.put("Numéro de série Materiel " + i, m.getNumSerie());
				i++;
			}
			data.add(oneData);
		}
		return data;
	}

	public void launch() throws IOException {
		// Param
		final List<Map<String, String>> data = createMap();
		final List<String> listTitles = Arrays.asList( "Nom", "Prénom", "Adresse", "Ville", "Telephone", "Courriel");
		int nbMaterielMaximum = Methode.getPlusLongue(data);
		final String[] titles = Methode.convertListWithNMat(listTitles, nbMaterielMaximum);

		// Appel
		write(data, titles);

		// Resultat attendu
		final String[] wantedTitles = { "Nom", "Prénom", "Adresse", "Ville", "Telephone", "Courriel" };

		

	}

	public void main(String[] args) throws IOException {
		launch();
	}

}
