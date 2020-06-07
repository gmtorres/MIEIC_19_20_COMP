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
	.limit locals 3

new Hello_T
dup
invokespecial Hello_T/<init>()V
invokevirtual Hello_T.f()F
ldc 0.1f
fadd
f2i
istore_1

iinc 1 1

iload_1
invokestatic	io.println(I)V

	return
.end method

.method public f()F
	.limit stack 1
	.limit locals 1

ldc 1.8f
freturn

.end method
