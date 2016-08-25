
import java.util.Hashtable;
import java.util.Set;


public class SymbolTable {

   private Hashtable<String, Object> hashtable;
   
   public SymbolTable() {
	    hashtable = new Hashtable<String,Object>();
    }
   
   public Object lookup(String id){
	   return hashtable.get(id);
   }
   
   public boolean add(String id, Object val){
	   if(hashtable.containsKey(id)){
		   return false; // la variable se ha definido dos veces
	   }else{
		   hashtable.put(id, val);
		   return true;
	   }
   }
   
   public String toString(){
	   Set<String> keys = hashtable.keySet();
	   String tablaString = "\n\t\tTabla de simbolos:\n\t\t----------------------------------\n";
       for(String key: keys){
           tablaString+="\t\t"+key+" es de tipo: "+hashtable.get(key)+"\n";
       }
       return tablaString+"\t\t----------------------------------\n\n";
       
   }

}//SymbolTable