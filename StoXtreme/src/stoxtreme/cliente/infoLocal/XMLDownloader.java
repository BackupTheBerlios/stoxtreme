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
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class XMLDownloader {
	/**
	 *  Description of the Field
	 */
	public static boolean DEBUG = true;


	/**
	 *  Description of the Method
	 *
	 *@param  dirLocal         Description of Parameter
	 *@param  dirRemoto        Description of Parameter
	 *@param  fichs            Description of Parameter
	 *@exception  IOException  Description of Exception
	 */
	public static void downloadAll(String dirLocal, String dirRemoto, String[] fichs) throws IOException {
		for (int i = 0; i < fichs.length; i++) {
			File local = new File(dirLocal + "/" + fichs[i]);
			URL remoto = new URL(dirRemoto + "/" + fichs[i]);
			download(local, remoto);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  local            Description of Parameter
	 *@param  remoto           Description of Parameter
	 *@exception  IOException  Description of Exception
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
	 *  Description of the Method
	 *
	 *@param  ruta  Description of Parameter
	 *@return       Description of the Returned Value
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


	/**
	 *  The main program for the XMLDownloader class
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String[] args) {
		try {
			File local = new File("conf/nose/sigosinsaber/prueba.xml");
			URL fichero = new URL("http://localhost:8080/Stoxtreme/config/antena3.xml");
			download(local, fichero);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
