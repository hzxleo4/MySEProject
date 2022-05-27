addi.w $1 $0 8
ori.w $2 $0 2
add.w $3 $2 $1
sub.w $5 $3 $2
and.w $4 $5 $2
or.w $8 $4 $2
sll.w $8 $8 $1
bne $8 $1 -2
slti $6 $2 4
slti $7 $6 0
addi.w $7 $7 8
beq $7 $1 -2
st $8 $12 5 
ld $12 $12 5 
addi.w $10 $0 -2
addi.w $10 $10 1
jirl $13 18
beq $10 8
and.w $11 $2 2

