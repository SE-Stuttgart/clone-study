# Reads all the files for the statistical analysis

# Get all the recalls
recall.all <- read.csv("../5_toolchain-output/allRecalls.csv", sep=";", dec=".")
# Separate them into partial and full as well as 12, 123 and 1234 clones each for tool analysis or language analysis as well
# Partical recalls for all tools and languages
recall.partial12 <- subset(recall.all, type=="p12")
recall.partial123 <- subset(recall.all, type=="p123")
recall.partial1234 <- subset(recall.all, type=="p1234")

# Full recall for all tools and languages
recall.full12 <- subset(recall.all, type=="f12")
recall.full123 <- subset(recall.all, type=="f123")
recall.full1234 <- subset(recall.all, type=="f1234")

# Get the categorised clone pairs
category.full <- read.csv("../../func-clones/study-results/undetectedSampler/undetectedSampler/full-with-categories.csv", sep=";")
category.full <- cbind(category.full[,6:10], category.full[,1])
colnames(category.full)[6] <- "lang"
category.partial <- read.csv("../../func-clones/study-results/undetectedSampler/undetectedSampler/partial-with-categories.csv", sep=";")
category.partial <- cbind(category.partial[,10:14],category.partial[,1])
colnames(category.partial)[6] <- "lang"
category.all <- rbind(category.full, category.partial)