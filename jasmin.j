.class public Hello_T
.super java/lang/Object

.field foo I

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public inbounds(II)Z
	.limit stack 1
	.limit locals 3

iconst_1
ireturn

.end method

.method public Move(II)Z
	.limit stack 1
	.limit locals 3

iconst_1
ireturn

.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 5
	.limit locals 6

iconst_0
istore 5

new Hello_T
dup
invokespecial Hello_T/<init>()V
astore_1

loop1:
iconst_1
ifeq end_loop1

iconst_0
istore_3

loop2:
iload_3
ifne end_loop2

iload 5
invokestatic	BoardBase.playerTurn(I)[I
astore 4

aload_1
aload 4
iconst_0
iaload
aload 4
iconst_1
iaload
invokevirtual Hello_T.inbounds(II)Z
ifne else_if1

invokestatic	BoardBase.wrongMove()V

goto end_if1

else_if1:
aload_1
aload 4
iconst_0
iaload
aload 4
iconst_1
iaload
invokevirtual Hello_T.Move(II)Z
ifne else_if2

invokestatic	BoardBase.placeTaken()V

goto end_if2

else_if2:
iconst_1
istore_3

end_if2:

end_if1:

goto loop2
end_loop2:

goto loop1
end_loop1:

	return
.end method
