public class lista{

    void inserirInicio (int x) throws Exception{

        if(n >= array.length){throw new Exception("Erro!");}
    
    for (int i=n; i>0; i--){
        array[i]=array[i-1];
    }
    array[0]=x;
    n++;
    }

    void inserirFinal(int x)throws Exception{

        if(n >= array.length){throw new Exception("Erro!");}

        array[n]=x;
        n++;
    }

    void inserirPosicao(int x, int pos) throws Exception{

        if(n >= array.length || pos<0 || pos>n){throw new Exception("Erro!");}
        
        for(int i=0; i>pos; i--){
            array[i]=array[i-1];
        }
        array[pos]=x;
        n++;
    }

    int removerInicio()throws Exception{

        if(n =0){throw new Exception("Erro!");}
        int resp=array[0];
        n--;

        for(int i=0;i<n;i++){
            array[i]=array[i+1];
        }
        return resp;


    public static void main (String[] args){
        System.out.println("====LISTA LINEAR====");

        int n;
        int array[];
        int array = new int[6];

        
        array.inserirInicio(1);
        array.inserirFinal(4);
        array.inserirFinal(6);
        array.inserirPosicao(12, 4);
    }
}