import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Hashtable;


public class CodeGeneratorVisitor implements Visitor{

	String data, text,fglobales;
	private int numLabel;
	MIPSResistries registros;
	private static String registro_actual;
	private boolean includeConcat;
	private boolean X = false;
	private int numLit =0;
	private String includes;
	public CodeGeneratorVisitor(String includes){
		data=".data\n\n";
		text=".text\n";
		numLabel=0;
		includeConcat = false;
		this.includes = includes;
		registros = new MIPSResistries();
	}

		public void visit(AST.Nodo n){
			if (n instanceof AST.ExpresionLiteral) visit((AST.ExpresionLiteral)n);
	    }
	    
		public void visit(AST.Programa p){
			text+="\n programa:";
			p.bloqueDeSentencias.accept(this);
			text+="\n\t j fin_programa";
		}
		
		public void visit(AST.BloqueDeSentencias b){
			for(AST.Sentencia sentencia:b.sentencias){
				sentencia.accept(this);
			}
		}
		
		public void visit(AST.Sentencia s){
			if (s instanceof AST.Operacion) visit((AST.Operacion)s);
		}

		public void visit(AST.ExpresionLiteral el){
			
			registro_actual = registros.getFreeRegister();
			String valor = "";
			String tipo = "";
			if(el.literal instanceof String){ valor ="\""+el.literal+"\"";X=true; tipo="asciiz";}
			numLit++;
			data+="\n lit"+numLit+": ."+tipo+" "+valor;
			text+="\n\t la "+registro_actual+", "+"lit"+numLit;
			
		}

		
		public void visit(AST.Operacion o){
			if (o instanceof AST.OperacionSalida) visit((AST.OperacionSalida)o);
			
		}
		
		
		public void visit(AST.Expresion e){
			if (e instanceof AST.ExpresionLiteral) visit((AST.ExpresionLiteral)e);
			
		}
		
		public void visit(AST.OperacionSalida os){
			this.visit(os.exp);
			//Codigo para convertir a cadena
			text+="\n\t li $v0, 4";
			text+="\n\t move $a0, "+registro_actual;
			text+="\n\t syscall";
		}

		
		public String getASM(){
			if(includeConcat){
				try{
				byte[] encoded = Files.readAllBytes(Paths.get(includes));
				  String func = new String(encoded, "UTF-8");
				  text+="\n"+func;
				}catch(IOException ioe){
					System.out.println("ERROR: No fue posible incluir funciones auxiliares ["+includes+"]");
					ioe.printStackTrace(System.out);
					return null;
				}
			}
			return data+"\n"+text+"\n fin_programa:";
		}

 private  class MIPSResistries{
	 private  Hashtable<String, Boolean> mips_registries;
	 
	 public  MIPSResistries(){
		 mips_registries = new Hashtable<String,Boolean>();
		 mips_registries.put("$t6",true);
		 mips_registries.put("$t5",true);
		 mips_registries.put("$t4",true);
		 mips_registries.put("$t3",true);
		 mips_registries.put("$t2",true);
		 mips_registries.put("$t1",true);
		 mips_registries.put("$t0",true);
	 }
	 
	 public  String getFreeRegister(){
		 Enumeration<String> enumKey = mips_registries.keys();
		 while(enumKey.hasMoreElements()) {
		     String key = enumKey.nextElement();
		     Boolean val = mips_registries.get(key);
		     mips_registries.put(key, false);
		     if(val.equals(true)){
		     return key;
		     }
		 }
		 return null;
	 }
	 
	 public  void freeRegister(String reg){
		     mips_registries.put(reg, false);
	 }
	 
	 
 }

}