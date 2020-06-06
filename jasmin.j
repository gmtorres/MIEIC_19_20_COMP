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
	.limit locals 1

iconst_0
istore_0

iload_0
bipush 20
if_icmpge end_loop1
begin_loop1:

iinc 0 1

iload_0
invokestatic	io.println(I)V

iload_0
bipush 20
if_icmpge end_loop1
iinc 0 1

iload_0
invokestatic	io.println(I)V

iload_0
bipush 20
if_icmpge end_loop1
iinc 0 1

iload_0
invokestatic	io.println(I)V

iload_0
bipush 20
if_icmpge end_loop1
iinc 0 1

iload_0
invokestatic	io.println(I)V

iload_0
bipush 20
if_icmpge end_loop1
iinc 0 1

iload_0
invokestatic	io.println(I)V

iload_0
bipush 20
if_icmplt begin_loop1
end_loop1:

iload_0
invokestatic	io.println(I)V

	return
.end method

.method public prop(I)I
	.limit stack 2
	.limit locals 3

iload_1
iconst_1
iadd
pop

iconst_1
istore_2

iload_1
bipush 10
if_icmpge else_if5

iconst_2
istore_2

iload_1
iconst_5
if_icmpge end_loop2
begin_loop2:

iload_1
pop

iinc 1 1

iload_1
iconst_5
if_icmpge end_loop2
iload_1
pop

iinc 1 1

iload_1
iconst_5
if_icmpge end_loop2
iload_1
pop

iinc 1 1

iload_1
iconst_5
if_icmpge end_loop2
iload_1
pop

iinc 1 1

iload_1
iconst_5
if_icmpge end_loop2
iload_1
pop

iinc 1 1

iload_1
iconst_5
if_icmplt begin_loop2
end_loop2:

iinc 1 2

goto end_if5

else_if5:
iconst_1
istore_1

end_if5:

iload_1
iload_2
iadd
istore_2

iload_2
ireturn

.end method

.method public sum2()I
	.limit stack 1
	.limit locals 1

iconst_5
pop

bipush 9
pop

bipush 81
pop

bipush 76
pop

bipush 71
pop

bipush 77
pop

bipush 77
ireturn

.end method
