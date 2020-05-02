.class public Hello_T
.super java/lang/Object

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method


.method public static main([Ljava/lang/String;)V
	.limit stack 3
	.limit locals 10

new Hello_T
dup
invokespecial Hello_T/<init>()V
astore_3

iconst_1
istore_2

loop1:
iload_2
bipush 16
if_icmpge end_loop1

aload_3
iload_2
invokevirtual Hello_T.fibbonaci(I)I
invokestatic	io.println(I)V

iload_2
iconst_1
iadd
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

.method public fibbonaci(I)I
	.limit stack 5
	.limit locals 10

iload_1
iconst_3
if_icmpge else_if1

iconst_1
istore_2

goto end_if1

else_if1:
aload_0
iload_1
iconst_2
isub
invokevirtual Hello_T.fibbonaci(I)I
aload_0
iload_1
iconst_1
isub
invokevirtual Hello_T.fibbonaci(I)I
iadd
istore_2

end_if1:

iload_2
ireturn

.end method
