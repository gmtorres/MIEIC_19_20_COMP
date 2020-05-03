.class public ImportStressTest
.super java/lang/Object


.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 2
	.limit locals 3

iconst_3
iconst_4
iadd

pop

new List
dup
invokespecial List/<init>()V
astore_2

new List
dup
iconst_2
invokespecial List/<init>(I)V
astore_2

iconst_2
iconst_3
invokestatic	List.add(II)V

iconst_2
iconst_5
invokestatic	List.add(II)V

	return
.end method
