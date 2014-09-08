# This script analyses the categorised clone pairs inspected manually

# Correlations using Kendall. In case of missing data, we ignore
# pairs with missing data.
corrmatrix = cor(category.all[,1:5], method = "kendall", use= "pairwise.complete.obs")

cor.test(category.all[,1], category.all[,2], alternative="two.sided", method="kendall")
cor.test(category.all[,1], category.all[,3], alternative="two.sided", method="kendall")
cor.test(category.all[,1], category.all[,4], alternative="two.sided", method="kendall")
cor.test(category.all[,1], category.all[,5], alternative="two.sided", method="kendall")
cor.test(category.all[,2], category.all[,3], alternative="two.sided", method="kendall")
cor.test(category.all[,2], category.all[,4], alternative="two.sided", method="kendall")
cor.test(category.all[,2], category.all[,5], alternative="two.sided", method="kendall")
cor.test(category.all[,3], category.all[,4], alternative="two.sided", method="kendall")
cor.test(category.all[,3], category.all[,5], alternative="two.sided", method="kendall")
cor.test(category.all[,4], category.all[,5], alternative="two.sided", method="kendall")

# MANOVA analysis of the programming language and levels of difference
category.independent <- factor(category.all[,6])
result.categorylang <- manova(cbind(category.all[,1],category.all[,3],category.all[,4],category.all[,5])~category.independent)
