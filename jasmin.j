.class public ConnectFour
.super java/lang/Object

.field MAXROWS I
.field MAXCOLS I
.field row0 [I
.field row1 [I
.field row2 [I
.field row3 [I
.field row4 [I
.field row5 [I
.field currentPlayer I
.field run Z

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 2
	.limit locals 3

new ConnectFour
dup
invokespecial ConnectFour/<init>()V
astore_2

aload_2
invokevirtual ConnectFour.run()I

pop

	return
.end method

.method public init()I
	.limit stack 2
	.limit locals 1

aload_0
bipush 6
putfield ConnectFour/MAXROWS I

aload_0
bipush 7
putfield ConnectFour/MAXCOLS I

aload_0
bipush 7
newarray int
putfield ConnectFour/row0 [I

aload_0
bipush 7
newarray int
putfield ConnectFour/row1 [I

aload_0
bipush 7
newarray int
putfield ConnectFour/row2 [I

aload_0
bipush 7
newarray int
putfield ConnectFour/row3 [I

aload_0
bipush 7
newarray int
putfield ConnectFour/row4 [I

aload_0
bipush 7
newarray int
putfield ConnectFour/row5 [I

aload_0
iconst_1
putfield ConnectFour/run Z

aload_0
iconst_1
putfield ConnectFour/currentPlayer I

iconst_0
ireturn

.end method

.method public getRow(I)[I
	.limit stack 2
	.limit locals 3

iload_1
iconst_1
if_icmpge else_if1

aload_0
getfield ConnectFour/row0 [I
astore_2

goto end_if1

else_if1:
iload_1
iconst_2
if_icmpge else_if2

aload_0
getfield ConnectFour/row1 [I
astore_2

goto end_if2

else_if2:
iload_1
iconst_3
if_icmpge else_if3

aload_0
getfield ConnectFour/row2 [I
astore_2

goto end_if3

else_if3:
iload_1
iconst_4
if_icmpge else_if4

aload_0
getfield ConnectFour/row3 [I
astore_2

goto end_if4

else_if4:
iload_1
iconst_5
if_icmpge else_if5

aload_0
getfield ConnectFour/row4 [I
astore_2

goto end_if5

else_if5:
aload_0
getfield ConnectFour/row5 [I
astore_2

end_if5:

end_if4:

end_if3:

end_if2:

end_if1:

aload_2
areturn

.end method

.method public printRow([I)I
	.limit stack 3
	.limit locals 3

iconst_0
istore_2

loop1:
iload_2
aload_1
arraylength
if_icmpge end_loop1

aload_1
iload_2
iaload
invokestatic	io.print(I)V

iload_2
iconst_1
iadd
istore_2

goto loop1
end_loop1:

invokestatic	io.println()V

iconst_0
ireturn

.end method

.method public printBoard()I
	.limit stack 3
	.limit locals 2

invokestatic	io.println()V

iconst_0
istore_1

loop2:
iload_1
aload_0
getfield ConnectFour/MAXROWS I
if_icmpge end_loop2

aload_0
aload_0
iload_1
invokevirtual ConnectFour.getRow(I)[I
invokevirtual ConnectFour.printRow([I)I

pop

iload_1
iconst_1
iadd
istore_1

goto loop2
end_loop2:

iconst_0
ireturn

.end method

.method public getMove()I
	.limit stack 2
	.limit locals 3

iconst_0
istore_2

iconst_0
istore_1

loop3:
iload_2
ifne end_loop3

invokestatic	io.read()I
istore_1

iload_1
iconst_0
if_icmplt or_0
bipush 6
iload_1
if_icmpge else_if6
or_0:

invokestatic	BoardBase.wrongMove()V

goto end_if6

else_if6:
iconst_1
istore_2

end_if6:

goto loop3
end_loop3:

iload_1
ireturn

.end method

.method public putPiece(II)I
	.limit stack 3
	.limit locals 6

aload_0
getfield ConnectFour/MAXROWS I
istore_3

iconst_0
istore 5

loop4:
iload_3
iconst_0
if_icmplt end_loop4
iload 5
ifne end_loop4

iload_3
iconst_1
isub
istore_3

aload_0
iload_3
invokevirtual ConnectFour.getRow(I)[I
astore 4

aload 4
iload_1
iaload
iconst_1
if_icmpge else_if7

aload 4
iload_1
iload_2
iastore

iconst_1
istore 5

goto end_if7

else_if7:
end_if7:

goto loop4
end_loop4:

iload_3
ireturn

.end method

.method public changePlayer()I
	.limit stack 2
	.limit locals 1

aload_0
getfield ConnectFour/currentPlayer I
iconst_2
if_icmpge else_if8

aload_0
iconst_2
putfield ConnectFour/currentPlayer I

goto end_if8

else_if8:
aload_0
iconst_1
putfield ConnectFour/currentPlayer I

end_if8:

iconst_0
ireturn

.end method

.method public eq(II)Z
	.limit stack 2
	.limit locals 3

iload_1
iload_2
if_icmplt else_if9
iload_2
iload_1
if_icmplt else_if9

iconst_1

goto end_if9

else_if9:
iconst_0

end_if9:
ireturn

.end method

.method public countHorizontal(III)I
	.limit stack 3
	.limit locals 7

iconst_1
istore 4

iload_1
iconst_1
isub
istore 5

iload_2
istore 6

loop5:
iload 5
iconst_0
if_icmplt end_loop5
aload_0
aload_0
iload 6
invokevirtual ConnectFour.getRow(I)[I
iload 5
iaload
iload_3
invokevirtual ConnectFour.eq(II)Z
ifeq end_loop5

iload 4
iconst_1
iadd
istore 4

iload 5
iconst_1
isub
istore 5

goto loop5
end_loop5:

iload_1
iconst_1
iadd
istore 5

loop6:
iload 5
aload_0
getfield ConnectFour/MAXCOLS I
if_icmpge end_loop6
aload_0
aload_0
iload 6
invokevirtual ConnectFour.getRow(I)[I
iload 5
iaload
iload_3
invokevirtual ConnectFour.eq(II)Z
ifeq end_loop6

iload 4
iconst_1
iadd
istore 4

iload 5
iconst_1
iadd
istore 5

goto loop6
end_loop6:

iload 4
ireturn

.end method

.method public countVertical(III)I
	.limit stack 3
	.limit locals 7

iconst_1
istore 4

iload_1
istore 5

iload_2
iconst_1
isub
istore 6

loop7:
iload 6
iconst_0
if_icmplt end_loop7
aload_0
aload_0
iload 6
invokevirtual ConnectFour.getRow(I)[I
iload 5
iaload
iload_3
invokevirtual ConnectFour.eq(II)Z
ifeq end_loop7

iload 4
iconst_1
iadd
istore 4

iload 6
iconst_1
isub
istore 6

goto loop7
end_loop7:

iload_2
iconst_1
iadd
istore 6

loop8:
iload 6
aload_0
getfield ConnectFour/MAXROWS I
if_icmpge end_loop8
aload_0
aload_0
iload 6
invokevirtual ConnectFour.getRow(I)[I
iload 5
iaload
iload_3
invokevirtual ConnectFour.eq(II)Z
ifeq end_loop8

iload 4
iconst_1
iadd
istore 4

iload 6
iconst_1
iadd
istore 6

goto loop8
end_loop8:

iload 4
ireturn

.end method

.method public countDiagonal1(III)I
	.limit stack 4
	.limit locals 7

iconst_1
istore 4

iload_1
iconst_1
isub
istore 5

iload_2
iconst_1
isub
istore 6

loop9:
iload 6
iconst_0
if_icmplt end_loop9
iload 5
iconst_0
if_icmplt end_loop9
aload_0
aload_0
iload 6
invokevirtual ConnectFour.getRow(I)[I
iload 5
iaload
iload_3
invokevirtual ConnectFour.eq(II)Z
ifeq end_loop9

iload 4
iconst_1
iadd
istore 4

iload 6
iconst_1
isub
istore 6

iload 5
iconst_1
isub
istore 5

goto loop9
end_loop9:

iload_1
iconst_1
iadd
istore 5

iload_2
iconst_1
iadd
istore 6

loop10:
iload 6
aload_0
getfield ConnectFour/MAXROWS I
if_icmpge end_loop10
iload 5
aload_0
getfield ConnectFour/MAXCOLS I
if_icmpge end_loop10
aload_0
aload_0
iload 6
invokevirtual ConnectFour.getRow(I)[I
iload 5
iaload
iload_3
invokevirtual ConnectFour.eq(II)Z
ifeq end_loop10

iload 4
iconst_1
iadd
istore 4

iload 6
iconst_1
iadd
istore 6

iload 5
iconst_1
iadd
istore 5

goto loop10
end_loop10:

iload 4
ireturn

.end method

.method public countDiagonal2(III)I
	.limit stack 4
	.limit locals 7

iconst_1
istore 4

iload_1
iconst_1
iadd
istore 5

iload_2
iconst_1
isub
istore 6

loop11:
iload 6
iconst_0
if_icmplt end_loop11
iload 5
aload_0
getfield ConnectFour/MAXCOLS I
if_icmpge end_loop11
aload_0
aload_0
iload 6
invokevirtual ConnectFour.getRow(I)[I
iload 5
iaload
iload_3
invokevirtual ConnectFour.eq(II)Z
ifeq end_loop11

iload 4
iconst_1
iadd
istore 4

iload 6
iconst_1
isub
istore 6

iload 5
iconst_1
iadd
istore 5

goto loop11
end_loop11:

iload_1
iconst_1
isub
istore 5

iload_2
iconst_1
iadd
istore 6

loop12:
iload 6
aload_0
getfield ConnectFour/MAXROWS I
if_icmpge end_loop12
iload 5
iconst_0
if_icmplt end_loop12
aload_0
aload_0
iload 6
invokevirtual ConnectFour.getRow(I)[I
iload 5
iaload
iload_3
invokevirtual ConnectFour.eq(II)Z
ifeq end_loop12

iload 4
iconst_1
iadd
istore 4

iload 6
iconst_1
iadd
istore 6

iload 5
iconst_1
isub
istore 5

goto loop12
end_loop12:

iload 4
ireturn

.end method

.method public gameOver(III)Z
	.limit stack 4
	.limit locals 5

iconst_0
istore 4

aload_0
iload_1
iload_2
iload_3
invokevirtual ConnectFour.countHorizontal(III)I
iconst_4
if_icmplt else_if10

iconst_1
istore 4

goto end_if10

else_if10:
aload_0
iload_1
iload_2
iload_3
invokevirtual ConnectFour.countVertical(III)I
iconst_4
if_icmplt else_if11

iconst_1
istore 4

goto end_if11

else_if11:
aload_0
iload_1
iload_2
iload_3
invokevirtual ConnectFour.countDiagonal1(III)I
iconst_4
if_icmplt else_if12

iconst_1
istore 4

goto end_if12

else_if12:
aload_0
iload_1
iload_2
iload_3
invokevirtual ConnectFour.countDiagonal2(III)I
iconst_4
if_icmplt else_if13

iconst_1
istore 4

goto end_if13

else_if13:
end_if13:

end_if12:

end_if11:

end_if10:

iload 4
ireturn

.end method

.method public run()I
	.limit stack 6
	.limit locals 3

aload_0
invokevirtual ConnectFour.init()I

pop

loop13:
aload_0
getfield ConnectFour/run Z
ifeq end_loop13

aload_0
invokevirtual ConnectFour.printBoard()I

pop

aload_0
invokevirtual ConnectFour.getMove()I
istore_2

aload_0
iload_2
aload_0
getfield ConnectFour/currentPlayer I
invokevirtual ConnectFour.putPiece(II)I
istore_1

loop14:
iload_2
iconst_0
if_icmpge end_loop14

invokestatic	BoardBase.placeTaken()V

aload_0
invokevirtual ConnectFour.getMove()I
istore_2

aload_0
iload_2
aload_0
getfield ConnectFour/currentPlayer I
invokevirtual ConnectFour.putPiece(II)I
istore_1

goto loop14
end_loop14:

aload_0
aload_0
iload_2
iload_1
aload_0
getfield ConnectFour/currentPlayer I
invokevirtual ConnectFour.gameOver(III)Z
ifne else_if14

iconst_1

goto end_if14

else_if14:
iconst_0

end_if14:
putfield ConnectFour/run Z

aload_0
invokevirtual ConnectFour.changePlayer()I

pop

goto loop13
end_loop13:

aload_0
invokevirtual ConnectFour.printBoard()I

pop

aload_0
invokevirtual ConnectFour.changePlayer()I

pop

aload_0
getfield ConnectFour/currentPlayer I
invokestatic	BoardBase.printWinner(I)V

iconst_0
ireturn

.end method
