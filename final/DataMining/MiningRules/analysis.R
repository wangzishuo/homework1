library(Matrix)
library(arules)
#Survived	Pclass	Sex	Age	SibSp	Parch	Embarked
titanic <- read.csv("/Users/bitrsky/BitrSky/Study/Student/DataMining/Second/Data/data.csv", header = T,colClasses = "factor")
df <- titanic
titanic.raw<-data.frame(factor(df$Survived),factor(df$Pclass), factor(df$Sex), factor(df$Age),factor(df$SibSp),factor(df$Parch),factor(df$Embarked))
names(titanic.raw)<-names(df)

#rules.all <- apriori(titanic.raw)
#summary(rules.all)
#inspect(rules.all)

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

rules.pruned

library(grid)
library(arulesViz)
dir = "/Users/bitrsky/BitrSky/Study/Student/DataMining/Second/Data/"
png(file=paste(dir,"rules_all.png"))


dev.off();