# This Makefile will not build unless you first update the line 
# below to point to your linked list code from p0
LISTINSTALL=../p0/
export LISTINSTALL

all: mydash-project dox buddy

mydash-project: lib
	cd mydash-src; make
	cd ..
	cd other-progs; make
	cp $(LISTINSTALL)/lib/libmylib.so .

lib:
	cd $(LISTINSTALL); make 

dox:
	echo "Generating documentation using doxygen..."
	doxygen doxygen-config > doxygen.log 2>&1
	echo "Use konqueror docs/html/index.html to see docs (or any other browser)"

buddy:
	cd buddy-src; make
	cd buddy-src
	cp buddy-src/libbuddy.so .

clean:
	cd mydash-src; make clean
	cd ..
	cd other-progs; make clean
	cd ..
	cd buddy-src; make clean
	cd $(LISTINSTALL); make clean
	rm -f mydash
	rm libmylib.so
	rm libbuddy.so
