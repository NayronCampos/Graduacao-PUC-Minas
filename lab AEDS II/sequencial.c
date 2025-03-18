#include <stdio.h>
int main (void){

    int n,x, count =0;
    scanf("%d", &n);
    int array[n];
    for(int i=0;i<n;i++){
        scanf("%d", &array[i]);
    }

    printf("Number of question:");
    scanf("%d", &x);

    for(int i=0;i<n;i++){
        if(array[i]==x){
            printf("Yes, stay in vetor");
        }

        count++;
    }
    printf("\n%d", count);


}