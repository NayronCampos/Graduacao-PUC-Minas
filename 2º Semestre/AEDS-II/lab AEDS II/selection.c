#include <stdio.h>
void swap(int *a, int *b) { 
    int temp = *a;
    *a = *b;
    *b = temp;
}

int main (void){

    int n;
    scanf("%d", &n);
    int array[n];
    for(int i=0;i<n;i++){
        scanf("%d", &array[i]);
    }
    for(int j=0;j<n;j++){
        printf("%d ", array[j]);
    }

    int menor;
    for(int i=0;i<n-1;i++){
        menor=i;

        for(int j=i+1;j<n;j++){
            if(array[j]<array[menor]){
                menor =j;}
        }
        swap(&array[i], &array[menor]);

    }
        for(int j=0;j<n;j++){
    printf("\n%d ", array[j]);
}
}