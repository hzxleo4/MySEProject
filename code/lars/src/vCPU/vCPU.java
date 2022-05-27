package lars.vcpu;
public class vCPU{
	int [] register;
	int [] memory;
	String [] pc;
	int pcPtr;
	MainFrame mainFrame;
	public vCPU(String [] binCode,MainFrame mF){
		register = new int[32];
		memory = new int[256];
		pc = binCode;
		mainFrame = mF;
	}
	public void reset(){
		for(int i=0;i<32;++i)
			register[i] = 0;
		for(int i=0;i<256;++i)
			memory[i] = 0;
		pcPtr = 0;
	}
	public void reload(String [] binCode){
		reset();
		pc = binCode;
	}
	public void runAll(){
		while(pcPtr < pc.length){
			runStep();
		}
	}
	public void runStep(){
		String instruction = pc[pcPtr];
		pcPtr++;
		mainFrame.setRegisterValue(0,pcPtr);
		String opString = instruction.substring(0,6);
		int op = Integer.parseInt(opString,2);
		if(op == 0){
			runAri(instruction);
		}
		else if(op >= 16){
			runJump(instruction);
		}
		else{
			runSL(instruction);
		}	
	}
	void runAri(String instruction){
		String opString,rkString,rjString,rdString;
		int op,rk,rj,rd,sui5,sui12;
		opString = instruction.substring(0,17);
		rkString = instruction.substring(17,22);
		rjString = instruction.substring(22,27);
		rdString = instruction.substring(27,32);
		op = Integer.parseInt(opString,2);
		sui12 = Integer.parseInt(instruction.substring(10,22),2);
		sui5 = Integer.parseInt(rkString,2);
		rk = Integer.parseInt(rkString,2);
		rj = Integer.parseInt(rjString,2);
		rd = Integer.parseInt(rdString,2);
		int tmp = 0;
		if(op > 1024){
			tmp = ALU(op,register[rj],sui12);
		}
		else if(op > 128){
			tmp = ALU(op,register[rj],sui5);
		}
		else{
			tmp = ALU(op,register[rj],register[rk]);
		}
		register[rd] = tmp;
		mainFrame.setRegisterValue(rd,tmp);
	}
	void runJump(String instruction){
		String rjString,rdString,offString;
		int rj,rd,off;
		String opString = instruction.substring(0,6);
		int op = Integer.parseInt(opString,2);
		rjString = instruction.substring(22,27);
		rdString = instruction.substring(27,32);
		offString = instruction.substring(6,21);
		rj = Integer.parseInt(rjString,2);
		rd = Integer.parseInt(rdString,2);
		off = Integer.parseInt(offString,2);
		if(off >= 2 << 16){
			off -= 2 << 16;
			off = -off;
		}
		if(op == 22){
			if(register[rj] == register[rd])
				pcPtr += off;
		}
		else if(op == 23){
			if(register[rj] != register[rd])
				pcPtr += off;
		}
		else if(op == 19){
			register[rd] = pcPtr + 1;
			pcPtr = register[rj] + off;
		}
	}
	void runSL(String instruction){
		String opString,si12String,rjString,rdString;
		int op,si12,rj,rd;
		opString = instruction.substring(0,10);
		si12String = instruction.substring(10,22);
		rjString = instruction.substring(22,27);
		rdString = instruction.substring(27,32);
		op = Integer.parseInt(opString,2);
		si12 = Integer.parseInt(si12String,2);
		rj = Integer.parseInt(rjString,2);
		rd = Integer.parseInt(rdString,2);
		if(si12 >= 2 << 12){
				si12 -= 2 << 12;
				si12 = -si12;
			}
		int vaddr = register[rj] + si12;
		if(op == 162){
			register[rd] = memory[vaddr];
			mainFrame.setRegisterValue(rd,tmp);
		}
		else{
			memory[vaddr] = register[rd];
			mainFrame.setMemoryValue(vaddr,register[rd]);
		}
	}
	int ALU(int op,int a,int b){
		int res = 0;
		switch(op){
		case 32:
			res = a+b;
			break;
		case 34:
			res = a-b;
			break;
		case 56:
			res = a*b;
			break;
		case 57:
			res = a*b;
			res >>= 32;
			break;
		case 64:
			res = a/b;
			break;
		case 66:
			res = a/b;
			break;
		case 65:
			res = a%b;
			break;
		case 67:
			res = a%b;
			break;
		case 36:
			res = a < b	? 1 : 0;
			break;
		case 37:
			res = a < b ? 1 : 0;
			break;
		case 40:
			res = ~(a|b);
			break;
		case 41:
			res = a & b;
			break;
		case 42:
			res = a | b;
			break;
		case 43:
			res = a ^ b;
			break;
		case 46:
			res = a << b;
			break;
		case 47:
			res = a >> b;
			break;
		case 129:
			res = a << b;
			break;
		case 137:
			res = a >> b;
			break;
		default:
			res = -1e9;
		}
		if(op >= 1024){
			op >>= 7;
			switch(op){
			case 10:
				if(b >= 2 << 12){
					b -= 2 << 12;
					b = -b;
				}
				res = a+b;
				break;
			case 13:
				res = a&b;
				break;
			case 14:
				res = a|b;
				break;
			case 15:
				res = a^b;
				break;
			default:
				res = -1e9;
			}
		}
		return res;
	}
	public void printRegister(){
		for(int i=0;i<12;++i){
			System.out.println(register[i]);
		}
	}
	/**
	 * addi.w 1 1 5
	 * addi.w 2 2 5
	 * add.w 3 1 2
	 */ 
	/**
	public static void main(String[] args) {
		String [] example = {"00000010100000000001010000100001",
							 "00000010100000000001010001000010",
							 "00000000000100000000100000100011"};
		vCPU vc = new vCPU(example);
		vc.runAll();
		vc.printRegister();
	}
	*/

}