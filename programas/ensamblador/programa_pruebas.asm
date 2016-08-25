.data


 lit1: .asciiz "hola mundo!"
.text

 programa:
	 la $t6, lit1
	 li $v0, 4
	 move $a0, $t6
	 syscall
	 j fin_programa
 fin_programa: