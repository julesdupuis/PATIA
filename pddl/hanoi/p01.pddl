(define (problem hanoi-default)
(:domain hanoi)
(:objects a b c d - block s1 s2 s3 - stick)
(:init (clear a) (on a b) (on b c) (on c d) (onstick d s1) (handempty)
        (bigger d c) (bigger c b) (bigger b a)
        ; transitivity
        (bigger d b) (bigger d a)
        (bigger c a)
        (empty s2) (empty s3)
        )
(:goal (and (onstick d s3)
        (on c d) (on b c) (on a b)
        ))
)

; found plan as follows:

; 00: (  unstack a b) [0]
; 01: (put-down a s2) [0]
; 02: (  unstack b c) [0]
; 03: (put-down b s3) [0]
; 04: ( pick-up a s2) [0]
; 05: (    stack a b) [0]
; 06: (  unstack c d) [0]
; 07: (put-down c s2) [0]
; 08: (  unstack a b) [0]
; 09: (    stack a d) [0]
; 10: ( pick-up b s3) [0]
; 11: (    stack b c) [0]
; 12: (  unstack a d) [0]
; 13: (    stack a b) [0]
; 14: ( pick-up d s1) [0]
; 15: (put-down d s3) [0]
; 16: (  unstack a b) [0]
; 17: (    stack a d) [0]
; 18: (  unstack b c) [0]
; 19: (put-down b s1) [0]
; 20: (  unstack a d) [0]
; 21: (    stack a b) [0]
; 22: ( pick-up c s2) [0]
; 23: (    stack c d) [0]
; 24: (  unstack a b) [0]
; 25: (put-down a s2) [0]
; 26: ( pick-up b s1) [0]
; 27: (    stack b c) [0]
; 28: ( pick-up a s2) [0]
; 29: (    stack a b) [0]
