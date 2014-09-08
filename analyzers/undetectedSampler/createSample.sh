#!/bin/bash
# (c) Stefan Wagner
# Script for automating creating a sample of clone pairs
# for our study for ICSE 2015. It only finds fully undetected
# clone pairs.
# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
# Usage: createSample.sh 
# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

# Hard-coded file names of detected clone pair lists
FILENAME_FULL="../../study-results/allClonePairsFull.csv"
FILENAME_PARTIAL="../../study-results/allClonePairsPartial.csv"

# Further global variables
DETECTED=1
LANG="abc"
SOLUTION=999
SOLUTION1=999
SOLUTION2=999
SOLUTION_SET=999
#echo DEBUG: \$1 = $1
TYPE=full


# This function looks if the solution pair in SOLUTION1 
# and SOLUTION 2 is in
# any clone pair.
function search {
echo -n '*'
if grep -Fq "$1,$2,$3,$4" $5
then
	#echo Detected!
	DETECTED=1
else
	#echo Undetected!
	DETECTED=0
fi
}


# This function finds a solution not detected at all.
function find_undetected_pair {
while [ $DETECTED = 1 ];
do
	SOLUTION_SET=$((RANDOM%14))
	SOLUTION_SET=$[$SOLUTION_SET + 1]
	
	SOLUTION1=$((RANDOM%100))
	SOLUTION1=$[SOLUTION1 + 1]
	SOLUTION2=$((RANDOM%100))
	SOLUTION2=$[SOLUTION2 + 1]
	
	echo DEBUG: To test: $SOLUTION_SET, $SOLUTION1, $SOLUTION2
	
	if [ "$SOLUTION2" -lt "$SOLUTION1" ];
	then
		SOLUTION=$SOLUTION1
		SOLUTION1=$SOLUTION2
		SOLUTION2=$SOLUTION
	fi
	
	search $LANG $SOLUTION_SET $SOLUTION1 $SOLUTION2 $FILENAME_FULL
	
	if [ $DETECTED = 0 ];
	then
		echo DEBUG: Solution is not in full
		search $LANG $SOLUTION_SET $SOLUTION1 $SOLUTION2 $FILENAME_PARTIAL
		if [ $DETECTED = 0 ];
		then
			echo DEBUG: Solution found!
		fi
	else
		DETECTED=1
	fi
done
DETECTED=1
}

# This function creates 35 items for the sample containing
# a solution set number and two corresponding solutions each
# not detected by a detection tool.
function create_items {
for i in `seq 1 35`;
do
	find_undetected_pair
	#echo DEBUG: Type=$TYPE
	echo DEBUG: Solution: $LANG,$SOLUTION_SET,$SOLUTION1,$SOLUTION2
	echo $LANG,$SOLUTION_SET,$SOLUTION1,$SOLUTION2 >> $TYPE.csv
done
}

# clean file
echo $TYPE > $TYPE.csv
# Create a sample of 35 for Java
LANG="java"
create_items

# Create a sample of 35 for C
LANG="c"
create_items

echo


