.class public Hello_T
.super java/lang/Object


.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 1
	.limit locals 4

iconst_0
istore_3

iconst_0
istore_2

	return
.end method

.method public sum(IIIII)I
	.limit stack 2
	.limit locals 10

iload_1
iload_2
iadd
istore 6

iload 6
iload 4
iadd
istore_3

iload_2
iconst_2
imul
istore 7

iload 6
iload 7
iadd
istore 8

iload 8
iload 4
imul
istore_2

iload 8
iconst_1
iadd
istore 9

iload 9
iload_2
imul
istore 5

iload 4
istore_2

iconst_1
ireturn

.end method
