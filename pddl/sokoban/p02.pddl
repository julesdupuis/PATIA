(define (problem sokoban-default)
(:domain sokoban)
(:objects
        p11 p21 p31
        p12 p22 p32
        p13 p23 p33
         - position)
(:init
        ; horizontal
        (neighbor p11 p21) (neighbor p21 p31)
        (neighbor p12 p22) (neighbor p22 p32)
        (neighbor p13 p23) (neighbor p23 p33)
        (aligned p11 p21 p31)
        (aligned p12 p22 p32)
        (aligned p13 p23 p33)
        ; vertical
        (neighbor p11 p12) (neighbor p12 p13)
        (neighbor p21 p22) (neighbor p22 p23)
        (neighbor p31 p32) (neighbor p32 p33)
        (aligned p11 p12 p13)
        (aligned p21 p22 p23)
        (aligned p31 p32 p33)
        ; other
        (playeron p32)
        (isbox p12)
        (notbox p11) (notbox p21) (notbox p31)
                     (notbox p22) (notbox p32)
        (notbox p13) (notbox p23) (notbox p33)
        )
(:goal (and
        (isbox p11)
        ))
)

; simple
; "#####"
; "#.  #"
; "#$ @#"
; "#   #"
; "#####"

; # wall
; @ player
; $ box
; . goal
; * box on goal
; + player on goal
