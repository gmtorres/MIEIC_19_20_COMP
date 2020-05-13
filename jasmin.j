.class public Hello_T
.super java/lang/Object

.field c Z
.field j I

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public ackermann(II)I
	.limit stack 6
	.limit locals 4

iload_1
iconst_1
if_icmpge else_if1

iload_2
iconst_1
iadd
istore_3

goto end_if1

else_if1:
iload_2
iconst_1
if_icmpge else_if2

aload_0
iload_1
iconst_1
isub
iconst_1
invokevirtual Hello_T.ackermann(II)I
istore_3

goto end_if2

else_if2:
aload_0
iload_1
iconst_1
isub
aload_0
iload_1
iload_2
iconst_1
isub
invokevirtual Hello_T.ackermann(II)I
invokevirtual Hello_T.ackermann(II)I
istore_3

end_if2:

end_if1:

iload_3
ireturn

.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 3
	.limit locals 4

iconst_0
istore_3

new Hello_T
dup
invokespecial Hello_T/<init>()V
iconst_3
bipush 6
invokevirtual Hello_T.ackermann(II)I
invokestatic	io.println(I)V

	return
.end method

.method public a()[I
	.limit stack 2
	.limit locals 1

aload_0
iconst_4
putfield Hello_T/j I

aload_0
iconst_0
putfield Hello_T/c Z

aload_0
getfield Hello_T/c Z
ifeq else_if3

iconst_1
invokestatic	io.println(I)V

goto end_if3

else_if3:
iconst_0
invokestatic	io.println(I)V

end_if3:

bipush 100
newarray int
areturn

.end method
