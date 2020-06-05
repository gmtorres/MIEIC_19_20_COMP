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
	.limit locals 4

new Hello_T
dup
invokespecial Hello_T/<init>()V
pop

bipush -5
istore_0


iload_0
ifgt end_loop1
begin_loop1:

iload_0
invokestatic	io.println(I)V

iinc 0 1

iload_0
ifle begin_loop1
end_loop1:


iconst_5
istore_0

iload_0
ifle end_loop2
begin_loop2:

iload_0
invokestatic	io.println(I)V

iinc 0 -1

iload_0
ifgt begin_loop2
end_loop2:

iconst_1
invokestatic	io.println(I)V

	return
.end method
