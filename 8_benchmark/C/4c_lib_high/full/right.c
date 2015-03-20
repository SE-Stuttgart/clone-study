#include <stdio.h>

int main(){

   int t, i, input1, input2, j, k;
   float scanInput, exp, aux;
   
   scanf("%d", &t);
   
   for(i = 0; i < t; i++){
      scanf("%d %d", &input1, &input2);
      
      float prob[input1 + 1];
      
      for(j = 0; j < input1 + 1; j++){
         prob[j] = 1;
      }
      
      for(j = 0; j < input1; j++){
         scanf("%f", &scanInput);
         
         for(k = 0; k < input1; k++){
            if(k < (input1 - j)){
               prob[k] = prob[k]*scanInput;
            } else{
               prob[k] = prob[k]*(1 - scanInput);
            }
         }
      }
      
      
      exp = input2 + 2;
      
    
      for (j = 0; j < input1; j++){
         aux = (prob[j] * (2*j + input2 - input1 + 1)) + ((1 - prob[j]) * (2*j + 2*input2 - input1 + 2));
         prob[j + 1] = prob[j + 1] + prob[j];
         if(aux < exp){
            exp = aux;
         }
      }
      aux = input1 + input2 + 1;
      if(aux < exp){
         exp = aux;
      }
      
      printf("Case #%d: %.6f\n", i+1, exp);
      
   }

   return 0;
}
