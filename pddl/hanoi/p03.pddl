(define (problem hanoi-default)
(:domain hanoi)
(:objects a b - block s1 s2 s3 - stick)
(:init (clear a) (on a b) (onstick b s1) (handempty)
        (bigger b a)
        (empty s2) (empty s3)
        )
(:goal (and (onstick b s3)
        (on a b)
        ))
)

; found plan as follows:

; 0: (  unstack a b) [0]
; 1: (put-down a s2) [0]
; 2: ( pick-up b s1) [0]
; 3: (put-down b s3) [0]
; 4: ( pick-up a s2) [0]
; 5: (    stack a b) [0]
