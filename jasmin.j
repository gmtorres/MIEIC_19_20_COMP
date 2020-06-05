.class public Hello_T
.super java/lang/Object

.field foo I

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 2
	.limit locals 4

new Hello_T
dup
invokespecial Hello_T/<init>()V
astore_0



aload_0
bipush 9
invokevirtual Hello_T.prop(I)I
invokestatic	io.println(I)V

	return
.end method

.method public prop(I)I
	.limit stack 2
	.limit locals 4

iload_1
iconst_1
iadd
pop

iconst_1
istore_2

iload_1
bipush 10
if_icmpge else_if1

iconst_2
istore_2

loop1:
iload_1
iconst_5
if_icmpge end_loop1

iload_1
pop

iload_1
iconst_1
iadd
istore_1

goto loop1
end_loop1:

iload_1
iconst_2
iadd
istore_1

goto end_if1

else_if1:
iconst_1
istore_1

end_if1:

iload_1
iload_2
iadd
istore_2

iload_2
ireturn

.end method

.method public sum2()I
	.limit stack 1
	.limit locals 4







bipush 77
ireturn

.end method
