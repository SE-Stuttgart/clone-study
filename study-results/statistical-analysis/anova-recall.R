# This script runs all the ANOVA analyses on the recalls
# We use an unbalanced design. Therefore, it makes more sense to
# look at the Type II sums of squares. In R, we can realise that
# by running anova twice with switched independent variables.
# We use the second line of both outputs.

anova(lm(recall~tool*lang,data=recall.partial12))
anova(lm(recall~lang*tool,data=recall.partial12))

anova(lm(recall~tool*lang,data=recall.partial123))
anova(lm(recall~lang*tool,data=recall.partial123))

#anova(lm(recall~tool*lang,data=recall.partial1234))
#anova(lm(recall~lang*tool,data=recall.partial1234))

anova(lm(recall~tool*lang,data=recall.full12))
anova(lm(recall~lang*tool,data=recall.full12))

anova(lm(recall~tool*lang,data=recall.full123))
anova(lm(recall~lang*tool,data=recall.full123))

#anova(lm(recall~tool*lang,data=recall.full1234))
#anova(lm(recall~lang*tool,data=recall.full1234))