#include <stdio.h>

int main() {
    float p1, p2, e, m, a1, a2, ada;
    scanf("%f %f %f %f %f %f %f", &p1, &p2, &e, &m, &a1, &a2, &ada);
    float S = 2.5 * p1 + 2.5 * p2 + 2 * e + 1.5 * m + 1.5 * a1 + 1.5 * a2 + ada;
    float p3_min = (900 - S) / 2.5;
    if(p3_min < 0){
        p3_min = 0;}    
    printf("%.2f", p3_min);
    return 0;
}
