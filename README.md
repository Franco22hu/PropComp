![Pattern](propcomp.png?raw=true)

## Introduction

This is a ulitity Java app I created for quickly comparing two .properties files.
It seaches both files for the same keys, collects and sorts all of them, then shows every difference in a console window.
It is best used from a Total Commander custom toolbar button.

## Setting up
1. Compile the java file
1. Configure the Total Commander toolbar button as shown below
	```
	Command: cmd /k "java PropComp"
	Parameters: %X%P%N %X%T%M
	Start path: ...\ClassContainingFolder\
	```
1. Select two files on both sides, then press the compare button
