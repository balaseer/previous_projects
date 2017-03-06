#Project 1 {#mainpage}

* Author: Badr Al-aseeri
* Class: CS453/552 [Operating Systems] Section 2

##Overview

This is a shell program that executes all the common shell commands
and keeps track of running jobs.

##Manifest

mydash.c - main class that executes all the code
mydash.h - header file for main class
jobs.c - class that contains functions for jobs
jobs.h - header file for jobs.c

This program also links with all the files in p0 (linked list)

##Building the project

From the p1 folder, type make and press enter.

##Features and usage

This is a shell program that accepts commands such as:
ls, cd, who, jobs, tree, etc.

It allows the user to navigate through directories and run jobs.

##Testing

Please see my TestConsiderations.txt file to see how I tested
this program in depth.

###Valgrind

I used:
valgrind --leak-check=full ./mydash

to check and everything looks fine!

###Known Bugs

My cd command is not working correctly because of how I am 
parsing the line. every cd command takes you back to the home directory
because the parser forces argv[1] to be NULL.

UPDATE: I fixed my cd command!

##Discussion

This was definitely a challenging project. exec-ing the child proccess took
me a while to learn. The other command(exit, cd, empty command, history, jobs)
were more straight forward at the beginning, but once I learned about exec it
was really easy. 

I faced a few segfaults at the beginning of this project because of how I 
used some loops and also because I had some invalid reads. I had a memory
leak as well because I was freeing something in the wrong place. It took me 
a few days of debugging to get everything right.

Overall, this project was difficult but it really taught me a lot. I have a 
much beter understanding of proccesses and how a shell works.

##Sources used

I used a lot of sources for this project. manpages, discussion boards, github, stackoverflow, etc.
I didn't have previous knowledge of a lot of this so I was looking for as much help as I could find.
The sources helped me learn shortcuts and how to access pointer and structs again since it
has been a while since ive taken CS253.
