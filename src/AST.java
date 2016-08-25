
import java.util.ArrayList;
import java.util.Collections;

class AST{

	static abstract class Nodo implements Visitable {
		int linea;
		public void accept(Visitor v){
		}
	}

	static class Programa extends Nodo{
		BloqueDeSentencias bloqueDeSentencias;

		Programa (BloqueDeSentencias bloqueDeSentencias, int linea){
			this.bloqueDeSentencias = bloqueDeSentencias;
			this.linea = linea;
		}
		
		public void accept(Visitor v){
			v.visit(this);
		}
	}

	static class BloqueDeSentencias extends Nodo{
		ArrayList<Sentencia> sentencias; 
		BloqueDeSentencias (ArrayList<Sentencia> sentencias, int linea){
			this.linea = linea;
			this.sentencias = sentencias;
		}
		public void accept(Visitor v){
			v.visit(this);
		}
	}

	static abstract class Sentencia extends Nodo{
		public void accept(Visitor v){
			v.visit(this);
		}
	}

	static abstract class Operacion extends Sentencia{
		public void accept(Visitor v){
			v.visit(this);
		}
	}

	static abstract class Expresion extends Nodo{
	public void accept(Visitor v){
			v.visit(this);
		}
	}
	
	static class ExpresionLiteral extends Expresion{
		Object literal;
		
		ExpresionLiteral(Object literal, int linea){
			this.literal = literal;
			this.linea = linea;
		}
		public void accept(Visitor v){
			v.visit(this);
		}

	}

	
	static  class OperacionSalida extends Operacion{
		Expresion exp;

		OperacionSalida(Expresion exp, int linea){
			this.exp = exp;
			this.linea = linea;
		}
	public void accept(Visitor v){
			v.visit(this);
		}

	}

}