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

bipush 13
pop

bipush 15
pop

aload_0
bipush 15
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
istore_3

iconst_1
istore_2

iload_1
bipush 10
if_icmpge else_if1

iconst_2
istore_2

iload_3
iconst_5
if_icmpge else_if2

iconst_1
istore_3

goto end_if2

else_if2:
iconst_2
istore_3

end_if2:

iload_3
iconst_2
iadd
istore_3

goto end_if1

else_if1:
iconst_1
istore_3

end_if1:

iload_3
iload_2
iadd
istore_3

iload_3
ireturn

.end method
