.class public HSort
.super java/lang/Object


.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public printarray([I)Z
	.limit stack 5
	.limit locals 3

iconst_0
istore_2

loop1:
iload_2
aload_1
arraylength
if_icmpge end_loop1
begin_loop1:

aload_0
iload_2
aload_1
arraylength
iconst_1
isub
invokevirtual HSort.equals(II)Z
ifeq else_if1

aload_1
iload_2
iaload
invokestatic	io.println(I)V

goto end_if1

else_if1:
aload_1
iload_2
iaload
invokestatic	io.print(I)V

end_if1:

iinc 2 1

goto loop1
end_loop1:

iconst_1
ireturn

.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 3
	.limit locals 6

iconst_0
istore_3

new HSort
dup
invokespecial HSort/<init>()V
astore 5

invokestatic	ioPlus.requestNumber()I
istore_1

iload_1
newarray int
astore 4

loop2:
iload_3
iload_1
if_icmpge end_loop2
begin_loop2:

aload 4
iload_3
invokestatic	ioPlus.requestNumber()I
iastore

iinc 3 1

goto loop2
end_loop2:

aload 5
aload 4
invokevirtual HSort.printarray([I)Z

pop

aload 5
aload 4
invokevirtual HSort.heapSort([I)Z

pop

aload 5
aload 4
invokevirtual HSort.printarray([I)Z

pop

	return
.end method

.method public equals(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual HSort.lt(II)Z
ifne else_if2
aload_0
iload_2
iload_1
invokevirtual HSort.lt(II)Z
ifne else_if2

iconst_1

goto end_if2

else_if2:
iconst_0

end_if2:
ireturn

.end method

.method public lt(II)Z
	.limit stack 2
	.limit locals 3

iload_1
iload_2
if_icmpge else_if3

iconst_1

goto end_if3

else_if3:
iconst_0

end_if3:
ireturn

.end method

.method public le(II)Z
	.limit stack 4
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual HSort.lt(II)Z
ifne or_0
aload_0
iload_1
iload_2
invokevirtual HSort.equals(II)Z
ifeq else_if4
or_0:

iconst_1

goto end_if4

else_if4:
iconst_0

end_if4:
ireturn

.end method

.method public gt(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual HSort.le(II)Z
ifne else_if5

iconst_1

goto end_if5

else_if5:
iconst_0

end_if5:
ireturn

.end method

.method public heapSort([I)Z
	.limit stack 8
	.limit locals 9

iconst_1
istore 7

iconst_1
istore 8

aload_1
arraylength
istore_2

iload_2
iconst_2
idiv
istore_3

loop3:
iconst_1
ifeq end_loop3
iload 7
ifeq end_loop3
begin_loop3:

iload_3
ifle else_if6

iinc 3 -1

aload_1
iload_3
iaload
istore 4

goto end_if6

else_if6:
iinc 2 -1

aload_0
iload_2
iconst_0
invokevirtual HSort.le(II)Z
ifeq else_if7

iconst_0
istore 7

iconst_0
istore 8

goto end_if7

else_if7:
end_if7:

iload 7
ifeq else_if8

aload_1
iload_2
iaload
istore 4

aload_1
iload_2
aload_1
iconst_0
iaload
iastore

goto end_if8

else_if8:
iconst_0
istore 4

end_if8:

end_if6:

iload 7
ifeq else_if9

iload_3
istore 5

iload_3
iconst_2
imul
iconst_1
iadd
istore 6

loop4:
iload 6
iload_2
if_icmpge end_loop4
iload 8
ifeq end_loop4
begin_loop4:

iload 6
iconst_1
iadd
iload_2
if_icmpge else_if10
aload_1
iload 6
iaload
aload_1
iload 6
iconst_1
iadd
iaload
if_icmpge else_if10

iinc 6 1

goto end_if10

else_if10:
end_if10:

iload 4
aload_1
iload 6
iaload
if_icmpge else_if11

aload_1
iload 5
aload_1
iload 6
iaload
iastore

iload 6
istore 5

iload 5
iconst_2
imul
iconst_1
iadd
istore 6

goto end_if11

else_if11:
iconst_0
istore 8

end_if11:

goto loop4
end_loop4:

aload_1
iload 5
iload 4
iastore

goto end_if9

else_if9:
end_if9:

goto loop3
end_loop3:

iconst_1
ireturn

.end method
