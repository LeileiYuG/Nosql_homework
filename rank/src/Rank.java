import java.util.Arrays;

public class Rank {
    //桶排序
    public static String[] reRank(String[] input) {
        int r=0,g=0,b=0;
        for(String s:input)
        {
            if(s.equals("r"))
                r++;
            else if(s.equals("g"))
                g++;
            else if(s.equals("b"))
                b++;
        }
        String[] temp=new String[input.length];
        for(int i=0;i<r;i++)
            temp[i]="r";
        for(int i=r;i<(r+g);i++)
            temp[i]="g";
        for(int i=(r+g);i<input.length;i++)
            temp[i]="b";

        return temp;
    }
    //双指针
    public static String[] reRankTwoPointers(String[] input)
    {
        if(input==null||input.length==0)
        {
            return new String[0];//处理异常
        }
        int i=0,left=0,right=input.length-1;
        while(i<=right)
        {
            if(input[i].equals("r")) {
                String t1=input[i];
                input[i]=input[left];
                input[left]=t1;
                i++;
                left++;
            }
            else if(input[i].equals("g")) {
                i++;
            }
            else if(input[i].equals("b")) {
                String t2=input[i];
                input[i]=input[right];
                input[right]=t2;
                right--;
            }
        }
        return input;
    }
    public static void main(String[] args) {
        String[] input=new String[] {"r","r","b","g","b","r","g"};
        String[] str=reRankTwoPointers(input);
        for(int i=0;i<input.length;i++)
        {
            System.out.print(str[i]);
        }
        System.out.println("");
        String[] str2=reRank(input);
        for(int i=0;i<input.length;i++)
        {
            System.out.print(str2[i]);
        }
    }
}

