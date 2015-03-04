package macierze;
import java.util.Scanner;
// gdhfghgfhf
 
public class Macierze {
 
    public static Scanner in = new Scanner(System.in);
    public static int[][] memorised;
    public static int[][] best_result_index;
    public static String[] substring;
    public static void main(String[] args) {
       
        // ilosc macierzy
        System.out.println("Podaj ilosc macierzy: ");
        int matrixNumber = in.nextInt();
       
        int[] matrix = new int[matrixNumber+1];
       
        // 2x5x6 ...
        System.out.println("Podaj kolejne wymiary: ");
        System.out.println("Podaj 1 wymiar 1 macierzy");
        matrix[0] = in.nextInt();
        for(int i =1; i <= matrixNumber; i++){
            System.out.println("Podaj 2 wymiar "+ i + " macierzy");
            matrix[i] = in.nextInt();
        }
       
               substring = new String[matrix.length-1];
       
       
        for(int i =0; i < substring.length; i++){
            substring[i] = "";
        }
       
        matrixChainOrder(matrix);
       
        boolean[] inAResult = new boolean[best_result_index.length];
        printOptimalParenthesizations(best_result_index, 0, best_result_index.length - 1, inAResult);
       
       
       
        System.out.println(memorised[0][memorised.length-1]);
        System.out.println(substring[0]);
    }
   
   
 
    public static void matrixChainOrder(int[] matrix){
        int n = matrix.length-1;
        memorised = new int[n][n];
        best_result_index = new int[n][n];
       
        //zeruje przekątną
        for(int i=0; i < n; i++){
            memorised[i][i] = 0;
        }
       
        // wypełnianie macierzy kosztków
        for(int ii = 1; ii < n; ii++){
            for(int i =0; i < n - ii; i++){
                int j = i+ii;
                memorised[i][j] = Integer.MAX_VALUE;
               
                    for(int k =i;  k < j; k++){
                    int q = memorised[i][k]+ memorised[k+1][j] + matrix[i]*matrix[k+1]*matrix[j+1];
                    //jeśli znalezlismy lepsze rozwiazanie to nadpisujemy i zapamietujemy indeks podmiany
                    if(q < memorised[i][j]){
                        memorised[i][j] = q;
                        best_result_index[i][j] = k; // najlepszy indeks
                    }
                }
            }
        }
    }
   
    public static void printOptimalParenthesizations(int[][]best_results, int left, int right,  boolean[] inAResult) {
        if (left != right) {
            // rekurencyjnie dziele na lewe i prawe podłancuchy(ze skryptem)
            // warunek 2 macierze obok siebie
            // dziele wzgledem best resultss
            printOptimalParenthesizations(best_results, left, best_results[left][right], inAResult);
            printOptimalParenthesizations(best_results, best_results[left][right] + 1, right, inAResult);
           
           
            if(!inAResult[left]){ //nie wzielismy jeszcze i-tej macierzy
                substring[left] = "A"+(left+1);
            }
            if(!inAResult[right]){//nie wzielismy jeszcze j-tej macierzy
                substring[right] = "A"+(right+1);
            }
            if( inAResult[left] == false && inAResult[right] == false){ //nie wzielismy jeszcze zadnej z dwoch
                substring[left] +="A"+(right+1);
                substring[right] = substring[left];
            }
            else if( inAResult[left] == true && inAResult[right] == false){//z lewej wzieta, z prawej nie
                substring[left] = "("+substring[left]+")"+"A"+(right+1);
                substring[right] = substring[left];
            }
            else if( inAResult[left] == false && inAResult[right] == true){//z prawej wzieta, z lewej nie
                substring[left] = "A"+(left+1)+"("+substring[right]+")";
                substring[right] = substring[left];
            }
            else if(inAResult[left] == true && inAResult[right] == true){//z obu stron wziete
                substring[left] = "("+substring[left]+")("+substring[right]+")";
                substring[right] = substring[left];
            }
            inAResult[left] = true;
            inAResult[right] = true;
        }
    }
}
