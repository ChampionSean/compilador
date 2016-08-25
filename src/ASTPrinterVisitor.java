import java.util.Collections;


public class ASTPrinterVisitor implements Visitor{

	private int nivel = 2;
	private String tabs = "";
	private String tabs(){
		tabs = "";
		for(int i=0;i<nivel;i++){
			tabs +="\t";
		}
		return tabs;
	}
    public void visit(AST.Nodo n){
    if (n instanceof AST.ExpresionLiteral) visit((AST.ExpresionLiteral)n);
    }
    
    
    
	public void visit(AST.Programa p){
		String tabs = tabs();
		System.out.println(tabs+"[Programa:");
		nivel++;
		p.bloqueDeSentencias.accept(this);
		System.out.println(tabs+"]");
	}
	
	public void visit(AST.BloqueDeSentencias b){
		String tabs = tabs();
		Collections.reverse(b.sentencias);
		System.out.println(tabs+"[BloqueDeSentencias:");
		nivel++;
		for(AST.Sentencia sentencia:b.sentencias){
			sentencia.accept(this);
		}
		System.out.println(tabs+"]");
	}
	
	public void visit(AST.Sentencia s){
		if (s instanceof AST.Operacion) visit((AST.Operacion)s);
	}
	
	public void visit(AST.Operacion o){
		 if (o instanceof AST.OperacionSalida) visit((AST.OperacionSalida)o);
	}
	
	public void visit(AST.OperacionSalida os){
		String tabs = tabs();
		System.out.println(tabs+"[OperacionSalida: ");
		nivel++;
		visit(os.exp);
		System.out.println("\n"+tabs+"]");
	}

	public void visit(AST.Expresion e){
		if (e instanceof AST.ExpresionLiteral) visit((AST.ExpresionLiteral)e);	
	}

	public void visit(AST.ExpresionLiteral e){
		String tabs = tabs();
		System.out.println(tabs+"\""+e.literal+"\"");
	}

	
}