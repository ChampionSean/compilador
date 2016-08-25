

public class TypeCheckVisitor implements Visitor{
private SymbolTable symtable;
private int numErr;

    public TypeCheckVisitor(SymbolTable symt){
    	symtable = symt;
    	numErr = 0;
    }
    
 public void visit(AST.Nodo n){
	 if (n instanceof AST.ExpresionLiteral) visit((AST.ExpresionLiteral)n);
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
		if (s instanceof AST.Operacion) visit((AST.Operacion)s);
		
	}
		
	public void visit(AST.Operacion o){
		if (o instanceof AST.OperacionSalida) visit((AST.OperacionSalida)o);
	}
	
	public void visit(AST.OperacionSalida os){}

	public void typeCheck(AST.Programa p){
		visit(p);
	}
	
	public String getTipo(AST.Expresion n){
				if(n instanceof AST.ExpresionLiteral){ 
			AST.ExpresionLiteral el = (AST.ExpresionLiteral)n;
			
			Object literal = el.literal;	
			if(literal instanceof String) return "cadena";
			else if (literal instanceof Boolean) return "logico";
			else if (literal instanceof Integer) return "entero";
			return "indefinido";
			
		}
		return "indefinido";
	}
	
	public int getNumErr(){
		return numErr;
	}
}
	
