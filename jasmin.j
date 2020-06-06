.class public TicTacToe
.super java/lang/Object

.field row0 [I
.field row1 [I
.field row2 [I
.field whoseturn I
.field movesmade I
.field pieces [I

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public init()Z
	.limit stack 3
	.limit locals 1

aload_0
iconst_3
newarray int
putfield TicTacToe/row0 [I

aload_0
iconst_3
newarray int
putfield TicTacToe/row1 [I

aload_0
iconst_3
newarray int
putfield TicTacToe/row2 [I

aload_0
iconst_2
newarray int
putfield TicTacToe/pieces [I

aload_0
getfield TicTacToe/pieces [I
iconst_0
iconst_1
iastore

aload_0
getfield TicTacToe/pieces [I
iconst_1
iconst_2
iastore

aload_0
iconst_0
putfield TicTacToe/whoseturn I

aload_0
iconst_0
putfield TicTacToe/movesmade I

iconst_1
ireturn

.end method

.method public getRow0()[I
	.limit stack 1
	.limit locals 1

aload_0
getfield TicTacToe/row0 [I
areturn

.end method

.method public getRow1()[I
	.limit stack 1
	.limit locals 1

aload_0
getfield TicTacToe/row1 [I
areturn

.end method

.method public getRow2()[I
	.limit stack 1
	.limit locals 1

aload_0
getfield TicTacToe/row2 [I
areturn

.end method

.method public MoveRow([II)Z
	.limit stack 4
	.limit locals 4

iload_2
ifge else_if1

iconst_0
istore_3

goto end_if1

else_if1:
iconst_2
iload_2
if_icmpge else_if2

iconst_0
istore_3

goto end_if2

else_if2:
aload_1
iload_2
iaload
ifle else_if3

iconst_0
istore_3

goto end_if3

else_if3:
aload_1
iload_2
aload_0
getfield TicTacToe/pieces [I
aload_0
getfield TicTacToe/whoseturn I
iaload
iastore

aload_0
aload_0
getfield TicTacToe/movesmade I
iconst_1
iadd
putfield TicTacToe/movesmade I

iconst_1
istore_3

end_if3:

end_if2:

end_if1:

iload_3
ireturn

.end method

.method public Move(II)Z
	.limit stack 3
	.limit locals 4

iload_1
iflt else_if4
iload_1
ifgt else_if4

aload_0
aload_0
getfield TicTacToe/row0 [I
iload_2
invokevirtual TicTacToe.MoveRow([II)Z
istore_3

goto end_if4

else_if4:
iload_1
iconst_1
if_icmplt else_if5
iconst_1
iload_1
if_icmplt else_if5

aload_0
aload_0
getfield TicTacToe/row1 [I
iload_2
invokevirtual TicTacToe.MoveRow([II)Z
istore_3

goto end_if5

else_if5:
iload_1
iconst_2
if_icmplt else_if6
iconst_2
iload_1
if_icmplt else_if6

aload_0
aload_0
getfield TicTacToe/row2 [I
iload_2
invokevirtual TicTacToe.MoveRow([II)Z
istore_3

goto end_if6

else_if6:
iconst_0
istore_3

end_if6:

end_if5:

end_if4:

iload_3
ireturn

.end method

.method public inbounds(II)Z
	.limit stack 2
	.limit locals 4

iload_1
ifge else_if7

iconst_0
istore_3

goto end_if7

else_if7:
iload_2
ifge else_if8

iconst_0
istore_3

goto end_if8

else_if8:
iconst_2
iload_1
if_icmpge else_if9

iconst_0
istore_3

goto end_if9

else_if9:
iconst_2
iload_2
if_icmpge else_if10

iconst_0
istore_3

goto end_if10

else_if10:
iconst_1
istore_3

end_if10:

end_if9:

end_if8:

end_if7:

iload_3
ireturn

.end method

.method public getCurrentPlayer()I
	.limit stack 2
	.limit locals 1

aload_0
getfield TicTacToe/whoseturn I
iconst_1
iadd
ireturn

.end method

.method public winner()I
	.limit stack 4
	.limit locals 4

bipush -1
istore_2

iconst_3
newarray int
astore_1

aload_0
getfield TicTacToe/row0 [I
invokestatic	BoardBase.sameArray([I)Z
ifeq else_if11
aload_0
getfield TicTacToe/row0 [I
iconst_0
iaload
ifle else_if11

aload_0
getfield TicTacToe/row0 [I
iconst_0
iaload
istore_2

goto end_if11

else_if11:
aload_0
getfield TicTacToe/row1 [I
invokestatic	BoardBase.sameArray([I)Z
ifeq else_if12
aload_0
getfield TicTacToe/row1 [I
iconst_0
iaload
ifle else_if12

aload_0
getfield TicTacToe/row1 [I
iconst_0
iaload
istore_2

goto end_if12

else_if12:
aload_0
getfield TicTacToe/row2 [I
invokestatic	BoardBase.sameArray([I)Z
ifeq else_if13
aload_0
getfield TicTacToe/row2 [I
iconst_0
iaload
ifle else_if13

aload_0
getfield TicTacToe/row2 [I
iconst_0
iaload
istore_2

goto end_if13

else_if13:
iconst_0
istore_3

iload_2
iconst_1
if_icmpge end_loop1
iload_3
iconst_3
if_icmpge end_loop1
begin_loop1:

aload_1
iconst_0
aload_0
getfield TicTacToe/row0 [I
iload_3
iaload
iastore

aload_1
iconst_1
aload_0
getfield TicTacToe/row1 [I
iload_3
iaload
iastore

aload_1
iconst_2
aload_0
getfield TicTacToe/row2 [I
iload_3
iaload
iastore

aload_1
invokestatic	BoardBase.sameArray([I)Z
ifeq else_if14
aload_1
iconst_0
iaload
ifle else_if14

aload_1
iconst_0
iaload
istore_2

goto end_if14

else_if14:
end_if14:

iinc 3 1

iload_2
iconst_1
if_icmpge or_0
iload_3
iconst_3
if_icmplt begin_loop1
or_0:
end_loop1:

iload_2
iconst_1
if_icmpge else_if15

aload_1
iconst_0
aload_0
getfield TicTacToe/row0 [I
iconst_0
iaload
iastore

aload_1
iconst_1
aload_0
getfield TicTacToe/row1 [I
iconst_1
iaload
iastore

aload_1
iconst_2
aload_0
getfield TicTacToe/row2 [I
iconst_2
iaload
iastore

aload_1
invokestatic	BoardBase.sameArray([I)Z
ifeq else_if16
aload_1
iconst_0
iaload
ifle else_if16

aload_1
iconst_0
iaload
istore_2

goto end_if16

else_if16:
aload_1
iconst_0
aload_0
getfield TicTacToe/row0 [I
iconst_2
iaload
iastore

aload_1
iconst_1
aload_0
getfield TicTacToe/row1 [I
iconst_1
iaload
iastore

aload_1
iconst_2
aload_0
getfield TicTacToe/row2 [I
iconst_0
iaload
iastore

aload_1
invokestatic	BoardBase.sameArray([I)Z
ifeq else_if17
aload_1
iconst_0
iaload
ifle else_if17

aload_1
iconst_0
iaload
istore_2

goto end_if17

else_if17:
end_if17:

end_if16:

goto end_if15

else_if15:
end_if15:

end_if13:

end_if12:

end_if11:

iload_2
iconst_1
if_icmpge else_if18
aload_0
getfield TicTacToe/movesmade I
bipush 9
if_icmplt else_if18
bipush 9
aload_0
getfield TicTacToe/movesmade I
if_icmplt else_if18

iconst_0
istore_2

goto end_if18

else_if18:
end_if18:

iload_2
ireturn

.end method

.method public printArr([I)I
	.limit stack 3
	.limit locals 3

iconst_0
istore_2

iload_2
aload_1
arraylength
if_icmpge end_loop2
begin_loop2:

aload_1
iload_2
iaload
invokestatic	io.println(I)V

iinc 2 1

iload_2
aload_1
arraylength
if_icmplt begin_loop2
end_loop2:

iconst_0
ireturn

.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 4
	.limit locals 3

new TicTacToe
dup
invokespecial TicTacToe/<init>()V
astore_2

aload_2
invokevirtual TicTacToe.init()Z

pop

aload_2
invokevirtual TicTacToe.winner()I
bipush -1
if_icmplt end_loop3
bipush -1
aload_2
invokevirtual TicTacToe.winner()I
if_icmplt end_loop3
begin_loop3:

iconst_0
istore_1

iload_1
ifne end_loop4
begin_loop4:

aload_2
invokevirtual TicTacToe.getRow0()[I
aload_2
invokevirtual TicTacToe.getRow1()[I
aload_2
invokevirtual TicTacToe.getRow2()[I
invokestatic	BoardBase.printBoard([I[I[I)V

aload_2
invokevirtual TicTacToe.getCurrentPlayer()I
istore_0

iload_0
invokestatic	BoardBase.playerTurn(I)[I
astore_0

aload_2
aload_0
iconst_0
iaload
aload_0
iconst_1
iaload
invokevirtual TicTacToe.inbounds(II)Z
ifne else_if19

invokestatic	BoardBase.wrongMove()V

goto end_if19

else_if19:
aload_2
aload_0
iconst_0
iaload
aload_0
iconst_1
iaload
invokevirtual TicTacToe.Move(II)Z
ifne else_if20

invokestatic	BoardBase.placeTaken()V

goto end_if20

else_if20:
iconst_1
istore_1

end_if20:

end_if19:

iload_1
ifeq begin_loop4
end_loop4:

aload_2
invokevirtual TicTacToe.changeturn()Z

pop

aload_2
invokevirtual TicTacToe.winner()I
bipush -1
if_icmplt or_1
bipush -1
aload_2
invokevirtual TicTacToe.winner()I
if_icmpge begin_loop3
or_1:
end_loop3:

aload_2
invokevirtual TicTacToe.getRow0()[I
aload_2
invokevirtual TicTacToe.getRow1()[I
aload_2
invokevirtual TicTacToe.getRow2()[I
invokestatic	BoardBase.printBoard([I[I[I)V

aload_2
invokevirtual TicTacToe.winner()I
istore_0

iload_0
invokestatic	BoardBase.printWinner(I)V

	return
.end method

.method public changeturn()Z
	.limit stack 3
	.limit locals 1

aload_0
iconst_1
aload_0
getfield TicTacToe/whoseturn I
isub
putfield TicTacToe/whoseturn I

iconst_1
ireturn

.end method
