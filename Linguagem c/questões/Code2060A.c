/* Quando i==1, ai+2 = ai + ai+1
i=1 -> a3 = a1+a2
i=2 -> a4 = a2+a3
i=3 -> a5 = a3+a4
*/

#include <stdio.h>

int main (void){

    int i;
    scanf("%d", &i);

    while(i--){
    int a1, a2, a4, a5, x1,x2,x3;
    scanf("%d %d %d %d", &a1, &a2, &a4, &a5);

    x1 = a1+a2;
    x2 = a4-a2;
    x3 = a5-a4;

    if (x1 == x2 && x2 == x3) {
        printf("3\n");
    } else if (x1 == x2 || x1 == x3 || x2 == x3) {
        printf("2\n");
    } else {
        printf("1\n");
    }

}


    return 0;
}