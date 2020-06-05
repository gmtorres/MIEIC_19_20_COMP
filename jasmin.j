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
	.limit locals 7

new Hello_T
dup
invokespecial Hello_T/<init>()V
astore_1

iconst_0
iconst_5
isub
istore_2

iconst_0
istore_3

bipush 16
istore 4

iconst_5
istore 5

iconst_3
istore 6

iload_2
ifgt end_loop1
begin_loop1:

iload_2
invokestatic	io.println(I)V

iinc 2 1

iload_2
ifle begin_loop1
end_loop1:

iinc 3 1

iconst_5
istore_2

iload_2
ifle end_loop2
begin_loop2:

iload_2
invokestatic	io.println(I)V

iinc 2 -1

iload_2
ifgt begin_loop2
end_loop2:

iload_3
invokestatic	io.println(I)V

iload 4
iconst_2
ishr
istore 4

iload 5
iconst_1
ishl
istore 5

iload 6
bipush 6
ishl
istore 6

iload 4
invokestatic	io.println(I)V

iload 5
invokestatic	io.println(I)V

iload 6
invokestatic	io.println(I)V

	return
.end method
