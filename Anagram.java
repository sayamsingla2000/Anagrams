
import java.util.*;
import java.io.*;


public class Anagram {

    public static int hashSize = 151;
    static final int SIZE = 37;
    public static Vector<String>[] wordsArray = new Vector[hashSize];
    public static Vector<ArrayList>[] wordsArraynC2 ;
    public static Vector<String > v = new Vector();
    public static Vector<String > output = new Vector();
    public static int doubleHashSize = 122*122*9 ;

    static class TrieNode
    {
        TrieNode[] Child = new TrieNode[SIZE];
        int leaf =0;
        int ref = 0;
       
        public TrieNode() {

            for (int i =0 ; i< SIZE ; i++)
                Child[i] = null;
        }
    }

    public static void insert(TrieNode root, String Key)
    {
        int n = Key.length();
        TrieNode parent = root;

        for (int i=0; i<n; i++)
        {
            int index = 0;

            if(Key.charAt(i)==39)
                index = 0;
            else if(Key.charAt(i) >= 48 && Key.charAt(i) <= 57)
                index = Key.charAt(i) -'a' + 50;
            else if(Key.charAt(i) >= 97 && Key.charAt(i) <= 122)
                index = Key.charAt(i) - 'a' + 11;

            if (parent.Child[index] == null)
                parent.Child[index] = new TrieNode();

            parent = parent.Child[index];
        }
        parent.leaf++;
        parent.ref++;
    }

    public static String searchWord(TrieNode root, int[] Hash,
                                    String str)
    {

        while (root.leaf > 0){
            hashFunction(str);
            root.leaf--;
        }
        root.leaf = root.ref ;

        for (int K = 0; K < SIZE; K++)
        {

            if ( Hash[K] > 0   && root.Child[K] != null )
            {
                Hash[K]--;

                char c = 0;
                if(K== 0)
                    c = (char) (K + 39);
                else if(K>=1 && K<=10)
                    c = (char) (K + 'a' - 50);
                else if(K>=11 && K<=36)
                    c = (char) (K + 'a' - 11);

                searchWord(root.Child[K], Hash, str + c);
                Hash[K]++;
            }
        }
        return str;
    }


    public static Vector<String>[] getAllWords(char[] Arr, TrieNode root, int n)
    {
        int[] Hash = new int[SIZE];
        int a = 0;

        for (int i = 0 ; i < n; i++){

            if(Arr[i]==39)
                a = 0;
            else if(Arr[i] >= 48 && Arr[i] <= 57)
                a = Arr[i] -'a' + 50;
            else if(Arr[i] >= 97 && Arr[i] <= 122)
                a = Arr[i] -'a' + 11 ;

            Hash[a]++;
        }
        TrieNode pChild = root ;
        String str = "" ;
        for (int i = 0 ; i < SIZE ; i++)
        {
            if (Hash[i] > 0 && pChild.Child[i] != null )
            {
                Hash[i]--;

                if(i== 0)
                    str = str + (char) (i + 39);
                else if(i>=1 && i<=10)
                    str = str + (char) (i + 'a' - 50);
                else if(i>=11 && i<=36)
                    str = str + (char) (i + 'a' - 11);

                searchWord(pChild.Child[i], Hash, str);
                Hash[i]++;
                str = "";
            }
        }
  return wordsArray;
    }

    public static Vector<String>[] hashFunction(String str){
        int r = 0;
        for (int i =0 ; i < str.length() ; i++){
            r += str.charAt(i);
        }
        r = r % hashSize;
        if(wordsArray[r] == null){
            wordsArray[r] = new Vector<>();
            wordsArray[r].add(str);
            v.add(str);
        }
        else {
            wordsArray[r].add(str);
            v.add(str);
        }
        return wordsArray;
    }

    public static Vector<ArrayList>[] hash2(){

        wordsArraynC2 = new Vector[doubleHashSize];

        for (int i=0 ; i < v.size() ; i++){
            for (int j = 0 ; j < v.size() ; j++){
                String temp1 = v.get(i);
                String temp2 = v.get(j);
                String temp3 = temp1 + temp2;

                int r = 0;
                for (int a =0 ; a < temp3.length() ; a++){
                    //r += temp3.charAt(a);
                    r += (temp3.charAt(a) * temp3.charAt(a));
                }
                r = r % doubleHashSize;

                if(wordsArraynC2[r] == null){
                wordsArraynC2[r] = new Vector<ArrayList>();
                ArrayList<String> arr = new ArrayList<>();
                arr.add(temp1);
                arr.add(temp2);
                wordsArraynC2[r].add(arr);
                }
                else{
                    ArrayList<String> arr = new ArrayList<>();
                    arr.add(temp1);
                    arr.add(temp2);
                    wordsArraynC2[r].add(arr);
                }

            }
        }
    return wordsArraynC2;
    }

    public static int compareString(String str1 , String str2){
        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }
        if (l1 != l2) {
            return l1 - l2;
        }
        else {
            return 0;
        }

    }

    public static String sortString(String str){
        char []arr = str.toCharArray();
        Arrays.sort(arr);


        return String.valueOf(arr);
    }

    public static int sumString(String str){
        int sum = 0;
        for(int i=0 ; i < str.length() ; i++){
            sum += str.charAt(i);
        }
        return sum ;
    }
    public static int squareString(String str){
        int sum = 0;
        for(int i=0 ; i < str.length() ; i++){
            sum += (str.charAt(i) * str.charAt(i));
        }
        return sum ;
    }

    public static void checker(String str){


        String a = sortString(str);
        int s = sumString(str);
        int s2 = squareString(str);
        if(str.length() > 8){
            hash2();
        }

        for(int i = 0 ; i < v.size(); i++){

            if(str.length() == v.get(i).length()){
                int sum = sumString(str) % hashSize;
                String b = wordsArray[sum].get(0);
                wordsArray[sum].remove(0);
                output.add(b);

            }
            if(v.get(i).length() < str.length()) {
                int v1 = sumString(v.get(i));
                int v2 = squareString(v.get(i));
                int index = (s - v1) % hashSize;
                int index2 = (s2 - v2) % doubleHashSize;

                if(str.length() > 5){
                Vector<String> vec = wordsArray[index];
                if (vec != null) {
                    for (int j = 0; j < vec.size(); j++) {
                        String temp = vec.get(j);
                        String temp1 = sortString(v.get(i) + temp);
                        if (compareString(a, temp1) == 0) {
                            output.add(v.get(i) + " " + temp);
                        }
                    }
                }
                }

                if(str.length() > 8){
                    if(wordsArraynC2[index2] != null) {
                    Vector<ArrayList> vec2 = wordsArraynC2[index2];
                    for (int k = 0; k < vec2.size(); k++) {

                        ArrayList arr = vec2.get(k);
                        String temp = sortString(v.get(i) + arr.get(0) + arr.get(1));

                        if (compareString(a, temp) == 0) {
                            output.add(v.get(i) + " " + arr.get(0) + " " + arr.get(1));
                        }
                    }
                }

                }
            }
        }
    }

    public static void main(String args[])
    {
        try {

          
            TrieNode root = new TrieNode();


            File fstream = new File(args[0]);
            //File fstream = new File("/Users/sayamsingla/Downloads/vocabulary.txt");
            BufferedReader br = new BufferedReader(new FileReader(fstream));
            String noOfVoc = br.readLine();
            String str ;
            while((str = br.readLine()) != null){
                insert(root, str);
            }
            br.close();




            File fstream1 = new File(args[1]);
            //File fstream1 = new File("/Users/sayamsingla/Downloads/input.txt");
            BufferedReader j = new BufferedReader(new FileReader(fstream1));

            String noOfInput = j.readLine();
            String input;

            while((input = j.readLine()) != null){
                char []arr = input.toCharArray();
                int N = arr.length;
                getAllWords(arr, root, N);
               
                checker(input);
                Collections.sort(output);

                for(int k=0; k< output.size(); k++){
                    System.out.println(output.get(k));
                }
                System.out.println(-1);

                wordsArray = new Vector[hashSize];
                v = new Vector();
                output = new Vector();
            }
            j.close();


            
       }

        catch(FileNotFoundException e) { } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
