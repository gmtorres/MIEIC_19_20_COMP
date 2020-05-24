.class public blackjack
.super java/lang/Object

.field cards [I
.field dealerCards [I
.field numCards I
.field numCardsDealer I
.field input I
.field game Z

.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

.method public init()Z
	.limit stack 2
	.limit locals 1

aload_0
bipush 12
newarray int
putfield blackjack/cards [I

aload_0
iconst_2
newarray int
putfield blackjack/dealerCards [I

aload_0
iconst_2
putfield blackjack/numCards I

aload_0
iconst_2
putfield blackjack/numCardsDealer I

aload_0
iconst_0
putfield blackjack/input I

aload_0
iconst_1
putfield blackjack/game Z

aload_0
invokevirtual blackjack.setCards()Z

pop

aload_0
invokevirtual blackjack.setDealerCards()Z

pop

aload_0
iconst_0
invokevirtual blackjack.printCards(I)Z

pop

iconst_1
ireturn

.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 2
	.limit locals 3

new blackjack
dup
invokespecial blackjack/<init>()V
astore_2

aload_2
invokevirtual blackjack.init()Z

pop

loop1:
aload_2
invokevirtual blackjack.game()Z
ifeq end_loop1

aload_2
invokevirtual blackjack.run()Z

pop

goto loop1
end_loop1:

	return
.end method

.method public setCards()Z
	.limit stack 4
	.limit locals 1

aload_0
getfield blackjack/cards [I
iconst_0
iconst_1
bipush 10
invokestatic	MathUtils.random(II)I
iastore

aload_0
getfield blackjack/cards [I
iconst_1
iconst_1
bipush 11
invokestatic	MathUtils.random(II)I
iastore

iconst_1
ireturn

.end method

.method public setDealerCards()Z
	.limit stack 4
	.limit locals 1

aload_0
getfield blackjack/dealerCards [I
iconst_0
iconst_1
bipush 11
invokestatic	MathUtils.random(II)I
iastore

aload_0
getfield blackjack/dealerCards [I
iconst_1
iconst_1
bipush 10
invokestatic	MathUtils.random(II)I
iastore

iconst_1
ireturn

.end method

.method public printCards(I)Z
	.limit stack 4
	.limit locals 5

iconst_0
istore_2

iconst_0
istore_3

aload_0
iconst_1
iload_1
invokevirtual blackjack.eq(II)Z
ifeq else_if1

iconst_1
istore 4

goto end_if1

else_if1:
iconst_0
istore 4

end_if1:

loop2:
iload_2
aload_0
getfield blackjack/numCards I
if_icmpge end_loop2

aload_0
getfield blackjack/cards [I
iload_2
iaload
invokestatic	io.print(I)V

invokestatic	io.println()V

iinc 2 1

goto loop2
end_loop2:

loop3:
iload_3
aload_0
getfield blackjack/numCardsDealer I
iconst_1
isub
iload 4
iadd
if_icmpge end_loop3

invokestatic	io.println()V

aload_0
getfield blackjack/dealerCards [I
iload_3
iaload
invokestatic	io.print(I)V

iinc 3 1

goto loop3
end_loop3:

iconst_1
ireturn

.end method

.method public askCard()Z
	.limit stack 3
	.limit locals 3

aload_0
getfield blackjack/numCards I
istore_1

iconst_1
bipush 11
invokestatic	MathUtils.random(II)I
istore_2

aload_0
getfield blackjack/cards [I
iload_1
iload_2
iastore

aload_0
aload_0
getfield blackjack/numCards I
iconst_1
iadd
putfield blackjack/numCards I

iconst_1
ireturn

.end method

.method public evaluate(I)I
	.limit stack 5
	.limit locals 4

iconst_0
istore_2

iconst_0
istore_3

aload_0
iload_1
iconst_1
invokevirtual blackjack.eq(II)Z
ifeq else_if2

loop4:
iload_2
aload_0
getfield blackjack/numCards I
if_icmpge end_loop4

iload_3
aload_0
getfield blackjack/cards [I
iload_2
iaload
iadd
istore_3

iinc 2 1

goto loop4
end_loop4:

goto end_if2

else_if2:
aload_0
iload_1
iconst_2
invokevirtual blackjack.eq(II)Z
ifeq else_if3

loop5:
iload_2
aload_0
getfield blackjack/numCardsDealer I
if_icmpge end_loop5

iload_3
aload_0
getfield blackjack/dealerCards [I
iload_2
iaload
iadd
istore_3

iinc 2 1

goto loop5
end_loop5:

goto end_if3

else_if3:
end_if3:

end_if2:

iload_3
ireturn

.end method

.method public askInput()Z
	.limit stack 4
	.limit locals 3

iconst_1
istore_1

invokestatic	io.println()V

invokestatic	ioPlus.requestNumber()I
istore_2

aload_0
iload_2
putfield blackjack/input I

aload_0
iload_2
iconst_1
invokevirtual blackjack.ne(II)Z
ifeq else_if4
aload_0
iload_2
iconst_2
invokevirtual blackjack.ne(II)Z
ifeq else_if4

iconst_0
istore_1

goto end_if4

else_if4:
end_if4:

iload_1
ireturn

.end method

.method public game()Z
	.limit stack 1
	.limit locals 1

aload_0
getfield blackjack/game Z
ireturn

.end method

.method public run()Z
	.limit stack 7
	.limit locals 1

loop6:
aload_0
invokevirtual blackjack.askInput()Z
ifne end_loop6

goto loop6
end_loop6:

aload_0
aload_0
getfield blackjack/input I
iconst_1
invokevirtual blackjack.eq(II)Z
ifeq else_if5

aload_0
invokevirtual blackjack.askCard()Z

pop

aload_0
iconst_0
invokevirtual blackjack.printCards(I)Z

pop

aload_0
aload_0
iconst_1
invokevirtual blackjack.evaluate(I)I
bipush 21
invokevirtual blackjack.gt(II)Z
ifeq else_if6

aload_0
iconst_0
putfield blackjack/game Z

goto end_if6

else_if6:
end_if6:

aload_0
iconst_0
putfield blackjack/input I

goto end_if5

else_if5:
aload_0
aload_0
getfield blackjack/input I
iconst_2
invokevirtual blackjack.eq(II)Z
ifeq else_if7

aload_0
iconst_1
invokevirtual blackjack.printCards(I)Z

pop

aload_0
aload_0
iconst_2
invokevirtual blackjack.evaluate(I)I
bipush 21
invokevirtual blackjack.gt(II)Z
ifeq else_if8

aload_0
iconst_0
putfield blackjack/game Z

goto end_if8

else_if8:
aload_0
aload_0
iconst_2
invokevirtual blackjack.evaluate(I)I
bipush 21
invokevirtual blackjack.le(II)Z
ifeq else_if9

aload_0
iconst_0
putfield blackjack/game Z

goto end_if9

else_if9:
end_if9:

end_if8:

aload_0
iconst_0
putfield blackjack/input I

goto end_if7

else_if7:
end_if7:

end_if5:

iconst_1
ireturn

.end method

.method public eq(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual blackjack.lt(II)Z
ifne else_if10
aload_0
iload_2
iload_1
invokevirtual blackjack.lt(II)Z
ifne else_if10

iconst_1

goto end_if10

else_if10:
iconst_0

end_if10:
ireturn

.end method

.method public ne(II)Z
	.limit stack 3
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual blackjack.eq(II)Z
ifne else_if11

iconst_1

goto end_if11

else_if11:
iconst_0

end_if11:
ireturn

.end method

.method public lt(II)Z
	.limit stack 2
	.limit locals 3

iload_1
iload_2
if_icmpge else_if12

iconst_1

goto end_if12

else_if12:
iconst_0

end_if12:
ireturn

.end method

.method public gt(II)Z
	.limit stack 2
	.limit locals 3

iload_1
iload_2
if_icmple else_if13

iconst_1

goto end_if13

else_if13:
iconst_0

end_if13:
ireturn

.end method

.method public le(II)Z
	.limit stack 4
	.limit locals 3

aload_0
iload_1
iload_2
invokevirtual blackjack.lt(II)Z
ifne or_0
aload_0
iload_1
iload_2
invokevirtual blackjack.eq(II)Z
ifeq else_if14
or_0:

iconst_1

goto end_if14

else_if14:
iconst_0

end_if14:
ireturn

.end method
