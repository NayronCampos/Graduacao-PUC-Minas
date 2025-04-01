#include <stdio.h>
int main (void){

    int a, b, c, d, maior=0;
    scanf("%d %d %d %d", &a, &b, &c, &d);
    
    if(a>b && a>c && a>d){maior=a; 
    printf("%d %d %d\n", maior-b, maior-c, maior-d ); }
    else if(b>a && b>c && b>d){maior=b;
    printf("%d %d %d\n", maior-a, maior-c, maior-d); }
    else if(c>a && c>b && c>d){maior=c;
    printf("%d %d %d\n", maior-a, maior-b, maior-d); }
    else if(d>a && d>b && d>c){maior=d;
    printf("%d %d %d\n", maior-a, maior-b, maior-c ); }




    return 0;
}