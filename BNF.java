
public class BNF {
	private boolean myresult;
	private char[] statement;
	private String bnfstatement;  //�x�sbnf�ثe�����A
	
	public BNF(String statement) {
		int end=0;
		this.statement=new char[statement.length()];
		for(int i=0;i<statement.length();i++) {  //�NString��statement���s�s��char[]
			if(statement.charAt(i)=='A' || statement.charAt(i)=='B' || statement.charAt(i)=='C')
				this.statement[i]='I';  //��id�Τ@���N��'I' �Bid������ 'A' | 'B' | 'C'
			else if(statement.charAt(i)=='=' || statement.charAt(i)=='+' || statement.charAt(i)=='*' || statement.charAt(i)=='(' || statement.charAt(i)==')')
				this.statement[i]=statement.charAt(i);  //�ˬd�O�_���X�k�B�⤸ '=' | '+' | '*' | '(' | ')'
			else  //���ŦX�W�w��id�ιB�⤸�A�����P�w�L�k��
				end=1;
		}
		
		if(end==0)  //�Yid�P�B�⤸���X�k�h�ϥ�findBnf�i�汵�U�Ӫ��P�_
			findBnf();
	}
	
	private void findBnf() {
		int index;
		int endflag=0;  //���ŦX�W�w��flag
		bnfstatement="e";  //<assign> -> <id> = <expr>
		if(statement[0] == 'I' || statement[1] == '=') {  //�e����ӥ�����'I'�M'='
			for(char c : statement) {  //�J��B�⤸�h�̹B�⤸��bnf�i�}
				if(c=='+') {
					expr();
				}
				else if(c=='*') {
					index=bnfstatement.lastIndexOf('t');
					if(index != -1)  //*��term���ܦ� �䤣��term�hindex��-1
						term(index);
					else {
						endflag=1;  //���ųW�w�]�mendflag��1
						break;
					}
				}
				else if(c=='(') {
					index=bnfstatement.lastIndexOf('t');
					int indexf=bnfstatement.lastIndexOf('f');
					if(index != -1) //()�O��factor���ܦ� ��term�i�ܦ�factor
						factor(index);
					else if(indexf != -1) {
						factor(indexf);
					}
					else{
						endflag=1;
						break;
					}
				}
			}
			
			if(endflag==0) {  //�̫��ˬdbnf���G���T��
				for(int i=0;i<bnfstatement.length();i++) {
					if(statement[i+2]=='I') {  //expr��term��factor�᳣̫�i�H����id
						if(bnfstatement.charAt(i) != 'e' && bnfstatement.charAt(i) != 't' && bnfstatement.charAt(i) != 'f')
							endflag=1;
					}
					else if(statement[i+2] == '*' || statement[i+2] == '+' || statement[i+2] == '(' || statement[i+2] == ')'){
						if(statement[i+2] != bnfstatement.charAt(i))
							endflag=1;
					}  //�ˬd�B�⤸�O�_�ۦP
				}
				if(endflag==0)  //�ˬd���S���~�Nmyresult�]��true
					myresult=true;
			}
		 }
	}
	
	public boolean result() {
		return myresult;
	}
	
	private void expr() {
		int index=bnfstatement.lastIndexOf('e');
		String theFront=bnfstatement.substring(0,index);
		String theRest=bnfstatement.substring(index+1);
		bnfstatement=theFront+"e+t"+theRest;
		//<exper> + <term> | <id>
	}
	
	private void term(int index) {
		String theFront=bnfstatement.substring(0,index);
		String theRest=bnfstatement.substring(index+1);
		bnfstatement=theFront+"t*f"+theRest;
		//<term> * <factor> | <factor>
	}
	
	private void factor(int index) {
		String theFront=bnfstatement.substring(0,index);
		String theRest=bnfstatement.substring(index+1);
		bnfstatement=theFront+"(e)"+theRest;
		//(<expr>) | <id>
	}
}
