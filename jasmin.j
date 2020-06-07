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
	.limit locals 2

iconst_0
istore_1

iinc 1 1

	return
.end method

.method public optimization(F)LHello_T;
	.limit stack 2
	.limit locals 2

fconst_2
fstore_1

loop1:
fload_1
ldc 4.0f
fcmpg
ifge end_loop1
begin_loop1:

fload_1
fconst_2
fadd
fstore_1

fload_1
ldc 0.25f
fsub
fstore_1

goto loop1
end_loop1:

aload_0
areturn

.end method
