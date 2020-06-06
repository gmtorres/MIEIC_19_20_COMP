.class public Hello_T
.super java/lang/Object

.field foo I

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 3
	.limit locals 2

ldc 0.5f
ldc 0.4f
fmul
ldc 1.7f
ldc 0.4f
fdiv
fadd
fstore_1

fload_1
invokestatic	io.println(F)V

	return
.end method
