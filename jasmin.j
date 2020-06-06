.class public HSort
.super java/lang/Object


.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public heapify([III)Z
	.limit stack 4
	.limit locals 8

iconst_2
iload_2
imul
iconst_1
iadd
istore 4

iconst_2
iload_2
imul
iconst_2
iadd
istore 5

iload 4
iload_3
if_icmpge else_if1
aload_1
iload 4
iaload
aload_1
iload_2
iaload
if_icmple else_if1

iload 4
istore 7

goto end_if1

else_if1:
iload_2
istore 7

end_if1:

iload 5
iload_3
if_icmpge else_if2
aload_1
iload 5
iaload
aload_1
iload 7
iaload
if_icmple else_if2

iload 5
istore 7

goto end_if2

else_if2:
iload 7
iload_2
invokestatic	sort.equals(II)Z
ifne else_if3

aload_1
iload 7
iaload
istore 6

aload_1
iload 7
aload_1
iload_2
iaload
iastore

aload_1
iload_2
iload 6
iastore

aload_0
aload_1
iload 7
iload_3
invokevirtual HSort.heapify([III)Z

pop

goto end_if3

else_if3:
end_if3:

end_if2:

iconst_1
ireturn

.end method

.method public bheap([I)Z
	.limit stack 5
	.limit locals 3

aload_1
arraylength
iconst_2
idiv
iconst_1
isub
istore_2

iload_2
ifgt or_0
iload_2
iconst_0
invokestatic	sort.equals(II)Z
ifeq end_loop1
or_0:
begin_loop1:

aload_0
aload_1
iload_2
aload_1
arraylength
invokevirtual HSort.heapify([III)Z

pop

iinc 2 -1

iload_2
ifgt begin_loop1
iload_2
iconst_0
invokestatic	sort.equals(II)Z
ifne begin_loop1
end_loop1:

iconst_1
ireturn

.end method

.method public Sort([I)Z
	.limit stack 5
	.limit locals 5

aload_0
aload_1
invokevirtual HSort.bheap([I)Z

pop

aload_1
arraylength
iconst_1
isub
istore 4

iload 4
ifle end_loop2
begin_loop2:

aload_1
iconst_0
iaload
istore_2

aload_1
iconst_0
aload_1
iload 4
iaload
iastore

aload_1
iload 4
iload_2
iastore

iinc 4 -1

aload_0
aload_1
iconst_0
iload 4
invokevirtual HSort.heapify([III)Z

pop

iload 4
ifgt begin_loop2
end_loop2:

iconst_1
ireturn

.end method

.method public printarray([I)Z
	.limit stack 3
	.limit locals 3

iconst_0
istore_2

iload_2
aload_1
arraylength
if_icmpge end_loop3
begin_loop3:

aload_1
iload_2
iaload
invokestatic	io.print(I)V

iinc 2 1

iload_2
aload_1
arraylength
if_icmplt begin_loop3
end_loop3:

iconst_1
ireturn

.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 3
	.limit locals 6

new HSort
dup
invokespecial HSort/<init>()V
astore 5

invokestatic	ioPlus.requestNumber()I
istore_1

iload_1
newarray int
astore 4

ldc "Enter elements "
invokestatic	io.print(Ljava/lang/String;)V

iconst_0
istore_3

iload_3
iload_1
if_icmpge end_loop4
begin_loop4:

aload 4
iload_3
invokestatic	ioPlus.requestNumber()I
iastore

iinc 3 1

iload_3
iload_1
if_icmplt begin_loop4
end_loop4:

ldc "elements in array "
invokestatic	io.print(Ljava/lang/String;)V

aload 5
aload 4
invokevirtual HSort.printarray([I)Z

pop

aload 5
aload 4
invokevirtual HSort.Sort([I)Z

pop

ldc "elements after sorting"
invokestatic	io.print(Ljava/lang/String;)V

aload 5
aload 4
invokevirtual HSort.printarray([I)Z

pop

	return
.end method
