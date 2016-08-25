import java.util.Collections;


public class BuildSymbolTableVisitor implements Visitor{
	private SymbolTable symtable;
	private int numErr;

    public BuildSymbolTableVisitor(){
    	symtable = new SymbolTable();
    	numErr = 0;
    }
    
 public void visit(AST.Nodo n){
    	
    }
    
	public void visit(AST.Programa p){
		p.bloqueDeSentencias.accept(this);
	}
	
	public void visit(AST.BloqueDeSentencias b){
		for(AST.Sentencia sentencia:b.sentencias){
			sentencia.accept(this);
		}
	}
	
	public void visit(AST.Sentencia s){
	}
		
	public void visit(AST.Operacion o){}
	
	public void visit(AST.OperacionSalida os){}

	
	public int getNumErr(){
		return numErr;
	}
	
	public SymbolTable buildSymbolTable(AST.Programa p){
		visit(p);
		return symtable;
	}

}