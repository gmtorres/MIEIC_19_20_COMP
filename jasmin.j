.class public Hello
.super java/lang/Object

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method


.method public static main([Ljava/lang/String;)V
	.limit stack 2
	.limit locals 10

new Hello
dup
invokespecial Hello/<init>()V
astore_3

iconst_3
istore_2

aload_0
iload_2
invokevirtual Hello.soma(I)I
pop

	return
.end method

.method public soma(I)I
	.limit stack 2
	.limit locals 10

iload_1
iconst_1
iadd
ireturn

.end method
