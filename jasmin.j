.class public StressTest
.super java/lang/Object

.field a I
.field arr0 [I
.field arr1 [I

.method public <init>(I)V
	.limit stack 4
	.limit locals 2

aload_0
invokenonvirtual java/lang/Object/<init>()V

aload_0
iload_1
putfield StressTest/a I

aload_0
iconst_2
newarray int
putfield StressTest/arr0 [I

aload_0
iconst_5
newarray int
putfield StressTest/arr1 [I

aload_0
getfield StressTest/arr0 [I
iconst_0
iconst_1
iastore

aload_0
getfield StressTest/arr0 [I
iconst_1
iconst_2
iastore

aload_0
getfield StressTest/arr1 [I
iconst_0
aload_0
getfield StressTest/arr0 [I
iconst_0
iaload
bipush 10
imul
iastore

aload_0
getfield StressTest/arr1 [I
iconst_1
aload_0
getfield StressTest/arr0 [I
iconst_1
iaload
bipush 10
imul
iastore

	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 3
	.limit locals 7

ldc "Starting stress test"
invokestatic	io.println(Ljava/lang/String;)V

new StressTest
dup
iconst_3
invokespecial StressTest/<init>(I)V
astore 6

iconst_0
istore_3

loop1:
iconst_3
iload_3
if_icmple end_loop1

aload 6
invokevirtual StressTest.printA()LStressTest;
iload_3
invokevirtual StressTest.setA(I)LStressTest;
invokevirtual StressTest.printA()LStressTest;
iconst_0
iload_3
isub
invokevirtual StressTest.setA(I)LStressTest;

pop

iinc 3 1

goto loop1
end_loop1:

aload 6
bipush 10
invokevirtual StressTest.fibonacci(I)I
invokestatic	io.println(I)V

iconst_0
istore_3

iconst_1
istore_2

aload 6
iconst_3
invokevirtual StressTest.setA(I)LStressTest;

pop

iload_3
iconst_1
if_icmpge else_if1
iload_2
ifeq else_if1
aload 6
invokevirtual StressTest.getA()I
iconst_4
if_icmple or_0
aload 6
invokevirtual StressTest.getA()I
iconst_2
if_icmplt else_if1
or_0:

ldc "True"
invokestatic	io.println(Ljava/lang/String;)V

goto end_if1

else_if1:
ldc "False"
invokestatic	io.println(Ljava/lang/String;)V

end_if1:

iconst_0
iconst_5
isub
istore_3

loop2:
bipush 26
iload_3
iload_3
imul
if_icmple end_loop2

iload_3
iconst_0
if_icmpge else_if2

iconst_0
iload_3
isub
istore 5

goto end_if2

else_if2:
iload_3
istore 5

end_if2:

iconst_0
istore 4

loop3:
iload 4
iload 5
if_icmpge end_loop3

ldc "o"
invokestatic	io.print(Ljava/lang/String;)V

iinc 4 1

goto loop3
end_loop3:

ldc ""
invokestatic	io.println(Ljava/lang/String;)V

iinc 3 1

goto loop2
end_loop2:

aload 6
iconst_0
invokevirtual StressTest.getArray(I)[I
iconst_0
iaload
invokestatic	io.println(I)V

aload 6
iconst_1
invokevirtual StressTest.getArray(I)[I
iconst_1
iaload
invokestatic	io.println(I)V

aload 6
iconst_1
invokevirtual StressTest.getArray(I)[I
arraylength
invokestatic	io.println(I)V

ldc "Stress test finished"
invokestatic	io.println(Ljava/lang/String;)V

	return
.end method

.method public getArray(I)[I
	.limit stack 2
	.limit locals 3

iload_1
iconst_1
if_icmpge else_if3

aload_0
getfield StressTest/arr0 [I
astore_2

goto end_if3

else_if3:
aload_0
getfield StressTest/arr1 [I
astore_2

end_if3:

aload_2
areturn

.end method

.method public fibonacci(I)I
	.limit stack 4
	.limit locals 3

iload_1
iconst_3
if_icmpge else_if4

iconst_1
istore_2

goto end_if4

else_if4:
aload_0
iload_1
iconst_1
isub
invokevirtual StressTest.fibonacci(I)I
aload_0
iload_1
iconst_2
isub
invokevirtual StressTest.fibonacci(I)I
iadd
istore_2

end_if4:

iload_2
ireturn

.end method

.method public printA()LStressTest;
	.limit stack 1
	.limit locals 1

aload_0
getfield StressTest/a I
invokestatic	io.println(I)V

aload_0
areturn

.end method

.method public setA(I)LStressTest;
	.limit stack 2
	.limit locals 2

aload_0
iload_1
putfield StressTest/a I

aload_0
areturn

.end method

.method public getA()I
	.limit stack 1
	.limit locals 1

aload_0
getfield StressTest/a I
ireturn

.end method
