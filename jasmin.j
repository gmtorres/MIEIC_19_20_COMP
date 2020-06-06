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
	.limit locals 3

iconst_0
istore_1

iload_1
ifle else_if1

iconst_1
invokestatic	io.println(I)V

goto end_if1

else_if1:
iconst_0
invokestatic	io.println(I)V

end_if1:

iload_1
ifge else_if2

iconst_1
invokestatic	io.println(I)V

goto end_if2

else_if2:
iconst_0
invokestatic	io.println(I)V

end_if2:

iload_1
ifgt else_if3

iconst_1
invokestatic	io.println(I)V

goto end_if3

else_if3:
iconst_0
invokestatic	io.println(I)V

end_if3:

iload_1
iflt else_if4

iconst_1
invokestatic	io.println(I)V

goto end_if4

else_if4:
iconst_0
invokestatic	io.println(I)V

end_if4:

	return
.end method

.method public prop(I)I
	.limit stack 2
	.limit locals 4

iload_1
iconst_1
iadd
istore_2

iconst_1
istore_3

iload_1
bipush 10
if_icmpge else_if5

iconst_2
istore_3

loop1:
iload_1
iconst_5
if_icmpge end_loop1
begin_loop1:

iload_1
istore_2

iinc 1 1

goto loop1
end_loop1:

iload_1
iload_3
iadd
istore_1

goto end_if5

else_if5:
iload_3
istore_1

end_if5:

iload_1
iload_3
iadd
istore_2

iload_2
ireturn

.end method

.method public sum2()I
	.limit stack 2
	.limit locals 4

iconst_5
istore_1

iconst_4
iload_1
iadd
istore_2

iload_2
iload_2
imul
istore_3

iload_3
iload_1
isub
istore_2

iload_2
iload_1
isub
istore_3

iload_2
iconst_1
iadd
istore_1

iload_1
ireturn

.end method
