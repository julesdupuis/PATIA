(define (problem hanoi-default)
(:domain hanoi)
(:objects a - block s1 s2 s3 - stick)
(:init (clear a) (onstick a s1) (handempty)
        (empty s2) (empty s3)
        )
(:goal (and (onstick a s3)
        ))
)

; found plan as follows:

; 0: ( pick-up a s1) [0]
; 1: (put-down a s3) [0]
