.class public Hello_T
.super java/lang/Object

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method


.method public static main([Ljava/lang/String;)V
	.limit stack 2
	.limit locals 10

new Hello_T
dup
invokespecial Hello_T/<init>()V
astore_3

iconst_3
istore_2

loop1:
iload_2
bipush 10
if_icmpge end_loop1

aload_3
iload_2
invokevirtual Hello_T.soma(I)I
istore_2

goto loop1
end_loop1:

	return
.end method

.method public soma(I)I
	.limit stack 2
	.limit locals 10

iload_1
invokestatic	io.println(I)V

iload_1
iconst_1
iadd
ireturn

.end method
