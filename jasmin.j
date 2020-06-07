.class public Hello_T
.super java/lang/Object

.field foo I

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 1
	.limit locals 3

ldc 3.21f
f2i
istore_1

iinc 1 1

iload_1
invokestatic	io.println(I)V

	return
.end method
