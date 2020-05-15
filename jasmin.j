.class public Hello_T
.super java/lang/Object

.field b I

.method public <init>(I)V
	.limit stack 3
	.limit locals 2

aload_0
invokenonvirtual java/lang/Object/<init>()V

aload_0
iload_1
putfield Hello_T/b I

	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 3
	.limit locals 5

iconst_4
istore_3

new Hello_T
dup
iload_3
invokespecial Hello_T/<init>(I)V
astore 4

aload 4
invokevirtual Hello_T.get()I
invokestatic	io.println(I)V

	return
.end method

.method public get()I
	.limit stack 1
	.limit locals 1

aload_0
getfield Hello_T/b I
ireturn

.end method
