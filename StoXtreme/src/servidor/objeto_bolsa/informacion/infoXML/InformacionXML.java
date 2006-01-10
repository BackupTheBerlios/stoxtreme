package servidor.objeto_bolsa.informacion.infoXML;

import interfaz_remota.IInformacion;

import java.util.Date;
import java.util.Vector;

import servidor.objeto_bolsa.informacion.*;

public class InformacionXML implements IInformacion{
	/* 
	 * Actuará como un monitor de lectores escritores, creando inicialmente el hilo que escribirá
	 * Toda la información concurrentemente y que comprobará si ha habido cambios o quizas mejor
	 * mediante un metodo de UPDATE, los lectores leeran eventualmente la información que necesiten.
	 * 
	 * Si se omite el nombre de la empresa se supondra que el archivo esta
	 * dedicado por entero a la empresa y por tanto no tiene que buscar el tag
	 * 
	 * FORMATO DE LOS XMLS
	 * XML GLOBAL
	 * <info>
	 * 		<empresa nombre="NombreDeLaEmpresa">
	 * 			<info_bursatil>
	 * 				<participaciones> NUMERO_PARTICIPACIONES </participaciones>
	 * 				<ampliaciones> NUMERO_AMPLIACIONES </ampliaciones>
	 * 				<capital> DINERO_CAPITAL </capital>
	 * 				<archivo_historico>
	 * 					FICHERO_HISTORICO.xml
	 * 				</archivo_historico>
	 * 			<info_bursatil>
	 * 			<balances>
	 * 				<balance fecha="FECHA">
	 * 					<efectivo></efectivo>
	 * 					<bienes></bienes>
	 * 					<empleados></empleados>
	 * 				</balance>
	 * 			</balances>
	 * 			<cuentas>
	 * 				<cuenta fecha="FECHA">
	 * 					<periodo> MENSUAL | TRIMESTRAL |ANUAL </periodo>
	 * 					<ganancias>GANANCIAS</ganancias>
	 * 					<perdidas>PERDIDAS</perdidas>
	 * 				</cuenta>
	 * 			</cuentas>
	 * 			<perspectiva></perspectiva>
	 * 		</empresa>
	 * </info>
	*/
	public InformacionXML(String archivo, String empresa){
		
	}

	public Balance getBalanceActual() {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector getBalances(Date inicio, Date fin) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setBalance(Balance b, Date fecha) {
		// TODO Auto-generated method stub
		
	}

	public Vector getCuentas(Date inicio, Date fin) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCuenta(Cuenta c, Date fecha) {
		// TODO Auto-generated method stub
		
	}

	public InfoBursatil getDatosBursatiles() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInfoBursatil(InfoBursatil i) {
		// TODO Auto-generated method stub
		
	}

	public String getPerspectivaFuturo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPerspectivaFuturo(String s) {
		// TODO Auto-generated method stub
		
	}
}
