.class public Hello_T
.super java/lang/Object

.field b I

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 2
	.limit locals 5

iconst_4
istore_3

new Hello_T
dup
invokespecial Hello_T/<init>()V
astore 4

aload 4
invokevirtual Hello_T.get()I
invokestatic	io.println(I)V

ldc "ola"
invokestatic	io.println(Ljava/lang/String;)V

	return
.end method

.method public get()I
	.limit stack 1
	.limit locals 1

aload_0
getfield Hello_T/b I
ireturn

.end method
