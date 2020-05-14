.class public sudoku
.super java/lang/Object

.field line1 [I
.field line2 [I
.field line3 [I
.field line4 [I
.field line5 [I
.field line6 [I
.field line7 [I
.field line8 [I
.field line9 [I
.field canRun Z

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public init(I)Z
	.limit stack 2
	.limit locals 2

aload_0
bipush 9
newarray int
putfield sudoku/line1 [I

aload_0
bipush 9
newarray int
putfield sudoku/line2 [I

aload_0
bipush 9
newarray int
putfield sudoku/line3 [I

aload_0
bipush 9
newarray int
putfield sudoku/line4 [I

aload_0
bipush 9
newarray int
putfield sudoku/line5 [I

aload_0
bipush 9
newarray int
putfield sudoku/line6 [I

aload_0
bipush 9
newarray int
putfield sudoku/line7 [I

aload_0
bipush 9
newarray int
putfield sudoku/line8 [I

aload_0
bipush 9
newarray int
putfield sudoku/line9 [I

aload_0
iconst_1
putfield sudoku/canRun Z

aload_0
iload_1
invokevirtual sudoku.setLines(I)Z

pop

aload_0
invokevirtual sudoku.printBoard()Z

pop

iconst_1
ireturn

.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 2
	.limit locals 4

iconst_0
istore_3

loop1:
iload_3
bipush 10
if_icmpge end_loop1

invokestatic	ioPlus.requestNumber()I
istore_3

new sudoku
dup
invokespecial sudoku/<init>()V
astore_2

aload_2
iload_3
invokevirtual sudoku.init(I)Z

<<<<<<< HEAD
iinc 3 1
=======
pop

loop1:
aload_2
invokevirtual sudoku.canRun()Z
ifeq end_loop1

aload_2
invokevirtual sudoku.run()Z

pop
>>>>>>> 5b82f704ff10759132b7e2e2e9839b97baa49d33

goto loop1
end_loop1:

bipush 10
istore_3

loop2:
iconst_0
iload_3
if_icmpge end_loop2

iload_3
invokestatic	io.println(I)V

iinc 3 -1

goto loop2
end_loop2:

	return
.end method

.method public setLines(I)Z
	.limit stack 5
	.limit locals 2

aload_0
iconst_1
iload_1
invokevirtual sudoku.eq(II)Z
ifeq else_if1

aload_0
getfield sudoku/line1 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_4
bipush 6
iastore

aload_0
getfield sudoku/line1 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
bipush 6
bipush 8
iastore

aload_0
getfield sudoku/line1 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
iconst_0
bipush 7
iastore

aload_0
getfield sudoku/line2 [I
iconst_1
iconst_1
iastore

aload_0
getfield sudoku/line2 [I
iconst_2
iconst_3
iastore

aload_0
getfield sudoku/line2 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
bipush 6
iconst_2
iastore

aload_0
getfield sudoku/line2 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_0
iconst_5
iastore

aload_0
getfield sudoku/line3 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_3
iconst_2
iastore

aload_0
getfield sudoku/line3 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
bipush 6
iconst_1
iastore

aload_0
getfield sudoku/line3 [I
bipush 7
iconst_3
iastore

aload_0
getfield sudoku/line3 [I
bipush 8
bipush 9
iastore

aload_0
getfield sudoku/line4 [I
iconst_0
bipush 8
iastore

aload_0
getfield sudoku/line4 [I
iconst_1
iconst_3
iastore

aload_0
getfield sudoku/line4 [I
iconst_2
iconst_5
iastore

aload_0
getfield sudoku/line4 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
iconst_4
iconst_4
iastore

aload_0
getfield sudoku/line4 [I
iconst_5
bipush 6
iastore

aload_0
getfield sudoku/line4 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
bipush 8
iconst_2
iastore

aload_0
getfield sudoku/line5 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_1
bipush 7
iastore

aload_0
getfield sudoku/line5 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_4
iconst_1
iastore

aload_0
getfield sudoku/line5 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
bipush 7
iconst_4
iastore

aload_0
getfield sudoku/line5 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_0
iconst_4
iastore

aload_0
getfield sudoku/line6 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_3
bipush 9
iastore

aload_0
getfield sudoku/line6 [I
iconst_4
iconst_3
iastore

aload_0
getfield sudoku/line6 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
bipush 6
bipush 6
iastore

aload_0
getfield sudoku/line6 [I
bipush 7
bipush 7
iastore

aload_0
getfield sudoku/line6 [I
bipush 8
iconst_5
iastore

aload_0
getfield sudoku/line7 [I
iconst_0
bipush 6
iastore

aload_0
getfield sudoku/line7 [I
iconst_1
bipush 8
iastore

aload_0
getfield sudoku/line7 [I
iconst_2
iconst_2
iastore

aload_0
getfield sudoku/line7 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
iconst_5
iconst_3
iastore

aload_0
getfield sudoku/line7 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
bipush 8
iconst_1
iastore

aload_0
getfield sudoku/line8 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_2
iconst_4
iastore

aload_0
getfield sudoku/line8 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
bipush 6
iconst_5
iastore

aload_0
getfield sudoku/line8 [I
bipush 7
iconst_2
iastore

aload_0
getfield sudoku/line8 [I
bipush 8
iconst_3
iastore

aload_0
getfield sudoku/line9 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_2
bipush 7
iastore

aload_0
getfield sudoku/line9 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_4
iconst_2
iastore

aload_0
getfield sudoku/line9 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
bipush 8
iconst_0
iastore

goto end_if1

else_if1:
aload_0
iconst_2
iload_1
invokevirtual sudoku.eq(II)Z
ifeq else_if2

aload_0
getfield sudoku/line1 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_1
bipush 6
iastore

aload_0
getfield sudoku/line1 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_3
bipush 9
iastore

aload_0
getfield sudoku/line1 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_5
iconst_4
iastore

aload_0
getfield sudoku/line1 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
bipush 8
iconst_1
iastore

aload_0
getfield sudoku/line2 [I
iconst_0
iconst_1
iastore

aload_0
getfield sudoku/line2 [I
iconst_1
bipush 8
iastore

aload_0
getfield sudoku/line2 [I
iconst_2
iconst_3
iastore

aload_0
getfield sudoku/line2 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
iconst_4
iconst_5
iastore

aload_0
getfield sudoku/line2 [I
iconst_5
bipush 7
iastore

aload_0
getfield sudoku/line2 [I
bipush 6
bipush 6
iastore

aload_0
getfield sudoku/line2 [I
bipush 7
iconst_4
iastore

aload_0
getfield sudoku/line2 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_2
iconst_4
iastore

aload_0
getfield sudoku/line3 [I
iconst_3
bipush 6
iastore

aload_0
getfield sudoku/line3 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
bipush 8
bipush 7
iastore

aload_0
getfield sudoku/line4 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
iconst_1
iconst_2
iastore

aload_0
getfield sudoku/line4 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
iconst_5
bipush 6
iastore

aload_0
getfield sudoku/line4 [I
bipush 6
bipush 7
iastore

aload_0
getfield sudoku/line4 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_0
bipush 6
iastore

aload_0
getfield sudoku/line5 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
bipush 6
iconst_2
iastore

aload_0
getfield sudoku/line5 [I
bipush 7
iconst_1
iastore

aload_0
getfield sudoku/line5 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_0
bipush 7
iastore

aload_0
getfield sudoku/line6 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
iconst_0
iconst_5
iastore

aload_0
getfield sudoku/line7 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
iconst_4
bipush 7
iastore

aload_0
getfield sudoku/line7 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
bipush 8
iconst_2
iastore

aload_0
getfield sudoku/line8 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_3
iconst_4
iastore

aload_0
getfield sudoku/line8 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_5
bipush 9
iastore

aload_0
getfield sudoku/line8 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_2
iconst_2
iastore

aload_0
getfield sudoku/line9 [I
iconst_3
iconst_5
iastore

aload_0
getfield sudoku/line9 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
bipush 6
iconst_3
iastore

aload_0
getfield sudoku/line9 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
bipush 8
iconst_4
iastore

goto end_if2

else_if2:
aload_0
getfield sudoku/line1 [I
iconst_0
iconst_4
iastore

aload_0
getfield sudoku/line1 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
bipush 6
bipush 8
iastore

aload_0
getfield sudoku/line1 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line1 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
iconst_1
bipush 6
iastore

aload_0
getfield sudoku/line2 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
iconst_4
iconst_5
iastore

aload_0
getfield sudoku/line2 [I
iconst_5
iconst_1
iastore

aload_0
getfield sudoku/line2 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line2 [I
bipush 8
bipush 8
iastore

aload_0
getfield sudoku/line3 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line3 [I
bipush 6
iconst_4
iastore

aload_0
getfield sudoku/line3 [I
bipush 7
iconst_2
iastore

aload_0
getfield sudoku/line3 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
iconst_1
bipush 7
iastore

aload_0
getfield sudoku/line4 [I
iconst_2
iconst_4
iastore

aload_0
getfield sudoku/line4 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line4 [I
bipush 8
iconst_1
iastore

aload_0
getfield sudoku/line5 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_2
iconst_1
iastore

aload_0
getfield sudoku/line5 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
iconst_5
bipush 7
iastore

aload_0
getfield sudoku/line5 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line5 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
iconst_2
bipush 8
iastore

aload_0
getfield sudoku/line6 [I
iconst_3
bipush 8
iastore

aload_0
getfield sudoku/line6 [I
iconst_4
iconst_1
iastore

aload_0
getfield sudoku/line6 [I
iconst_5
iconst_3
iastore

aload_0
getfield sudoku/line6 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line6 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
iconst_0
bipush 8
iastore

aload_0
getfield sudoku/line7 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
iconst_3
iconst_5
iastore

aload_0
getfield sudoku/line7 [I
iconst_4
bipush 9
iastore

aload_0
getfield sudoku/line7 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line7 [I
bipush 8
iconst_3
iastore

aload_0
getfield sudoku/line8 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line8 [I
bipush 8
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_0
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_1
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_2
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_3
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_4
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
iconst_5
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
bipush 6
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
bipush 7
iconst_0
iastore

aload_0
getfield sudoku/line9 [I
bipush 8
iconst_0
iastore

end_if2:

end_if1:

iconst_1
ireturn

.end method

.method public canRun()Z
	.limit stack 1
	.limit locals 1

aload_0
getfield sudoku/canRun Z
ireturn

.end method

.method public printLine([I)Z
	.limit stack 2
	.limit locals 3

iconst_0
istore_2

loop2:
iload_2
bipush 9
if_icmpge end_loop2

aload_1
iload_2
iaload
invokestatic	io.print(I)V

iload_2
iconst_1
iadd
istore_2

goto loop2
end_loop2:

invokestatic	io.println()V

iconst_1
ireturn

.end method

.method public printBoard()Z
	.limit stack 2
	.limit locals 1

aload_0
aload_0
getfield sudoku/line1 [I
invokevirtual sudoku.printLine([I)Z

pop

aload_0
aload_0
getfield sudoku/line2 [I
invokevirtual sudoku.printLine([I)Z

pop

aload_0
aload_0
getfield sudoku/line3 [I
invokevirtual sudoku.printLine([I)Z

pop

aload_0
aload_0
getfield sudoku/line4 [I
invokevirtual sudoku.printLine([I)Z

pop

aload_0
aload_0
getfield sudoku/line5 [I
invokevirtual sudoku.printLine([I)Z

pop

aload_0
aload_0
getfield sudoku/line6 [I
invokevirtual sudoku.printLine([I)Z

pop

aload_0
aload_0
getfield sudoku/line7 [I
invokevirtual sudoku.printLine([I)Z

pop

aload_0
aload_0
getfield sudoku/line8 [I
invokevirtual sudoku.printLine([I)Z

pop

aload_0
aload_0
getfield sudoku/line9 [I
invokevirtual sudoku.printLine([I)Z

pop

iconst_1
ireturn

.end method

.method public getLine(I)[I
	.limit stack 10
	.limit locals 3

aload_0
iconst_1
iload_1
invokevirtual sudoku.eq(II)Z
ifeq else_if3

aload_0
getfield sudoku/line1 [I
astore_2

goto end_if3

else_if3:
aload_0
iconst_2
iload_1
invokevirtual sudoku.eq(II)Z
ifeq else_if4

aload_0
getfield sudoku/line2 [I
astore_2

goto end_if4

else_if4:
aload_0
iconst_3
iload_1
invokevirtual sudoku.eq(II)Z
ifeq else_if5

aload_0
getfield sudoku/line3 [I
astore_2

goto end_if5

else_if5:
aload_0
iconst_4
iload_1
invokevirtual sudoku.eq(II)Z
ifeq else_if6

aload_0
getfield sudoku/line4 [I
astore_2

goto end_if6

else_if6:
aload_0
iconst_5
iload_1
invokevirtual sudoku.eq(II)Z
ifeq else_if7

aload_0
getfield sudoku/line5 [I
astore_2

goto end_if7

else_if7:
aload_0
bipush 6
iload_1
invokevirtual sudoku.eq(II)Z
ifeq else_if8

aload_0
getfield sudoku/line6 [I
astore_2

goto end_if8

else_if8:
aload_0
bipush 7
iload_1
invokevirtual sudoku.eq(II)Z
ifeq else_if9

aload_0
getfield sudoku/line7 [I
astore_2

goto end_if9

else_if9:
aload_0
bipush 8
iload_1
invokevirtual sudoku.eq(II)Z
ifeq else_if10

aload_0
getfield sudoku/line8 [I
astore_2

goto end_if10

else_if10:
aload_0
getfield sudoku/line9 [I
astore_2

end_if10:

end_if9:

end_if8:

end_if7:

end_if6:

end_if5:

end_if4:

end_if3:

aload_2
areturn

.end method

.method public setLine(III)Z
	.limit stack 3
	.limit locals 5

aload_0
iload_1
invokevirtual sudoku.getLine(I)[I
astore 4

aload 4
iload_2
iload_3
iastore

iconst_1
ireturn

.end method

.method public setPosition()Z
	.limit stack 6
	.limit locals 5

invokestatic	ioPlus.requestNumber()I
istore_2

invokestatic	ioPlus.requestNumber()I
istore_3

invokestatic	ioPlus.requestNumber()I
istore 4

iload 4
iconst_1
isub
istore 4

aload_0
iload_2
invokevirtual sudoku.validateInput(I)Z
ifeq else_if11
aload_0
iload_3
invokevirtual sudoku.validateInput(I)Z
ifeq else_if11
aload_0
iload 4
invokevirtual sudoku.validateInput(I)Z
ifeq else_if11

aload_0
iload_3
iload 4
iload_2
invokevirtual sudoku.validatePlay(III)Z
ifeq else_if12

aload_0
iload_3
iload 4
iload_2
invokevirtual sudoku.setLine(III)Z

pop

iconst_1
istore_1

goto end_if12

else_if12:
iconst_0
istore_1

end_if12:

goto end_if11

else_if11:
iconst_0
istore_1

end_if11:

iload_1
ireturn

.end method

.method public validatePlay(III)Z
	.limit stack 5
	.limit locals 7

iconst_0
istore 6

iconst_1
istore 4

aload_0
iload_1
invokevirtual sudoku.getLine(I)[I
astore 5

aload_0
aload 5
iload_2
iaload
iconst_0
invokevirtual sudoku.eq(II)Z
ifeq else_if13

aload_0
iload_1
iload_2
iload_3
invokevirtual sudoku.validateSquare(III)Z
ifeq else_if14
aload_0
iload_2
iload_3
invokevirtual sudoku.validateColumn(II)Z
ifeq else_if14

loop3:
iload 6
bipush 9
if_icmpge end_loop3
iload 4
ifeq end_loop3

aload_0
aload 5
iload 6
iaload
iload_3
invokevirtual sudoku.eq(II)Z
ifeq else_if15

invokestatic	BoardBase.wrongMove()V

iconst_0
istore 4

goto end_if15

else_if15:
end_if15:

iload 6
iconst_1
iadd
istore 6

goto loop3
end_loop3:

goto end_if14

else_if14:
invokestatic	BoardBase.wrongMove()V

iconst_0
istore 4

end_if14:

goto end_if13

else_if13:
invokestatic	BoardBase.placeTaken()V

iconst_0
istore 4

end_if13:

iload 4
ireturn

.end method

.method public validateColumn(II)Z
	.limit stack 3
	.limit locals 6

iconst_1
istore 4

iconst_1
istore_3

loop4:
iload 4
bipush 10
if_icmpge end_loop4
iload_3
ifeq end_loop4

aload_0
iload 4
invokevirtual sudoku.getLine(I)[I
astore 5

aload_0
aload 5
iload_1
iaload
iload_2
invokevirtual sudoku.eq(II)Z
ifeq else_if16

iconst_0
istore_3

goto end_if16

else_if16:
end_if16:

iload 4
iconst_1
iadd
istore 4

goto loop4
end_loop4:

iload_3
ireturn

.end method

.method public validateSquare(III)Z
	.limit stack 4
	.limit locals 11

iconst_1
istore 4

aload_0
iconst_1
iload_1
invokevirtual sudoku.ge(II)Z
ifeq else_if17
aload_0
iconst_3
iload_1
invokevirtual sudoku.le(II)Z
ifeq else_if17

sipush 7777
invokestatic	io.print(I)V

iconst_1
istore 5

iconst_4
istore 10

goto end_if17

else_if17:
aload_0
iconst_4
iload_1
invokevirtual sudoku.ge(II)Z
ifeq else_if18
aload_0
bipush 6
iload_1
invokevirtual sudoku.le(II)Z
ifeq else_if18

iconst_4
istore 5

bipush 7
istore 10

goto end_if18

else_if18:
bipush 7
istore 5

bipush 10
istore 10

end_if18:

end_if17:

aload_0
iconst_0
iload_2
invokevirtual sudoku.ge(II)Z
ifeq else_if19
aload_0
iconst_2
iload_2
invokevirtual sudoku.le(II)Z
ifeq else_if19

sipush 8888
invokestatic	io.print(I)V

iconst_0
istore 6

iconst_0
istore 7

iconst_3
istore 9

goto end_if19

else_if19:
aload_0
iconst_3
iload_2
invokevirtual sudoku.ge(II)Z
ifeq else_if20
aload_0
iconst_5
iload_2
invokevirtual sudoku.le(II)Z
ifeq else_if20

iconst_3
istore 6

iconst_3
istore 7

bipush 6
istore 9

goto end_if20

else_if20:
bipush 6
istore 6

bipush 6
istore 7

bipush 9
istore 9

end_if20:

end_if19:

loop5:
iload 5
iload 10
if_icmpge end_loop5
iload 4
ifeq end_loop5

aload_0
iload 5
invokevirtual sudoku.getLine(I)[I
astore 8

loop6:
iload 6
iload 9
if_icmpge end_loop6
iload 4
ifeq end_loop6

aload_0
aload 8
iload 6
iaload
iload_3
invokevirtual sudoku.eq(II)Z
ifeq else_if21

iconst_0
istore 4

goto end_if21

else_if21:
end_if21:

iload 6
iconst_1
iadd
istore 6

goto loop6
end_loop6:

iload 5
iconst_1
iadd
istore 5

iload 7
istore 6

goto loop5
end_loop5:

iload 4
ireturn

.end method

.method public validateInput(I)Z
	.limit stack 4
	.limit locals 3

aload_0
iconst_1
iload_1
invokevirtual sudoku.lt(II)Z
ifne or_0
aload_0
bipush 9
iload_1
invokevirtual sudoku.gt(II)Z
ifeq else_if22
or_0:

iconst_1
istore_2

goto end_if22

else_if22:
invokestatic	BoardBase.wrongMove()V

iconst_0
istore_2

end_if22:

iload_2
ireturn

.end method

.method public validateBoard()Z
	.limit stack 4
	.limit locals 5

iconst_1
istore_2

iconst_1
istore_1

loop7:
iload_1
bipush 10
if_icmpge end_loop7
iload_2
ifeq end_loop7

aload_0
iload_1
invokevirtual sudoku.getLine(I)[I
astore_3

iconst_0
istore 4

loop8:
iload 4
bipush 9
if_icmpge end_loop8

aload_0
iconst_0
aload_3
iload 4
iaload
invokevirtual sudoku.eq(II)Z
ifeq else_if23

iconst_0
istore_2

goto end_if23

else_if23:
end_if23:

iload 4
iconst_1
iadd
istore 4

goto loop8
end_loop8:

iconst_0
istore 4

iload_1
iconst_1
iadd
istore_1

goto loop7
end_loop7:

iload_2
ireturn

.end method

.method public run()Z
	.limit stack 3
	.limit locals 1

loop9:
aload_0
invokevirtual sudoku.setPosition()Z
ifne end_loop9

goto loop9
end_loop9:

aload_0
invokevirtual sudoku.printBoard()Z

pop

aload_0
invokevirtual sudoku.validateBoard()Z
ifeq else_if24

aload_0
iconst_0
putfield sudoku/canRun Z

goto end_if24

else_if24:
end_if24:

iconst_1
ireturn

.end method

.method public eq(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual sudoku.lt(II)Z
ifne else_if25
aload_0
iload_2
iload_1
invokevirtual sudoku.lt(II)Z
ifne else_if25

iconst_1

goto end_if25

else_if25:
iconst_0

end_if25:
ireturn

.end method

.method public ne(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual sudoku.eq(II)Z
ifne else_if26

iconst_1

goto end_if26

else_if26:
iconst_0

end_if26:
ireturn

.end method

.method public lt(II)Z
	.limit stack 2
	.limit locals 3

iload_1
iload_2
if_icmpge else_if27

iconst_1

goto end_if27

else_if27:
iconst_0

end_if27:
ireturn

.end method

.method public le(II)Z
	.limit stack 4
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual sudoku.lt(II)Z
ifne or_1
aload_0
iload_1
iload_2
invokevirtual sudoku.eq(II)Z
ifeq else_if28
or_1:

iconst_1

goto end_if28

else_if28:
iconst_0

end_if28:
ireturn

.end method

.method public gt(II)Z
	.limit stack 2
	.limit locals 3

iload_1
iload_2
if_icmple else_if29

iconst_1

goto end_if29

else_if29:
iconst_0

end_if29:
ireturn

.end method

.method public ge(II)Z
	.limit stack 4
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual sudoku.gt(II)Z
ifne or_2
aload_0
iload_1
iload_2
invokevirtual sudoku.eq(II)Z
ifeq else_if30
or_2:

iconst_1

goto end_if30

else_if30:
iconst_0

end_if30:
ireturn

.end method
