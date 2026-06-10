#include <stdio.h>

int main(void) {
    int a = 5;      // variável com valor 5
    int *b = &a;    // b aponta para a

    printf("Endereço de a: %p\n", b);

    printf("Valor via ponteiro b: %d\n", *b);

    return 0;
}
