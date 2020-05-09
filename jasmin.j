.class public Hello_T
.super java/lang/Object

.field i I
.field num I

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public ackermann(II)I
	.limit stack 6
	.limit locals 4

iload_1
iconst_1
if_icmpge else_if1

iload_2
iconst_1
iadd
istore_3

goto end_if1

else_if1:
iload_2
iconst_1
if_icmpge else_if2

aload_0
iload_1
iconst_1
isub
iconst_1
invokevirtual Hello_T.ackermann(II)I
istore_3

goto end_if2

else_if2:
aload_0
iload_1
iconst_1
isub
aload_0
iload_1
iload_2
iconst_1
isub
invokevirtual Hello_T.ackermann(II)I
invokevirtual Hello_T.ackermann(II)I
istore_3

end_if2:

end_if1:

iload_3
ireturn

.end method

.method public getInst(I)LHello_T;
	.limit stack 2
	.limit locals 3

new Hello_T
dup
invokespecial Hello_T/<init>()V
astore_2

aload_2
iload_1
invokevirtual Hello_T.setI(I)I

pop

aload_2
areturn

.end method

.method public setI(I)I
	.limit stack 2
	.limit locals 2

aload_0
iload_1
putfield Hello_T/i I

aload_0
getfield Hello_T/i I
ireturn

.end method

.method public b()Z
	.limit stack 1
	.limit locals 1

iconst_1
ireturn

.end method

.method public val()I
	.limit stack 1
	.limit locals 1

aload_0
getfield Hello_T/i I
ireturn

.end method

.method public eq(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual Hello_T.lt(II)Z
ifne else_if3
not_1:
aload_0
iload_2
iload_1
invokevirtual Hello_T.lt(II)Z
ifne else_if3
not_2:

iconst_1

goto end_if3

else_if3:
iconst_0

end_if3:
ireturn

.end method

.method public ne(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual Hello_T.eq(II)Z
ifne else_if4
not_3:

iconst_1

goto end_if4

else_if4:
iconst_0

end_if4:
ireturn

.end method

.method public lt(II)Z
	.limit stack 2
	.limit locals 3

iload_1
iload_2
if_icmpge else_if5

iconst_1

goto end_if5

else_if5:
iconst_0

end_if5:
ireturn

.end method

.method public gt(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual Hello_T.le(II)Z
ifne else_if6
not_4:

iconst_1

goto end_if6

else_if6:
iconst_0

end_if6:
ireturn

.end method

.method public ge(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual Hello_T.gt(II)Z
ifne not_6
not_6:
aload_0
iload_1
iload_2
invokevirtual Hello_T.eq(II)Z
ifne not_7
not_7:
goto else_if7
not_7:

iconst_1

goto end_if7

else_if7:
iconst_0

end_if7:
ireturn

.end method

.method public le(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual Hello_T.lt(II)Z
ifne not_9
not_9:
aload_0
iload_1
iload_2
invokevirtual Hello_T.eq(II)Z
ifne not_10
not_10:
goto else_if8
not_10:

iconst_1

goto end_if8

else_if8:
iconst_0

end_if8:
ireturn

.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 6
	.limit locals 7

iconst_0
istore_2

bipush 10
istore_3

iconst_0
istore 4

iconst_1
istore 5

new Hello_T
dup
invokespecial Hello_T/<init>()V
iload_2
iload_3
invokevirtual Hello_T.lt(II)Z
ifeq else_if9

iconst_1
istore 6

goto end_if9

else_if9:
iconst_0
istore 6

end_if9:

iload 6
invokestatic	io.println(I)V

new Hello_T
dup
invokespecial Hello_T/<init>()V
iload_2
iload_3
invokevirtual Hello_T.eq(II)Z
ifeq else_if10

iconst_1
istore 6

goto end_if10

else_if10:
iconst_0
istore 6

end_if10:

iload 6
invokestatic	io.println(I)V

new Hello_T
dup
invokespecial Hello_T/<init>()V
iload_2
iload_3
invokevirtual Hello_T.le(II)Z
ifeq else_if11

iconst_1
istore 6

goto end_if11

else_if11:
iconst_0
istore 6

end_if11:

iload 6
invokestatic	io.println(I)V

new Hello_T
dup
invokespecial Hello_T/<init>()V
iload_2
iload_3
invokevirtual Hello_T.gt(II)Z
ifeq else_if12

iconst_1
istore 6

goto end_if12

else_if12:
iconst_0
istore 6

end_if12:

iload 6
invokestatic	io.println(I)V

iload 4
ifne not_12
not_12:
iload 5
ifne not_13
not_13:
goto else_if13
not_13:

iconst_1
istore 6

goto end_if13

else_if13:
iconst_0
istore 6

end_if13:

iload 6
invokestatic	io.println(I)V

	return
.end method
