package stoxtreme.cliente.infoLocal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *  Permite Descargar archivos XML
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class XMLDownloader {
	/**
	 *  Description of the Field
	 */
	public static boolean DEBUG = true;


	/**
	 *  Descarga los xml en local de una direccion remota donde se encuentran
	 *
	 *@param  dirLocal         Dirección destino
	 *@param  dirRemoto        Dirección origen
	 *@param  fichs            Lista de ficheros
	 *@exception  IOException  Excepción de entrada y salida manejando ficheros
	 */
	public static void downloadAll(String dirLocal, String dirRemoto, String[] fichs) throws IOException {
		for (int i = 0; i < fichs.length; i++) {
			File local = new File(dirLocal + "/" + fichs[i]);
			URL remoto = new URL(dirRemoto + "/" + fichs[i]);
			download(local, remoto);
		}
	}


	/**
	 *  Descarga una archivo en la dirección local indicada desde la dirección url 
	 *
	 *@param  local            Dirección destino
	 *@param  remoto           Dirección origen
	 *@exception  IOException  Excepción de entrada y salida manejando ficheros
	 */
	public static void download(File local, URL remoto) throws IOException {
		// Necesitamos preparar el fichero local
		File dirs = sacaDirectorio(local.getAbsolutePath());
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		try {
			if (local.exists()) {
				local.delete();
				local.createNewFile();
			}
			else {
				local.createNewFile();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(local));
		// Abrir el Stream con el URL remoto
		InputStreamReader isr = new InputStreamReader(remoto.openStream());
		BufferedReader reader = new BufferedReader(isr);
		StringBuffer buffer = new StringBuffer();
		int ch;
		while ((ch = isr.read()) > -1) {
			buffer.append((char) ch);
		}
		osw.append(buffer);

		// Cerrar las conexiones necesarias
		osw.close();
		isr.close();
		reader.close();

		if (DEBUG) {
			System.err.println("Descargado el fichero: " + remoto);
		}
	}


	/**
	 *  Obtiene el directorio de una ruta tanto local como url
	 *
	 *@param  ruta  Path de la ruta a explorar
	 *@return       Fichero creado con dicha ruta
	 */
	public static File sacaDirectorio(String ruta) {
		String[] splits;
		if ("\\".equals(File.separator)) {
			splits = ruta.split("\\" + File.separator);
		}
		else {
			splits = ruta.split("/");
		}
		String fichero = splits[splits.length - 1];
		int index = ruta.indexOf(fichero);
		return new File(ruta.substring(0, index));
	}
}
