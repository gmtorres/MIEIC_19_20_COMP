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
	.limit locals 2

fconst_2
fstore_1

fload_1
ldc 5.6f
fcmpl
ifgt else_if1

iconst_1
invokestatic	io.println(I)V

goto end_if1

else_if1:
iconst_0
invokestatic	io.println(I)V

end_if1:

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
if_icmpge else_if2

iload_1
iconst_5
iadd
istore_2

goto end_if2

else_if2:
iload_1
iconst_5
if_icmpge else_if3

iload_1
iconst_5
isub
istore_2

iinc 2 1

goto end_if3

else_if3:
iconst_1
istore_2

end_if3:

end_if2:

iinc 3 3

iload_2
iload_3
iadd
ireturn

.end method
