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
istore_2

iconst_0
istore_3

iload_2
iconst_5
if_icmpge else_if1

iconst_1
istore_3

goto end_if1

else_if1:
iconst_2
istore_3

end_if1:

	return
.end method
