#!/bin/bash
# (c) Stefan Wagner
# Script for automating creating a sample of clone pairs
# for our study for ICSE 2015
# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
# Usage: createSample.sh full,partial
# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

# Hard-coded file names of detected clone pair lists
FILENAME_FULL="../../study-results/allClonePairsFull.csv"
FILENAME_PARTIAL="../../study-results/allClonePairsPartialC.csv"

# Further global variables
LINE_NUMBERS=-1
DETECTED=1


# This function creates 35 items for the sample containing
# a solution set number and two corresponding solutions each
# not detected by a detection tool.
function create_items {
for i in `seq 1 35`;
do

	while [ $DETECTED -eq 1 ]
	do
		LINE_NUMBER=$((RANDOM%$LINE_NUMBERS))
		LINE_NUMBER=$[$LINE_NUMBER + 1]

		echo DEBUG: Random line number: $LINE_NUMBER
	
		LINE="`sed -n ${LINE_NUMBER}p $FILENAME_PARTIAL`"
		if grep -Fq "$LINE" $FILENAME_FULL
		then
			#echo Detected!
			DETECTED=1
		else
			#echo Undetected!
			DETECTED=0
		fi
	done
	
	DETECTED=1
	
	echo $LINE >> partial.csv
	echo DEBUG: Solution: $LINE
done
}

LINE_NUMBERS=`cat ../../study-results/allClonePairsPartialC.csv | wc -l`
echo DEBUG: File length: $LINE_NUMBERS

# clean file
#echo partial > partial.csv
# Create a sample of 35 for Java
#LANG="java"
#create_items

# Create a sample of 35 for C
LANG="c"
create_items

echo


