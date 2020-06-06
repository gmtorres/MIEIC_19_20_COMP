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
	.limit locals 1

new Hello_T
dup
invokespecial Hello_T/<init>()V
iconst_2
invokevirtual Hello_T.prop(I)I
invokestatic	io.println(I)V

	return
.end method

.method public prop(I)I
	.limit stack 2
	.limit locals 4

iconst_4
istore_2

iconst_3
iload_2
iadd
istore_3

iload_3
iconst_5
if_icmpge else_if1

iload_1
iconst_5
iadd
istore_2

goto end_if1

else_if1:
iload_1
iconst_5
if_icmpge else_if2

iload_1
iconst_5
isub
istore_2

iinc 2 1

goto end_if2

else_if2:
iconst_1
istore_2

end_if2:

end_if1:

iinc 3 3

iload_2
iload_3
iadd
ireturn

.end method
