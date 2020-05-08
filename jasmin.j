.class public Hello_T
.super java/lang/Object

.field i I
.field num I

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public getInst(I)LHello_T;
	.limit stack 2
	.limit locals 3

new Hello_T
dup
invokespecial Hello_T/<init>()V
astore_2

aload_2
iload_1
invokevirtual Hello_T.setI(I)I

pop

aload_2
areturn

.end method

.method public setI(I)I
	.limit stack 2
	.limit locals 2

aload_0
iload_1
putfield Hello_T/i I

aload_0
getfield Hello_T/i I
ireturn

.end method

.method public lt(II)Z
	.limit stack 2
	.limit locals 3

iload_1
iload_2
if_icmpge else_if1

iconst_1

goto end_if1

else_if1:
iconst_0

end_if1:
ireturn

.end method

.method public b()Z
	.limit stack 1
	.limit locals 1

iconst_1
ireturn

.end method

.method public val()I
	.limit stack 1
	.limit locals 1

aload_0
getfield Hello_T/i I
ireturn

.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 3
	.limit locals 4

new Hello_T
dup
invokespecial Hello_T/<init>()V
astore_2

aload_2
iconst_2
iconst_5
invokevirtual Hello_T.lt(II)Z
istore_3

aload_2
invokevirtual Hello_T.b()Z
istore_3

aload_2
iconst_3
invokevirtual Hello_T.getInst(I)LHello_T;
invokevirtual Hello_T.b()Z
istore_3

aload_2
invokevirtual Hello_T.val()I
invokestatic	io.println(I)V

	return
.end method
