# These scripts generate all descriptive statistics for recalls and categories

# Means for full clone recalls type 1-2
colMeans(subset(recall.full12, lang=="java" & tool=="conqat", select = recall))
sapply(subset(recall.full12, lang=="java" & tool=="conqat", select = recall), sd)
colMeans(subset(recall.full12, lang=="c" & tool=="conqat", select = recall))
sapply(subset(recall.full12, lang=="c" & tool=="conqat", select = recall), sd)
colMeans(subset(recall.full12, lang=="java" & tool=="deckard", select = recall))
sapply(subset(recall.full12, lang=="java" & tool=="deckard", select = recall), sd)
colMeans(subset(recall.full12, lang=="c" & tool=="deckard", select = recall))
sapply(subset(recall.full12, lang=="c" & tool=="deckard", select = recall), sd)
colMeans(subset(recall.full12, lang=="c" & tool=="cccd", select = recall))
sapply(subset(recall.full12, lang=="c" & tool=="cccd", select = recall), sd)

# Means for full clone recalls type 1-3
colMeans(subset(recall.full123, lang=="java" & tool=="conqat", select = recall))
sapply(subset(recall.full123, lang=="java" & tool=="conqat", select = recall), sd)
colMeans(subset(recall.full123, lang=="c" & tool=="conqat", select = recall))
sapply(subset(recall.full123, lang=="c" & tool=="conqat", select = recall), sd)
colMeans(subset(recall.full123, lang=="java" & tool=="deckard", select = recall))
sapply(subset(recall.full123, lang=="java" & tool=="deckard", select = recall), sd)
colMeans(subset(recall.full123, lang=="c" & tool=="deckard", select = recall))
sapply(subset(recall.full123, lang=="c" & tool=="deckard", select = recall), sd)
colMeans(subset(recall.full123, lang=="c" & tool=="cccd", select = recall))
sapply(subset(recall.full123, lang=="c" & tool=="cccd", select = recall), sd)

# Means for full clone recalls type 1-4
colMeans(subset(recall.full1234, lang=="java" & tool=="conqat", select = recall))
colMeans(subset(recall.full1234, lang=="c" & tool=="conqat", select = recall))
colMeans(subset(recall.full1234, lang=="java" & tool=="deckard", select = recall))
colMeans(subset(recall.full1234, lang=="c" & tool=="deckard", select = recall))
colMeans(subset(recall.full1234, lang=="c" & tool=="cccd", select = recall))
sapply(subset(recall.full1234, lang=="c" & tool=="cccd", select = recall), sd)

# Means for partial clone recalls type 1-2
colMeans(subset(recall.partial12, lang=="java" & tool=="conqat", select = recall))
sapply(subset(recall.partial12, lang=="java" & tool=="conqat", select = recall), sd)
colMeans(subset(recall.partial12, lang=="c" & tool=="conqat", select = recall))
sapply(subset(recall.partial12, lang=="c" & tool=="conqat", select = recall), sd)
colMeans(subset(recall.partial12, lang=="java" & tool=="deckard", select = recall))
sapply(subset(recall.partial12, lang=="java" & tool=="deckard", select = recall), sd)
colMeans(subset(recall.partial12, lang=="c" & tool=="deckard", select = recall))
sapply(subset(recall.partial12, lang=="c" & tool=="deckard", select = recall), sd)
colMeans(subset(recall.partial12, lang=="c" & tool=="cccd", select = recall))
sapply(subset(recall.partial12, lang=="c" & tool=="cccd", select = recall), sd)

# Means for partial clone recalls type 1-3
colMeans(subset(recall.partial123, lang=="java" & tool=="conqat", select = recall))
sapply(subset(recall.partial123, lang=="java" & tool=="conqat", select = recall), sd)
colMeans(subset(recall.partial123, lang=="c" & tool=="conqat", select = recall))
sapply(subset(recall.partial123, lang=="c" & tool=="conqat", select = recall), sd)
colMeans(subset(recall.partial123, lang=="java" & tool=="deckard", select = recall))
sapply(subset(recall.partial123, lang=="java" & tool=="deckard", select = recall), sd)
colMeans(subset(recall.partial123, lang=="c" & tool=="deckard", select = recall))
sapply(subset(recall.partial123, lang=="c" & tool=="deckard", select = recall), sd)
colMeans(subset(recall.partial123, lang=="c" & tool=="cccd", select = recall))
sapply(subset(recall.partial123, lang=="c" & tool=="cccd", select = recall), sd)

# Means for partial clone recalls type 1-4
colMeans(subset(recall.partial1234, lang=="java" & tool=="conqat", select = recall))
colMeans(subset(recall.partial1234, lang=="c" & tool=="conqat", select = recall))
colMeans(subset(recall.partial1234, lang=="java" & tool=="deckard", select = recall))
colMeans(subset(recall.partial1234, lang=="c" & tool=="deckard", select = recall))
colMeans(subset(recall.partial1234, lang=="c" & tool=="cccd", select = recall))
sapply(subset(recall.partial1234, lang=="c" & tool=="cccd", select = recall), sd)

# Shares, medians and MADs of the categories
sum(subset(category.all, lang=="java")$Algorithm > 0) / sum(subset(category.all, lang=="java")$Algorithm >= 0)
median(subset(category.all, lang=="java")$Algorithm)
mad(subset(category.all, lang=="java")$Algorithm)

sum(subset(category.all, lang=="java")$Data.Structure > 0) / sum(subset(category.all, lang=="java")$Data.Structure >= 0)
median(subset(category.all, lang=="java")$Data.Structure)
mad(subset(category.all, lang=="java")$Data.Structure)

sum(category.all$OO.Design > 0, na.rm=TRUE) / sum(category.all$OO.Design >= 0, na.rm=TRUE)
median(subset(category.all, lang=="java")$OO.Design)
mad(subset(category.all, lang=="java")$OO.Design)

sum(subset(category.all, lang=="java")$Input.Output > 0) / sum(subset(category.all, lang=="java")$Input.Output >= 0)
median(subset(category.all, lang=="java")$Input.Output)
mad(subset(category.all, lang=="java")$Input.Output)

sum(subset(category.all, lang=="java")$Library > 0) / sum(subset(category.all, lang=="java")$Library >= 0)
median(subset(category.all, lang=="java")$Library)
mad(subset(category.all, lang=="java")$Library)

sum(subset(category.all, lang=="c")$Algorithm > 0) / sum(subset(category.all, lang=="c")$Algorithm >= 0)
median(subset(category.all, lang=="c")$Algorithm)
mad(subset(category.all, lang=="c")$Algorithm)

sum(subset(category.all, lang=="c")$Data.Structure > 0) / sum(subset(category.all, lang=="c")$Data.Structure >= 0)
median(subset(category.all, lang=="c")$Data.Structure)
mad(subset(category.all, lang=="c")$Data.Structure)

sum(subset(category.all, lang=="c")$Input.Output > 0) / sum(subset(category.all, lang=="c")$Input.Output >= 0)
median(subset(category.all, lang=="c")$Input.Output)
mad(subset(category.all, lang=="c")$Input.Output)

sum(subset(category.all, lang=="c")$Library > 0) / sum(subset(category.all, lang=="c")$Library >= 0)
median(subset(category.all, lang=="c")$Library)
mad(subset(category.all, lang=="c")$Library)

sum(category.all$Algorithm > 0) / sum(category.all$Algorithm >= 0)
median(category.all$Algorithm)
mad(category.all$Algorithm)

sum(category.all$Data.Structure > 0) / sum(category.all$Data.Structure >= 0)
median(category.all$Data.Structure)
mad(category.all$Data.Structure)

sum(category.all$Input.Output > 0) / sum(category.all$Input.Output >= 0)
median(category.all$Input.Output)
mad(category.all$Input.Output)

sum(category.all$Library > 0) / sum(category.all$Library >= 0)
median(category.all$Library)
mad(category.all$Library)

sum(subset(category.all, Data.Structure > 0 | Algorithm > 0)$Library >= 0) / sum(category.all$Library >= 0)
