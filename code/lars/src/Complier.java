package Complier;

public class Complier {
	
	private String Op;
	private String InsOutput;
	
	public Boolean Complie(String Ins) {
		/*get opcode and choose Ins type*/
		Op = "";
		int i;
		for(i=0;Ins.charAt(i)!=' ';i++)
		{
			Op+=Ins.charAt(i);
		}
		
		switch (Op) {
		case "add.w":
			InsOutput="00000000000100000"+TRtype(Ins,i);
			break;
		case "sub.w":
			InsOutput="00000000000100010"+TRtype(Ins,i);
			break;
		case "mul.w":
			InsOutput="00000000000111000"+TRtype(Ins,i);
			break;
		case "mulh.w":
			InsOutput="00000000000111001"+TRtype(Ins,i);
			break;
		case "div.w":
			InsOutput="00000000001000000"+TRtype(Ins,i);
			break;
		case "div.wu":
			InsOutput="00000000001000010"+TRtype(Ins,i);
			break;
		case "mod.w":
			InsOutput="00000000001000001"+TRtype(Ins,i);
			break;
		case "mod.wu":
			InsOutput="00000000001000011"+TRtype(Ins,i);
			break;
		case "slt.w":
			InsOutput="00000000000100100"+TRtype(Ins,i);
			break;
		case "sltu.w":
			InsOutput="00000000000100101"+TRtype(Ins,i);
			break;
		case "and":
			InsOutput="00000000000101001"+TRtype(Ins,i);
			break;
		case "or":
			InsOutput="00000000000101010"+TRtype(Ins,i);
			break;
		case "nor":
			InsOutput="00000000000101000"+TRtype(Ins,i);
			break;
		case "xor":
			InsOutput="00000000000101011"+TRtype(Ins,i);
			break;
		case "sll.w":
			InsOutput="00000000000101110"+TRtype(Ins,i);
			break;
		case "srl.w":
			InsOutput="00000000000101111"+TRtype(Ins,i);
			break;
		case "slli.w":
			InsOutput="00000000010000001"+TRtype(Ins,i);
			break;
		case "srli.w":
			InsOutput="00000000010001001"+TRtype(Ins,i);
			break;
		case "andi":
			InsOutput="0000001101"+TRUI12type(Ins,i);
			break;
		case "ori":
			InsOutput="0000001110"+TRUI12type(Ins,i);
			break;
		case "xori":
			InsOutput="0000001111"+TRUI12type(Ins,i);
			break;
		case "beq":
			InsOutput="010110"+SRI16type1(Ins,i);
			break;
		case "bne":
			InsOutput="010111"+SRI16type1(Ins,i);
			break;
		case "jirl":
			InsOutput="010011"+SRI16type2(Ins,i);
			break;
		case "addi.w":
			InsOutput="0000001010"+TRSI12type(Ins,i);
			break;
		case "ld.w":
			InsOutput="0010100010"+TRSI12type(Ins,i);
			break;
		case "st.w":
			InsOutput="0010100110"+TRSI12type(Ins,i);
			break;
		default :
			return false;
		}
		return true;
	}
	/*following code are get the rest of code member*/
	private String TRtype(String Ins,int S)
	{
		
		String rd="";String rj="";String rk = "";
		
		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(Ins.charAt(S)!=' '&&Ins.charAt(S)!=',')
		{rd += Ins.charAt(S);S++;}

		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(Ins.charAt(S)!=' '&&Ins.charAt(S)!=',')
		{rj += Ins.charAt(S);S++;}

		
		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(S<Ins.length()&&Ins.charAt(S)!=' ')
		{rk += Ins.charAt(S);S++;}
		
		String out = conver5(rk)+conver5(rj)+conver5(rd);
		return out;
	}
	
	private String TRSI12type(String Ins,int S)
	{
		String rd="";String rj="";String si12="";
		
		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(Ins.charAt(S)!=' '&&Ins.charAt(S)!=',')
		{rd += Ins.charAt(S);S++;}

		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(Ins.charAt(S)!=' '&&Ins.charAt(S)!=',')
		{rj += Ins.charAt(S);S++;}

		
		while(Ins.charAt(S)==' '||Ins.charAt(S)==',')
			S++;
		while(S<Ins.length()&&Ins.charAt(S)!=' ')
		{si12 += Ins.charAt(S);S++;}
		
		String out = convers12(si12)+conver5(rj)+conver5(rd);
		return out;
	}
	
	private String TRUI12type(String Ins,int S)
	{
		String rd="";String rj="";String ui12="";
		
		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(Ins.charAt(S)!=' '&&Ins.charAt(S)!=',')
		{rd += Ins.charAt(S);S++;}

		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(Ins.charAt(S)!=' '&&Ins.charAt(S)!=',')
		{rj += Ins.charAt(S);S++;}
		
		while(Ins.charAt(S)==' '||Ins.charAt(S)==',')
			S++;
		while(S<Ins.length()&&Ins.charAt(S)!=' ')
		{ui12 += Ins.charAt(S);S++;}
		
		String out = converu12(ui12)+conver5(rj)+conver5(rd);
		return out;
	}
	
	private String SRI16type1(String Ins,int S)
	{
		String rj="";String rd="";String offs="";
		
		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(Ins.charAt(S)!=' '&&Ins.charAt(S)!=',')
		{rj += Ins.charAt(S);S++;}

		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(Ins.charAt(S)!=' '&&Ins.charAt(S)!=',')
		{rd += Ins.charAt(S);S++;}

		
		while(Ins.charAt(S)==' '||Ins.charAt(S)==',')
			S++;
		while(S<Ins.length()&&Ins.charAt(S)!=' ')
		{offs += Ins.charAt(S);S++;}
		
		String out = converoffs(offs)+conver5(rj)+conver5(rd);
		return out;
	}
	
	private String SRI16type2(String Ins,int S)
	{
		String rd="";String rj="";String offs="";
		
		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(Ins.charAt(S)!=' '&&Ins.charAt(S)!=',')
		{rd += Ins.charAt(S);S++;}

		while(Ins.charAt(S)!='$')
			S++;
		S++;
		while(Ins.charAt(S)!=' '&&Ins.charAt(S)!=',')
		{rj += Ins.charAt(S);S++;}

		
		while(Ins.charAt(S)==' '||Ins.charAt(S)==',')
			S++;
		while(S<Ins.length()&&Ins.charAt(S)!=' ')
		{offs += Ins.charAt(S);S++;}
		String out = converoffs(offs)+conver5(rj)+conver5(rd);
		return out;
	}
	
	/*conver dex string to bin string*/
	private String conver5(String t){
		int n = Integer.parseInt(t);
		if (n == 0)
	      return "00000";
		String bin = "";
		while (n != 0) {
		  bin = n % 2 + bin;
		  n = n / 2;
		  }
		for(int i = 5-bin.length();i>0;i--)
		{bin = "0" + bin;}
		return bin;
	}
	
	private String converu12(String t) 
	{
		int n = Integer.parseInt(t);
		if (n == 0)
	      return "000000000000";
		String bin = "";
		while (n != 0) {
		  bin = n % 2 + bin;
		  n = n / 2;
		  }
		for(int i = 12-bin.length();i>0;i--)
		{bin = "0" + bin;}
		return bin;
	}
	
	private String convers12(String t) {
		int n = Integer.parseInt(t);
		if(n<0) n=-n;
		if (n == 0)
	      return "000000000000";
		String bin = "";
		while (n != 0) {
		  bin = n % 2 + bin;
		  n = n / 2;
		  }
		for(int i = 11-bin.length();i>0;i--)
		{bin = "0" + bin;}
		if(Integer.parseInt(t)<0)
			bin='1'+bin;
		else
			bin='0'+bin;
		return bin;
	}
	
	private String converoffs(String t) {
		int n = Integer.parseInt(t);
		if(n<0) n=-n;
		if (n == 0)
	      return "0000000000000000";
		String bin = "";
		while (n != 0) {
		  bin = n % 2 + bin;
		  n = n / 2;
		  }
		for(int i = 15-bin.length();i>0;i--)
		{bin = "0" + bin;}
		if(Integer.parseInt(t)<0)
			bin='1'+bin;
		else
			bin='0'+bin;
		
		return bin;
	}
	/*Êä³ö½Ó¿Ú*/
	public String Ins_Out() {
		return InsOutput;
	}	
}
