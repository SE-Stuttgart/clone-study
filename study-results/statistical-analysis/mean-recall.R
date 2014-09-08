# This file anlyses the distribution mean of the recalls

# First, we need to test for normality so that we know if we can apply a t-test
shapiro.test(recall.partial12[,"recall"])
shapiro.test(recall.partial123[,"recall"])
shapiro.test(recall.partial1234[,"recall"])

shapiro.test(recall.full12[,"recall"])
shapiro.test(recall.full123[,"recall"])
shapiro.test(recall.full1234[,"recall"])

# Everything seems normally distributed. So we go on with the t-test
t.test(recall.partial12[,"recall"], alternative="less", mu = 0.1, conf.level = 0.95)
t.test(recall.partial123[,"recall"], alternative="less", mu = 0.1, conf.level = 0.95)
t.test(recall.partial1234[,"recall"], alternative="less", mu = 0.1, conf.level = 0.95)

t.test(recall.full12[,"recall"], alternative="less", mu = 0.01, conf.level = 0.95)
t.test(recall.full123[,"recall"], alternative="less", mu = 0.01, conf.level = 0.95)
t.test(recall.full1234[,"recall"], alternative="less", mu = 0.01, conf.level = 0.95)