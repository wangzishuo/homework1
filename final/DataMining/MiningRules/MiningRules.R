library(Matrix)
library(arules)
#Survived	Pclass	Sex	Age	SibSp	Parch	Embarked
titanic <- read.csv("/Users/bitrsky/BitrSky/Study/Student/DataMining/Second/Data/data.csv", header = T,colClasses = "factor")
df <- titanic
titanic.raw<-data.frame(factor(df$Survived),factor(df$Pclass), factor(df$Sex), factor(df$Age),factor(df$SibSp),factor(df$Parch),factor(df$Embarked))
names(titanic.raw)<-names(df)

rules.all <- apriori(titanic.raw)
summary(rules.all)
inspect(rules.all)

rules2 <- apriori(titanic.raw, parameter=list(minlen=2, supp=0.005, conf=0.8),
                  appearance = list(rhs=c("Survived=0","Survived=1"), 
                              default ="lhs")
                  )

rules2.sorted <-sort(rules2,by="lift")
#inspect(rules2.sorted)

subset.matrix<-is.subset(rules2.sorted,rules2.sorted)
subset.matrix[lower.tri(subset.matrix, diag=T)] <-NA
redundant <-colSums(subset.matrix,na.rm=T)>=1
which(redundant)
rules.pruned <-rules2.sorted[!redundant]
inspect(rules.pruned)

library(grid)
library(arulesViz)
dir = "/Users/bitrsky/BitrSky/Study/Student/DataMining/Second/Data/"
png(file=paste(dir,"rules_all.png"))
plot(rules.all)

png(file=paste(dir,"rules_all_grouped.png"))
plot(rules.all, method="grouped")

png(file=paste(dir,"rule_all_graph.png"))
plot(rules.all, method="graph")

png(file=paste(dir,"rules_all_paracoord.png"))
plot(rules.all, method="paracoord", control=list(reorder=T))

png(file=paste(dir,"rules_pruned_graph.png"))
plot(rules.pruned, method="graph")

png(file=paste(dir,"rules_pruned_grouped.png"))
plot(rules.pruned, method="grouped")

png(file=paste(dir,"rules_pruned_paracoord.png"))
plot(rules.pruned, method="paracoord", control=list(reorder=T))

dev.off();
