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

bipush 10
istore_3

loop1:
iconst_0
iload_3
if_icmpge end_loop1

iload_3
invokestatic	io.println(I)V

iload_3
iconst_1
isub
istore_3

goto loop1
end_loop1:

	return
.end method
