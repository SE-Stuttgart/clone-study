#include<stdio.h>
int main()
{
    
    FILE *fileOpen;
    FILE *fileOutout;
    fileOpen=fopen("A-small-attempt1.in","r");
    fileOutout=fopen(" Freecell.out","w");
    int i,j,test_case,n,pd,pg,flag=0;
    long int intermedErg,b;

    fscanf(fileOpen,"%d\n",&test_case);
//printf("%d ",test_case);
    for(i=0;i<test_case;i++)
    {
        flag=0;
        fscanf(fileOpen,"%d %d %d\n",&n,&pd,&pg);
        for(j=1;j<=n;j++)
        {
            intermedErg=j*pd;
            if(intermedErg%100==0)
            {
                flag=1;
                break;
            }    
        }
        if(flag==1 )        
        {
            if(pg==100 && pd<pg)
            {
                fprintf(fileOutout,"Case #%d: Broken\n",i+1) ;
            }   
            else if(pg==0 && pd>0)
            {
                fprintf(fileOutout,"Case #%d: Broken\n",i+1) ;
            }    
            else if(pd==0 && pg==100)
            {
                fprintf(fileOutout,"Case #%d: Broken\n",i+1) ;
            }    
            else 
            {
                fprintf(fileOutout,"Case #%d: Possible\n",i+1) ;
            }    
            
        }   
        else
        fprintf(fileOutout,"Case #%d: Broken\n",i+1) ;
	
	
     }

    fclose(fileOpen);
   fclose(fileOutout);
return(0);       
}    
