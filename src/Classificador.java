import java.io.*;
import java.util.*;

/**
 * Created by Eduardo on 12/06/2016.
 */
public class Classificador {

    public static List<String> dadosFilme = new ArrayList<>();
    public static List<String> dadosTeste = new ArrayList<>();
    public static List<Filme> dadosTreinamento = new ArrayList<>();
    public static List<Integer> rotulos = new ArrayList<>();
    public static List<Integer> rotulosTeste = new ArrayList<>();
    public static Map<Integer, String> persons = new HashMap<>();
    public static int contKey = 1;


    /**
     *
     * @param name
     */
    public static void addPerson(String name){
        // Verificar se o ator ou diretor já está no Map
        if(!persons.containsValue(name)){
            persons.put(contKey++, name);
        }
    }

    /**
     * Retorna a chave de um person do Map. Caso não exista retorna 0.
     * @param name
     * @return
     */
    public static int getKeyPerson(String name){

        for(Map.Entry<Integer, String> person: persons.entrySet()){
            if (person.getValue().equalsIgnoreCase(name)){
                return person.getKey();
            }
        }
        return 0;
    }

    public static double calcularDistancia(Filme filmeX, String filmeY){
        String[] filme = filmeY.split(",");
        double ator1XY = Math.pow((filmeX.getAtor1() - getKeyPerson(filme[0])),2);
        double ator2XY = Math.pow((filmeX.getAtor2() - getKeyPerson(filme[1])),2);
        double ator3XY = Math.pow((filmeX.getAtor3() - getKeyPerson(filme[2])),2);
        double ator4XY = Math.pow((filmeX.getAtor4() - getKeyPerson(filme[3])),2);
        double diretorXY = Math.pow((filmeX.getDiretor() - getKeyPerson(filme[4])),2);

        return Math.sqrt(ator1XY + ator2XY + ator3XY + ator4XY + diretorXY);
    }

    public static void classificar(String filmeY, int k){

        List<Integer> vizinhos;
        List<Vizinho> listAux = new ArrayList<>();

        for (int i = 0; i < dadosTreinamento.size(); i++) {
            double distancia = calcularDistancia(dadosTreinamento.get(i), filmeY);
            listAux.add(new Vizinho(dadosTreinamento.get(i).getNota(), distancia));
        }

        vizinhos = getVizinhos(listAux, k); // Obtém os vizinhos da flor
        int notaFilme = getRotulo(vizinhos); // Obtém o rótulo que mais se repete
        rotulos.add(notaFilme);
    }

    public static List<Integer> getVizinhos(List<Vizinho> listV, int k){
        List<Integer> arrayAux = new ArrayList<>();

        Collections.sort(listV);
        for(int i = 0; i < k; i++){
            arrayAux.add(listV.get(i).getRotulo());
        }

        return arrayAux;
    }


    public static int getRotulo(List<Integer> vizinhos){
        /*
        int contRot1 = 0;
        int contRot2 = 0;
        int contRot3 = 0;
        int contRot4 = 0;
        int contRot5 = 0;
        for (int i = 0; i < vizinhos.size(); i++){
            int rotulo = vizinhos.get(i);
            if(rotulo == 1){
                contRot1++;
            }else if(rotulo == 2){
                contRot2++;
            }else if(rotulo == 3){
                contRot3++;
            }else if(rotulo == 4){
                contRot4++;
            }else if(rotulo == 5){
                contRot5++;
            }
        }

        if(contRot1 > contRot2 && contRot1 > contRot3){
            return 1;
        } else if(contRot1 > contRot0 && contRot1 > contRot2){
            return 1;
        } else{
            return 2;
        }
        */
        return 0;
    }


    /**
     *  Lê os dados de um arquivo com os dados dos atores na lista dadosFilmes
     *  Que deverá ser gravado como o método dados treinamento
     * @param nomeDoArquivo
     * @throws IOException
     */
    public static void carregarDadosFilme(String nomeDoArquivo) throws IOException {
        BufferedReader leitor = null;
        try{
            leitor = new BufferedReader(new FileReader(nomeDoArquivo));

            String linha = null;
            //linha = leitor.readLine();
            do{
                linha = leitor.readLine();
                if(linha != null) {
                    System.out.println(linha);
                    dadosFilme.add(linha);

                    String[] linhaSplit = linha.split(",");
                    // length-1 para não obter o rótulo
                    for(int i = 0; i < linhaSplit.length-1; i++){
                        addPerson(linhaSplit[i]); // Adiciona no Map
                    }
                }

            } while(linha != null);

        } finally {
            if(leitor != null){
                leitor.close();
            }
        }
    }

    /**
     *
     * @param nomeDoArquivo
     * @throws IOException
     */
    public static void carregarDadosTreinamento(String nomeDoArquivo) throws IOException {
        BufferedReader leitor = null;
        try{
            leitor = new BufferedReader(new FileReader(nomeDoArquivo));
            String linha = null;
            do{
                linha = leitor.readLine();
                if(linha != null) {

                    String[] dados = linha.split(",");
                    //System.out.println(dados[0] + " " + dados[1] + " " + dados[2] + " " + dados[3] + " " + dados[4]);
                    int diretor = Integer.parseInt(dados[0]);
                    int ator1 = Integer.parseInt(dados[1]);
                    int ator2 = Integer.parseInt(dados[2]);
                    int ator3 = Integer.parseInt(dados[3]);
                    int ator4 = Integer.parseInt(dados[4]);
                    int nota = Integer.parseInt(dados[5]);
                    dadosTreinamento.add(new Filme(diretor, ator1, ator2, ator3, ator4, nota));
                }
            } while(linha != null);

        } finally {
            if(leitor != null){
                leitor.close();
            }
        }
    }


    /**
     *  Carrega os dados de teste para classificar
     * @param nomeDoArquivo
     * @throws IOException
     */
    public static void carregarDadosTeste(String nomeDoArquivo) throws IOException {
        BufferedReader leitor = null;
        try{
            leitor = new BufferedReader(new FileReader(nomeDoArquivo));
            String linha = null;
            do{
                linha = leitor.readLine();
                if(linha != null) {
                    dadosTeste.add(linha);
                }
            } while(linha != null);

        } finally {
            if(leitor != null){
                leitor.close();
            }
        }
    }


    /**
     *  Grava os dados de Treinamento lidos a partir do arquivo com os nomes do elenco e diretor
     * @param nomeArquivo
     * @throws IOException
     */
    public static void gravarDadosTreinamento(String nomeArquivo) throws IOException {
        BufferedWriter gravador = null;
        try{
            gravador = new BufferedWriter(new FileWriter(nomeArquivo));
            for(String str: dadosFilme){
                String linha = "";
                String[] strSplit = str.split(",");
                System.out.println(linha);
                for(int i = 0; i < strSplit.length-1; i++){
                    linha += getKeyPerson(strSplit[i]) + ",";
                }
                String rotulo = strSplit[strSplit.length-1];
                linha += rotulo;
                System.out.println(linha);
                gravador.write(linha+"\n");
            }
        } finally {
            if (gravador != null){
                gravador.close();
            }
        }
    }

//    public static void main(String[] args) throws IOException {
//
//        //System.out.println(conversorToBinary("Harrison Ford"));
//        carregarDadosFilme("dadosTeste.txt");
//        gravarDadosTeste("treinamento.txt");
//    }

    public static void gravarDadosRotulos(List<Integer> rotulos, String nomeArquivo) throws IOException {
        BufferedWriter gravador = null;
        try{
            gravador = new BufferedWriter(new FileWriter(nomeArquivo));
            for(Integer i: rotulos){
                gravador.write(i+"\n");
            }
        } finally {
            if (gravador != null){
                gravador.close();
            }
        }
    }

    public static void main(String[] args) {
        try {

            // Carregar dados do arquivo com o nome dos atores e diretor
            carregarDadosFilme("dadosFilmes.txt");

            // Grava um arquivo com os dados dos filmes convertidos em números
            gravarDadosTreinamento("treinamento.txt");

            // Carregar dados de aprendizagem
            carregarDadosTreinamento("treinamento.txt");

            // Arquivo para carregar os dados de teste para passar os elementos no classificar()
            carregarDadosTeste("dadosTeste.txt");

            /*
            int k = 50;

            for (int i = 0; i < dadosTeste.size(); i++) {
                classificar(dadosTeste.get(i), k);
            }
            System.out.println(dadosTeste.size());
            gravarDadosRotulos(rotulos, "rotulosClassificados.txt");
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    }
