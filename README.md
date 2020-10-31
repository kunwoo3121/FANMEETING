# FANMEETING

https://algospot.com/judge/problem/read/FANMEETING

# 구현 방법
```
  i)   M = 1, F = 0으로 치환하여 팬과 멤버 사이에 비트 연산을 취한다. 남자/남자의 조합이 하나라도 있을 경우 AND 연산의 값이 0이 나오지 않는다.
  
  ii)  최대 입력 인원 수가 20만명이므로 int 변수 하나로는 부족하다.
  
  iii) int 변수는 32비트를 표현할 수 있지만 최상위 비트는 부호 비트이므로 int 변수 하나의 31비트만 활용한다. 20만 / 31 = 6451.6.... -> 6500개의 방을 가진 int 배열을 활용
  
  iv)  팬이 왼쪽으로 이동하는 것은 멤버들이 오른쪽으로 이동하는 것과 같다.
  
  v)   멤버 배열을 오른쪽으로 shift하면서 제일 오른쪽 비트가 1일 경우 int 배열의 다음 방의 최상위 비트를 1 더해준다.
  
  vi)  멤버 수와 팬들의 수의 차이만큼 shift를 반복하면서 모든 멤버가 포옹을 하는 경우를 체크한다.
 ```
 
# 구현 코드
```java
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
```
