package stoxtreme.cliente.infoLocal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

public class XMLDownloader {
	public static boolean DEBUG = true;
	
	public static void downloadAll(String dirLocal, String dirRemoto, String[] fichs) throws IOException{
		System.out.println("En XML download: "+fichs.length);
		for(int i=0; i < fichs.length; i++){
			System.out.println("entro:"+i);
			File local = new File(dirLocal+"/"+fichs[i]);
			URL remoto = new URL(dirRemoto+"/"+fichs[i]);
			download(local,remoto);
		}
	}
	
	public static void download(File local, URL remoto) throws IOException{
		// Necesitamos preparar el fichero local
		File dirs = sacaDirectorio(local.getAbsolutePath());
		if(!dirs.exists()){
			dirs.mkdirs();
		}
		try {
			if(local.exists()){
				local.delete();
				local.createNewFile();
			}
			else{
				local.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(local));
		// Abrir el Stream con el URL remoto
		InputStreamReader isr = new InputStreamReader(remoto.openStream());
		BufferedReader reader = new BufferedReader(isr);
		StringBuffer buffer = new StringBuffer();
		int ch;
		while((ch =isr.read())>-1){
			buffer.append((char)ch);
		}
		osw.append(buffer);
		
		// Cerrar las conexiones necesarias
		osw.close();
		isr.close();
		reader.close();
		
		if(DEBUG)
			System.err.println("Descargado el fichero: "+remoto);
	}
	
	public static File sacaDirectorio(String ruta){
		String[] splits;
		if("\\".equals(File.separator)){
			splits = ruta.split("\\"+File.separator);
		}
		else{
			splits = ruta.split("/");
		}
		String fichero = splits[splits.length-1];
		int index = ruta.indexOf(fichero);
		return new File(ruta.substring(0,index));
	}
	
	public static void main(String[] args){
		try {
			File local = new File("conf/nose/sigosinsaber/prueba.xml");
			URL fichero = new URL("http://localhost:8080/Stoxtreme/config/antena3.xml");
			download(local,fichero);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
