
public class MyBNF {
	public static void main(String[] args) {
		for(String statements : args) {
			BNF bnf=new BNF(statements);
			System.out.println(statements+": "+bnf.result());
		}
	}
}
