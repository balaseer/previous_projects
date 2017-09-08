#Project 5 {#mainpage}

* Author: Badr Al-aseeri
* Class: CS453/552 [Operating Systems] Section 2
* Date: 12/9/16
##Overview

This program replaces malloc with your own memory management 
scheme based on the Buddy system.

##Manifest

README.md - this file
Makefile - top level Makefile
ourhdr.h - own header
doxygen-config
doxygen.log 

/buddy-src - directory with all buddy related files
-Makefile - Makefile for buddy files
-buddy.c - implementation of buddy system memory management
-buddy.h - header for buddy memory manager
-buddy-test.c - provided buddy memory manager test
-buddy-unit-test.c - provided buddy memory manager unit test
-malloc-test.c - provided malloc test

/mydash-src - directory with all dash related files
-Makefile - Makefile for dash files that links to linked list
-jobs.c - files for job handeling
-jobs.h - header for job handeling
-mydash.c - implementaion of my own dash
-mydash.h - header for dash implementation 

/other-progs
-Makefile
-loop.c - file that runs loop
-test-readline.c - readline tester for dash

##Building the project

To compile:
            from p5 folder, type "make"

To run mydash with implementation of buddy memory manager:

           LD_LIBRARY_PATH=$(pwd) LD_PRELOAD=libbuddy.so ./mydash 

To run any program with implementation of buddy memory manager:

		   LD_LIBRARY_PATH=. LD_PRELOAD=libbuddy.so <program name>

##Program design

This program uses a struct that represents a block of memory to keep track
of the k value, if the block is being used, and pointers to the next and previous.
It uses the buddy memory management system to implement malloc, calloc, realloc and free.
It then uses wrapper functions to overide the C stdlib calls to malloc, calloc, realloc and free
to run them instead of the C stdlib calls.

I used the Knuth slides that were posted on Piazza as well as a lot of research I found online
on how the buddy memory manager works. I definitely had to do my research for this project. 

##Testing

To test this program I first used the provided tests to make sure 
that the program was initiallyworking correctly and I was on the right track.  

I also tested the buddy memory manager by trying to run as many programs using the buddy memory 
calls instead of the C stdlib calls. This is where problems were exposed in my realloc that I had
to go back and fix. I spent a lot of time here but adding print statements in my realloc helped me
pinpoint where my issues were coming from.

###Valgrind

I say this with a heavy heart, but Valgrind does not apply to this program :(

##Discussion

While working on this project I realized how much I dislike pointer arithmatic. It took
a lot of trial and error with pointer arithmatic to get things working. Since debugging
was tough with this project, I had to make sure my pointer arithmatic was working correctly.
So this project definitely helped me brush up on pointer arithmatic.

For some reason my program was looping infinitely in buddy_init. I could not figure out
what on earth was going on and then it just started working one day out of no where. 
I honestly don't think I changed anything to make it work but it's driving me crazy
that I can't figure out what happened. 

Overall, This project really helped me understand memory and how it works. It helped me
understand what happens when I call malloc and free in my code. It painted a solid
image in my head of what happens behind the scenes which I think will come in handy
when programming in the future. It will definitely help me fix bugs and memory leaks
when I face them in the future. If that was the intent of this project, then good job!

##Sources used

cs453 examples repository.
Knuth slides on Piazza.

##Extra credit

1) I made my buddy memory manager multi threaded

2) performance of buddy-test VS malloc-test: 

---------------------------FIRST ATTEMPT------------------------
[balaseer@onyx buddy-src]$ time ./buddy-test 200000 123 silent 											
real	0m0.040s
user	0m0.037s
sys	0m0.003s
[balaseer@onyx buddy-src]$ time ./malloc-test 200000 123 silent

real	0m0.051s
user	0m0.050s
sys	0m0.001s
----------------------------------------------------------------


---------------------------SECOND ATTEMPT-------------------------
[balaseer@onyx buddy-src]$ time ./buddy-test 200000 123 silent

real	0m0.037s
user	0m0.035s
sys	0m0.002s
[balaseer@onyx buddy-src]$ time ./malloc-test 200000 123 silent

real	0m0.049s
user	0m0.047s
sys	0m0.002s
-------------------------------------------------------------------


---------------------------THIRD ATTEMPT---------------------------
[balaseer@onyx buddy-src]$ time ./buddy-test 200000 123 silent

real	0m0.038s
user	0m0.037s
sys	0m0.002s
[balaseer@onyx buddy-src]$ time ./malloc-test 200000 123 silent

real	0m0.041s
user	0m0.040s
sys	0m0.001s
-------------------------------------------------------------------
