package savop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.Formatter;

/**
 *
 * @author
 */
public class SAVOP {

	private final static int MAX_DEPUTADOS = 230;
	private final static String FILE_DEPUTADOS = "Deputados.txt";
	private final static String PAGINA_HTML = "Pagina.html";
	private final static int MAX_LINHAS_PAGINA = 5;
	private final static String LOG_ERROS_DEP = ("errosDeputados.txt");

	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		String[][] deputados = new String[MAX_DEPUTADOS][4];
		char[] votacoes = new char[MAX_DEPUTADOS];
		String assuntoVotado = null;

		menu(deputados);

	}

	/**
	 * Carrega informa��o dos deputados para mem�ria a partir de ficheiro de
	 * texto FILE_DEPUTADOS
	 *
	 * @param deputados
	 *            - matriz de strings para guardar info dos deputados
	 * @return do n�mero de depotados inseridos na matriz
	 * @throws FileNotFoundException
	 */
	public static int lerInfoFicheiro(String[][] deputados)
			throws FileNotFoundException {

		Scanner fileIn = new Scanner(new File(FILE_DEPUTADOS));
		Formatter erros = new Formatter(new File(LOG_ERROS_DEP));
		int numDeputados = 0;

		while (fileIn.hasNext() && numDeputados < MAX_DEPUTADOS) {
			String linha = fileIn.nextLine();

			if (linha.length() > 0) {
				guardarDadosDeputado(linha, deputados, numDeputados, erros);
			}
		}
		fileIn.close();
		erros.close();
		return numDeputados;

	}

	public static int guardarDadosDeputado(String linha, String[][] deputados,
			int numDeputados, Formatter erros) {

		String[] temp = linha.split(";");

		if (temp.length == 4) {

			String id = temp[0].trim();

			if (id.length() == 5) {
				deputados[numDeputados][0] = id;
				deputados[numDeputados][1] = temp[1].trim();
				deputados[numDeputados][2] = temp[2].trim();
				deputados[numDeputados][3] = temp[3].trim();
				numDeputados++;
			} else {
				erros.format("Linha incorreta porque %s incorreto", id);
			}
		} else {
			erros.format("LInha: %f _ Campo Incorreto:\n %s ", numDeputados,
					linha);

		}

		return numDeputados;

	}

	/**
	 * Atualiza informa��o alter�vel de um deputado
	 * 
	 * @param idDeputado
	 *            - identifica��o do deputado
	 * @param deputados
	 *            - matriz com toda a informa��o dos deputados
	 * @param nDeputados
	 *            - n�mero de deputados
	 * @return false se o deputado n�o foi encontrado ou true se foi encontrado
	 *         e atualizadao provavelmente atualizado
	 */

	public static boolean actualizaInfoDeputado(String idDeputado,String[][] deputados, int numDeputados) {
		int pos, nc, np, ntp;
		if ((pos = pesquisarDeputadoPorID(idDeputado, numDeputados, deputados)) > -1) {
			System.out.printf("");
			int op;
			do {
				op = menuDadosDeputado(deputados[pos]);
				switch (op) {
				case 1:
					System.out.println("Novo nome:");
					deputados[pos][1] = sc.nextLine();
					break;
				case 2:
					System.out.println("Nova data:");
					deputados[pos][3] = sc.nextLine();
					break;
				case 0:
					System.out.println("Info correta");
					break;
				default:
					System.out.println("Op��o incorreta");
					break;
				}
			} while (op != 0);
		} else {
			System.out.printf("O deputado %s n�o foi encontrado!", idDeputado);
			return false;
		}
		return true;
	}

	public static int menuDadosDeputado(String[] deputado) {

		System.out.printf("%6s-%30s-%7s-%12s%n", deputado[0], deputado[1],
				deputado[2], deputado[3]);

		String texto = "ATUALIZAR" + "\n NOME ... 1"
				+ "\n DATA NASCIMENTO ... 2" + "\n TERMINAR ... 0"
				+ "\n\nQUAL A SUA OP��O?";
		System.out.printf("%n%s%n", texto);
		int op = sc.nextInt();
		sc.nextLine();
		return op;
	}

	public static int menu(String deputados[][]) throws FileNotFoundException {
		int op;
		do {
			System.out
					.print("1 - Ler informa��o do ficheiro de texto \n2 - Listar informa��o de deputados \n3 - Menu alterar informa��o de deputado \n4 - Ver vota��o \n5 - Ver informa��o deputados  na vota��o \n6 - Ver Resultados da ultima vota��o \n7 - Ver resultados por idades \n8 - Sair");
			op = sc.nextInt();
			int numDeputados = 0;
			switch (op) {
			case 1:
				lerInfoFicheiro(deputados);
				break;
			case 2:
				break;
			case 3:
				String idDeputado;
				idDeputado = procurarDeputado();
				actualizaInfoDeputado(idDeputado, deputados, numDeputados);
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				System.exit(0);
			default:
				System.out.print("Op��o Inv�lida");
			}
		} while (true);
	}

	public static String procurarDeputado() {
		String id;
		System.out.print("Qual � o ID do deputado que quer procurar?");
		id = sc.nextLine();
		while(id.length() != 5) {
			System.out.print("Id Inv�lido\nQual � o ID do deputado que quer procurar?");
			id = sc.nextLine();
		}
		return id;
	}


	public static int pesquisarDeputadoPorID(String idDeputado,
			int numDeputados, String[][] deputados) {

		for (int i = 0; i < numDeputados; i++) {
			if (deputados[i][0].equals(idDeputado)) {
				return i;
			}
		}
		return -1;
	}
	public static void listagemPaginada(String[][] deputados, int numDeputados) {
        int contPaginas = 0;
        for (int i = 0; i < numDeputados; i++) {
            if (i % MAX_LINHAS_PAGINA == 0) {
                if (contPaginas > 0) {
                    Utilitarios.pausa();
                }
                contPaginas++;
                System.out.println("\nP�gina: " + contPaginas);
                Utilitarios.cabecalho();

            }
            System.out.printf("%-6s||%-30s||%-10s||%-12s%n", deputados[i][0], deputados[i][1], deputados[i][2], deputados[i][3]);

        }
    }




//public static int lerVotacoes(char[] votacoes, int numDeputados, String assuntoVotado) throws FileNotFoundException {
//        String linhaTemp;
//        
//        System.out.println("Introduzir nome da vota��o desejada:");
//        assuntoVotado = sc.next();
//        
//        Scanner fileIn = new Scanner(new File(assuntoVotado));
//        
//        while (fileIn.hasNext()){
//            String linha = fileIn.nextLine();
//            
//            if (linha.length()>0){
//                linhaTemp=linha.trim();
//                
//            }
//        }
//        
//        
//        
//
//    }
}
