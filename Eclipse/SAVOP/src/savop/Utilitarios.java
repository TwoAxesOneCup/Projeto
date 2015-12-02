package savop;

import java.util.Calendar;

public class Utilitarios{
    
    /**
     * o método recebe uma string com um nome completo e retorna apenas o
     * primeiro e último nome
     *
     * @param nome
     * @return
     */
    public static String nomeApelido(String nome) {
        nome = nome.trim();
        int primeiro = nome.indexOf(' ');
        int ultimo = nome.lastIndexOf(' ');

        return nome.substring(0, primeiro) + nome.substring(ultimo);
    }

    public static void mostrarDataHoje() {
        Calendar hoje = Calendar.getInstance();
        System.out.println((hoje.get(Calendar.DAY_OF_MONTH)) + "/" + (hoje.get(Calendar.MONTH) + 1) + "/" + (hoje.get(Calendar.YEAR)));
    }

    /**
     * método para calcular a idade de um deputado
     *
     * @param dataNasc - String com a data de nascimento no formato YYYYMMDD
     * @return
     */
    public static int idade(String dataNasc) {
        int ano = Integer.parseInt(dataNasc.substring(0, 4));
        int mes = Integer.parseInt(dataNasc.substring(4, 6));
        int dia = Integer.parseInt(dataNasc.substring(6, 8));

        Calendar hoje = Calendar.getInstance();

        int diaH = hoje.get(Calendar.DAY_OF_MONTH);
        int mesH = hoje.get(Calendar.MONTH) + 1;
        int anoH = hoje.get(Calendar.YEAR);

        if (mesH > mes || (mesH == mes && diaH > dia)) {
            return anoH - ano;
        } else {
            return anoH - ano - 1;
        }
    }

    public static void cabecalho() {
        System.out.printf("%-6s||%-30s||%-10s||%-12s%n", "ID", "NOME", "PARTIDO", "DATA NASC");
        System.out.println("================================...========================");
    }

    public static void pausa() {
        System.out.println("\n\nPara continuar prima ENTER\n");
        SAVOP.sc.nextLine();
    }
    
}
