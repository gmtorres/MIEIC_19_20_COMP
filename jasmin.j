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

iload_3
iconst_5
if_icmpge or_0
iconst_3
iload_3
if_icmpge else_if1
iconst_2
iload_3
if_icmpge else_if1
else_if1:
or_0:
iload_3
iconst_5
if_icmpge or_1
iconst_3
iload_3
if_icmpge end_and_0
iconst_2
iload_3
if_icmplt or_1
end_and_0:
iload_3
iconst_5
if_icmplt else_if1
or_1:

iconst_1

goto end_if1

else_if1:
iconst_0

end_if1:
istore_2

	return
.end method
