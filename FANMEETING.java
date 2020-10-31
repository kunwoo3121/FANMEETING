import java.util.Scanner;

public class FANMEETING {
	
	static int[] idol = new int[6500];
	static int[] fan = new int[6500];
	
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		
		int c, idol_num, fan_num, count, fn, in;
			
		c = sc.nextInt();
		
		for(int i = 0; i < c; i++)
		{
			String s;
			
			s = sc.next();
			idol_num = take_input_idol(s);
			in = s.length();
			
			s = sc.next();
			fan_num = take_input_fan(s, idol_num);
			fn = s.length();
			
			count = count_hug(fan_num, idol_num, fn, in);
			
			System.out.println(count);
		}
		
	}
	
	public static int take_input_idol(String s)
	{
		int j = 0, k = 1 << 30, fg = 0;
		idol[0] = 0;
		
		for(int tmp = 0; tmp < s.length(); tmp++)
		{
			if(tmp != 0 && tmp % 31 == 0)
			{
				j++;
				idol[j] = 0;
				k = 1 << 30;
				fg = 0;
			}
			else if(fg != 0)
			{
				k >>= 1;
			}
			
			if(s.charAt(tmp) == 'M')
			{
				idol[j]+=k;
			}
			
			fg = 1;
		}
		return j;
	}
	
	public static int take_input_fan(String s, int idol_num)
	{
		int j = 0, k = 1 << 30, fg = 0 ;
		fan[0] = 0;
		
		for(int tmp = 0; tmp < s.length(); tmp++)
		{
			if(tmp != 0 && tmp % 31 == 0)
			{
				j++;
				fan[j] = 0;
				k = 1 << 30;
				fg = 0;
				
				if(j > idol_num) idol[j] = 0; 
			}
			else if(fg != 0)
			{
				k >>=  1;
			}
			
			if(s.charAt(tmp) == 'M')
			{
				fan[j]+=k;
			}
			
			fg = 1;
		}
		return j;
	}
	
	public static int idol_shift(int n)
	{
		int k = 1 << 30;
		int rn = n;
		
		for(int i = n; i >= 0; i--)
		{
			if((idol[i] & 1) != 0)
			{
				idol[i + 1] += k;
				if(i == n) rn++;
			}
			
			idol[i] >>= 1;
		}
		
		return rn;
		
	}
	
	public static int checkhug(int n)
	{
		for(int i = 0; i <= n; i++)
		{
			if((idol[i] & fan[i]) != 0) return 0;
		}
		
		return 1;
	}
	
	public static int count_hug(int fan_num, int idol_num, int fn, int in)
	{
		int hug = 0;
		
		for(int i = 0; i < fn - in + 1; i++)
		{
			hug += checkhug(idol_num);
			idol_num = idol_shift(idol_num);
		}
		
		return hug;
	}
	
}