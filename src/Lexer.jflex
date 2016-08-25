import java_cup.runtime.*;

%%
%public
%class Lexer
%unicode
%cup
%line
%column
%function next_token
%type java_cup.runtime.Symbol

%eofval{
    return new Symbol(sym.EOF);
%eofval}
%eofclose

%state CADENA

%{
  StringBuffer cadena = new StringBuffer();
  int numErr = 0;
%}




FinDeLinea = \r|\n|\r\n
Espacio = {FinDeLinea}|[ \t\f]
Identificador = [:jletter:][:jletterdigit:]*
Numero = 0 | [1-9][0-9]*
operador = \+ | \* | \- | \< | \> | \% | \<= | \>= | o | y
comentario = \/\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*\*+\/

%%

<YYINITIAL> {
   {Espacio}                 {}
   "programa"	{return new Symbol(sym.PROGRAMA, yyline, yycolumn);}
   "{"			{return new Symbol(sym.LLAVE_IZQ, yyline, yycolumn);}
   "}"			{return new Symbol(sym.LLAVE_DER, yyline, yycolumn);}
    "escribe"	{return new Symbol(sym.OP_ESCRITURA, yyline, yycolumn);}    
   ";"			{return new Symbol(sym.MARCADOR_FIN_SENTENCIA, yyline, yycolumn);}
   \"						{cadena.setLength(0);yybegin(CADENA);}
   "entero" {return new Symbol(sym.TIPO, yyline, yycolumn);}
   "booleano"   {return new Symbol(sym.TIPO, yyline, yycolumn,yytext());}
   "lee"        {return new Symbol(sym.OP_LECTURA, yyline, yycolumn,yytext());}
   "mientras"   {return new Symbol(sym.MIENTRAS, yyline, yycolumn,yytext());}
   {Numero} {return new Symbol(sym.DIGITO,yyline, yycolumn,yytext());}
   "si no"  {return new Symbol(sym.ELSE,yyline,yycolumn,yytext());}
   "si"     {return new Symbol(sym.IF, yyline, yycolumn, yytext());}
   "entonces" {return new Symbol(sym.ENTONCES, yyline, yycolumn,yytext());}
   "="          {return new Symbol(sym.ASIGNACION,yyline,yycolumn,yytext());}
   "=="       {return new Symbol(sym.IGUAL,yyline,yycolumn,yytext());}
   {operador} {return new Symbol(sym.OP_BIN, yyline, yycolumn,yytext());}
   "desde"    {return new Symbol(sym.INICIO,yyline,yycolumn,yytext());}
   "hasta"    {return new Symbol(sym.FIN, yyline, yycolumn,yytext());}
   "para"     {return new Symbol(sym.FOR, yyline, yycolumn,yytext());}
   "verdad"   {return new Symbol(sym.VER, yyline, yycolumn,yytext());}
   "falso"    {return new Symbol(sym.FAL, yyline, yycolumn,yytext());}
   {comentario} {return new Symbol(sym.COMMT, yyline, yycolumn,yytext());}
   "no"     {return new Symbol(sym.NO,yyline,yycolumn,yytext());}
   {Identificador} {return new Symbol(sym.ID, yyline, yycolumn, yytext());} 

}

<CADENA> {
\"		{yybegin(YYINITIAL);return new Symbol(sym.LITERAL_CADENA, yyline, yycolumn,cadena.toString());}
[^\n\r\"\\]+ {cadena.append(yytext());}

}

/* si la entrada no cumple ninguna de las reglas se considera un error */
[^]                              {numErr++;return new Symbol(sym.error, yyline, yycolumn,yytext());}