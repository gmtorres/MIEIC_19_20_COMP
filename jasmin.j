.class public HelloWorld
.super java/lang/Object


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
fconst_2
invokevirtual Hello_T.optimization(F)F
invokestatic	io.println(F)V

	return
.end method

.method public optimization(F)F
	.limit stack 2
	.limit locals 2

fconst_2
fstore_1

begin_loop1:

fload_1
fconst_2
fadd
fstore_1

fload_1
ldc 0.25f
fsub
fstore_1

fload_1
ldc 4.0f
fcmpg
iflt begin_loop1
end_loop1:

fload_1
freturn

.end method