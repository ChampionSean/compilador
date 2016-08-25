

public interface Visitor {

	public void visit(AST.Nodo n);
	public void visit(AST.Programa p);
	public void visit(AST.BloqueDeSentencias b);
	public void visit(AST.Sentencia s);
	public void visit(AST.Operacion o);
	public void visit(AST.OperacionSalida oe);
}