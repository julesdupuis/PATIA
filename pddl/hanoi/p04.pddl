(define (problem hanoi-default)
(:domain hanoi)
(:objects a b c - block s1 s2 s3 - stick)
(:init (clear a) (on a b) (on b c) (onstick c s1) (handempty)
        (bigger c b) (bigger b a)
        ; transitivity
        (bigger c a)
        (empty s2) (empty s3)
        )
(:goal (and (onstick c s3)
        (on b c) (on a b)
        ))
)

; found plan as follows:

; 00: (  unstack a b) [0]
; 01: (put-down a s3) [0]
; 02: (  unstack b c) [0]
; 03: (put-down b s2) [0]
; 04: ( pick-up a s3) [0]
; 05: (    stack a b) [0]
; 06: ( pick-up c s1) [0]
; 07: (put-down c s3) [0]
; 08: (  unstack a b) [0]
; 09: (put-down a s1) [0]
; 10: ( pick-up b s2) [0]
; 11: (    stack b c) [0]
; 12: ( pick-up a s1) [0]
; 13: (    stack a b) [0]
