.class public Hello_T
.super java/lang/Object

.field foo I

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 6
	.limit locals 4

iconst_0
istore_0

iload_0
pop

new Hello_T
dup
invokespecial Hello_T/<init>()V
astore_0

aload_0
iconst_1
iconst_1
iconst_1
iconst_1
iconst_1
invokevirtual Hello_T.sum(IIIII)I
invokestatic	io.println(I)V

	return
.end method

.method public sum(IIIII)I
	.limit stack 3
	.limit locals 10

iload_1
iload_2
iadd
istore_3

aload_0
iload 4
invokevirtual Hello_T.set(I)I

pop

iload_3
iload 4
iadd
istore_1

iload_2
iconst_2
imul
istore_2

iload_3
iload_2
iload_1
iadd
iadd
istore_2

iload_2
iload 4
imul
istore_1

iload_2
iconst_1
iadd
istore_2

iload_2
iload_1
imul
istore_2

iload 4
pop

iload_2
ireturn

.end method

.method public set(I)I
	.limit stack 2
	.limit locals 2

aload_0
iload_1
putfield Hello_T/foo I

iload_1
ireturn

.end method
