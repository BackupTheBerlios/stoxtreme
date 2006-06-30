/*
 *  Generated By:JavaCC: Do not edit this line. EvaluadorConstants.java
 */
package stoxtreme.servidor.eventos.evaluador;

/**
 *  Description of the Interface
 *
 *@author    Chris Seguin
 */
public interface EvaluadorConstants {

	int EOF = 0;
	int AND = 5;
	int OR = 6;
	int TRUE = 7;
	int FALSE = 8;
	int PAP = 9;
	int PCI = 10;
	int MENORIGUAL = 11;
	int MAYORIGUAL = 12;
	int IGUALDAD = 13;
	int DISTINTO = 14;
	int MENOR = 15;
	int MAYOR = 16;
	int POR = 17;
	int ENTRE = 18;
	int MAS = 19;
	int MENOS = 20;
	int MOD = 21;
	int NOT = 22;
	int VAR = 23;
	int NUMERO = 24;
	int NUMEROENTERO = 25;
	int DIGITO = 26;
	int DIGITOS = 27;
	int ERROR = 28;

	int DEFAULT = 0;

	String[] tokenImage = {
			"<EOF>",
			"\" \"",
			"\"\\t\"",
			"\"\\n\"",
			"\"\\r\"",
			"<AND>",
			"<OR>",
			"\"true\"",
			"\"false\"",
			"\"(\"",
			"\")\"",
			"\"<=\"",
			"\">=\"",
			"\"==\"",
			"<DISTINTO>",
			"\"<\"",
			"\">\"",
			"\"*\"",
			"<ENTRE>",
			"\"+\"",
			"\"-\"",
			"\"mod\"",
			"<NOT>",
			"<VAR>",
			"<NUMERO>",
			"<NUMEROENTERO>",
			"<DIGITO>",
			"<DIGITOS>",
			"<ERROR>",
			};

}
