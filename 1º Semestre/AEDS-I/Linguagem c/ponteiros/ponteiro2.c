#include <stdio.h>
#include <stdbool.h>

bool palindromo_rec(char *p, int esq, int dir){
    if(esq >= dir){
        return true;
    }
    if(p[esq] != p[dir]){
        return false;
    }
    return palindromo_rec(p, esq+1, dir-1);
}

int main (void){
    char palavra[10];
    int i = 0;
    
    scanf("%c", &palavra[i]);
    while(palavra[i] != '\n' && i < 9){
        i++;
        scanf("%c", &palavra[i]);
    }
    palavra[i] = '\0';
    
    printf("%d", palindromo_rec(palavra, 0, i-1));
    
    return 0;
}
