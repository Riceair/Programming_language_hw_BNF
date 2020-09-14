
public class BNF {
	private boolean myresult;
	private char[] statement;
	private String bnfstatement;  //儲存bnf目前的狀態
	
	public BNF(String statement) {
		int end=0;
		this.statement=new char[statement.length()];
		for(int i=0;i<statement.length();i++) {  //將String的statement重新存成char[]
			if(statement.charAt(i)=='A' || statement.charAt(i)=='B' || statement.charAt(i)=='C')
				this.statement[i]='I';  //把id統一成代號'I' 且id必須為 'A' | 'B' | 'C'
			else if(statement.charAt(i)=='=' || statement.charAt(i)=='+' || statement.charAt(i)=='*' || statement.charAt(i)=='(' || statement.charAt(i)==')')
				this.statement[i]=statement.charAt(i);  //檢查是否為合法運算元 '=' | '+' | '*' | '(' | ')'
			else  //不符合規定的id或運算元，直接判定無法解
				end=1;
		}
		
		if(end==0)  //若id與運算元都合法則使用findBnf進行接下來的判斷
			findBnf();
	}
	
	private void findBnf() {
		int index;
		int endflag=0;  //不符合規定的flag
		bnfstatement="e";  //<assign> -> <id> = <expr>
		if(statement[0] == 'I' || statement[1] == '=') {  //前面兩個必須為'I'和'='
			for(char c : statement) {  //遇到運算元則依運算元把bnf展開
				if(c=='+') {
					expr();
				}
				else if(c=='*') {
					index=bnfstatement.lastIndexOf('t');
					if(index != -1)  //*由term所變成 找不到term則index為-1
						term(index);
					else {
						endflag=1;  //不符規定設置endflag為1
						break;
					}
				}
				else if(c=='(') {
					index=bnfstatement.lastIndexOf('t');
					int indexf=bnfstatement.lastIndexOf('f');
					if(index != -1) //()是由factor所變成 而term可變成factor
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
			
			if(endflag==0) {  //最後檢查bnf結果正確性
				for(int i=0;i<bnfstatement.length();i++) {
					if(statement[i+2]=='I') {  //expr或term或factor最後都可以換為id
						if(bnfstatement.charAt(i) != 'e' && bnfstatement.charAt(i) != 't' && bnfstatement.charAt(i) != 'f')
							endflag=1;
					}
					else if(statement[i+2] == '*' || statement[i+2] == '+' || statement[i+2] == '(' || statement[i+2] == ')'){
						if(statement[i+2] != bnfstatement.charAt(i))
							endflag=1;
					}  //檢查運算元是否相同
				}
				if(endflag==0)  //檢查都沒錯誤將myresult設為true
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
