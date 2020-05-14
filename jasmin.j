.class public Hello_T
.super java/lang/Object


.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 2
	.limit locals 4

iconst_0
istore_3

loop1:
iload_3
bipush 10
if_icmpge end_loop1

iload_3
invokestatic	io.println(I)V

iinc 3 1

goto loop1
end_loop1:

bipush 10
istore_3

loop2:
iconst_0
iload_3
if_icmpge end_loop2

iload_3
invokestatic	io.println(I)V

iinc 3 -1

goto loop2
end_loop2:

	return
.end method
