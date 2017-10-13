require(arules)
require(arulesViz)

#load the dataset and convert it into a sparse matri
data <- read.csv("D://Projects/SDL/Analysis/groceries.csv")
Groc <- read.transactions("D://Projects/SDL/Analysis/groceries.csv", sep=',')

#graphs
png(file = "D://Projects/SDL/Analysis/mostBought.png")
itemFrequencyPlot(Groc , topN = 5)
dev.off()
png(file = "D://Projects/SDL/Analysis/support.png")
itemFrequencyPlot(Groc , support = 0.10)
dev.off()

data(Groceries)
rules <- apriori(Groceries, parameter=list(support=0.005, confidence=0.5))
rules
## Scatterplot
##plot(rules)
inspect(sort(rules, by = "lift")[1:10])

##png(file = "D://Projects/SDL/Analysis/comparison.png")
##plot(rules)
##dev.off()

#plot(rules, measure=c("support", "lift"), shading="confidence")

#to remove redunduncy
subsetRules <- which(colSums(is.subset(rules, rules)) > 1) # get subset rules in vector
length(subsetRules)  #> 3913
rules <- rules[-subsetRules] 

#to find the rules according to an item
rulesMilk <- apriori (data=Groceries, parameter=list (supp=0.001,conf = 0.08),
 	appearance = list (default="lhs",rhs="whole milk"), 
	control = list (verbose=F))
rules_conf <- sort (rulesMilk, by="confidence", decreasing=TRUE) # 'high-confidence' rules.
inspect(head(rules_conf))

rules <- apriori (data=Groceries, parameter=list (supp=0.001,conf = 0.15,minlen=2), appearance = list(default="rhs",lhs="whole milk"), control = list (verbose=F)) # those who bought 'milk' also bought..
rules_conf <- sort (rules, by="confidence", decreasing=TRUE) # 'high-confidence' rules.
inspect(head(rules_conf))

