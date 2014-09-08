
cd /Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-results

echo "tool;lang;solset;type;recall" > allRecalls.csv

cat ./conqat/Java/clone-analysis-summary.csv ./conqat/C/clone-analysis-summary.csv ../CruncherResults/JavaRecall/clone-analysis-summary.csv ../CruncherResults/CRecall/clone-analysis-summary.csv ./CCCD/clone-analysis-summary.csv >> allRecalls.csv
