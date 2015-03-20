#include<stdio.h>
#include<math.h>


int main(int argc,char* argv[])
{
    char inputArray[10000];
    FILE *filePointer;
    char *ptr, *ptr1;
    int i=0,j=0,k=0,p;
    int tc=0,tt;
    int input1,input2,input3;
    long outsourchedArray[1000];
    long  max;
    long  ans;
    filePointer = fopen("input.in", "r"); 
    // Scan no of test cases.
    if(NULL != filePointer)
    {
        fgets(inputArray, 80, filePointer);      
        sscanf(inputArray,"%d",&tc);
    }
    for(i=0;i<tc;i++)
    {
        // Scan all lines.
        fgets(inputArray,80,filePointer);
        sscanf(inputArray,"%d %d %d",&input1 ,&input2 , &input3);
        fgets(inputArray,10000,filePointer);
        ptr = inputArray;
        sscanf(inputArray,"%ld",&outsourchedArray[0]);
       // printf("%inputArray \n",inputArray);
        for(j=1;j<input3;j++)
          {
            ptr1 = strstr(ptr," ");
            sscanf(ptr1," %ld",&outsourchedArray[j]);
            ptr1++;
            ptr = ptr1;             
          }
         
          max=outsourchedArray[0];
           for(j=0;j<input3;j++)
          {
             for(k=j+1;k<input3;k++)                  
             {
                if(outsourchedArray[j] < outsourchedArray[k])
                {
                  max = outsourchedArray[j];
                  outsourchedArray[j] = outsourchedArray[k];
                  outsourchedArray[k] = max;    
                }                                        
             }                             
          }

        //  for(j=0;j<input3;j++)
          //{
            //printf(" %ld ",outsourchedArray[j]);
         // } 
         // printf("\n");
          ans = 0;
         /* for(j=0;j<=input3;j++)
          {
             ans  = ans+ (abs(j/input2)+1) * outsourchedArray[j];
          }*/
          p=0;
          for(j=1;j<=input1;j++)
          {
             for(k=0;k<input2;k++)
             {
                ans = ans + (j*outsourchedArray[p++]);
                if(p==input3)
                  break;
             }
             if( p==input3)
               break;
          }
        printf("Case #%d: %ld\n",i+1,ans);
    }
    return 1;
}
     
