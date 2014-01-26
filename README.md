# play2-chameleon - Runtime theme change module for Play 2 #


Firstly, you need to add the chameleon repository to your build.sbt file so the chameleon jars can be resolved

    resolvers += Resolver.url("chameleon Play Snapshot Repository", 
    url("http://atakurt.github.io/releases/"))(Resolver.ivyStylePatterns)
    
    
  add the following to your Build.scala  
  
      libraryDependencies ++= Seq(
      "com.degiske" %% "play2-chameleon" % "0.1"
      )
