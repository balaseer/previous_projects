#lang racket

(define replace (lambda (source search-for replace-with)
                 (if (equal? source search-for)
                      replace-with
                      (if (null? source)
                          '()
                          (if (equal? search-for (car source))
                              (cons replace-with (replace (cdr source) search-for replace-with))
                              (if (not (list? (car source)))
                          (cons (car source) (replace (cdr source) search-for replace-with))
                          (cons (replace (car source) search-for replace-with) (replace (cdr source) search-for replace-with))))))))


(replace 1 1 2)

(replace '(a (b c d) e f)
         '(b c d)
         '(x y z))

(replace 4 4 6)

(replace '(a (b c) d)
         '(b c)
         '(x y))

(replace 6 6 7)

(replace '(a (b c) (d (b c)))
         '(b c)
         '(x y))

(replace 7 7 8)

(replace '(a b c)
         '(a b)
         '(x y))

(replace 8 8 9)

(replace '(a b c)
         '(b c)
         '(x y))

(replace 2 2 5)

(replace '(x (y z) k)
         '(y z)
         '(a b))

(replace 9 9 10)

(replace '(a (x z) (d (e f)))
         '(x z)
         '(b c))

(replace 6 6 9)



         
     





